package dev.vision.shopping.center.views;


import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

import de.vogella.algorithms.dijkstra.model.DijkstraAlgorithm;
import de.vogella.algorithms.dijkstra.model.Edge;
import de.vogella.algorithms.dijkstra.model.Graph;
import de.vogella.algorithms.dijkstra.model.Point;
import de.vogella.algorithms.dijkstra.model.Point.TYPE;
import de.vogella.algorithms.dijkstra.model.Vertex;
import dev.vision.shopping.center.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path.Direction;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;
import android.graphics.PathMeasure;
import android.graphics.Path;
import android.os.Handler;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.Toast;

public class Map extends ImageView {

	Context c ;

	private Paint paint;
	private Paint paintBorder;
	static Options op;
	static {
		op = new Options();
		op.inSampleSize= 2;
	}
	Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.icon_pinmap, op);
	//Bitmap escalator = BitmapFactory.decodeResource(getResources(), R.drawable.map_legend_escalator, op);


	private Matrix iMatrix;          //Image Matrix
	private boolean whereAmI;
	private boolean navigate;
	private boolean WhereToGo;
	private PathMeasure pm =new PathMeasure();

	private float length;
	private float strt[];
	private float dist[];
	private float disForHere;	
	private float start;
	private float end;
	
	private String mText;

	private float offset = 0.0f;
	private float leng=0;
	private float lent = 0;

	static Point From;
	static Point To;

	static Point path1= new Point(740, 245, TYPE.PATH);
	static Point path2= new Point(740, 280, TYPE.PATH);
	static Point path3= new Point(740, 320, TYPE.PATH);
	static Point path4= new Point(715, 320, TYPE.PATH);
	static Point path5= new Point(715, 513, TYPE.PATH);
	static Point path6= new Point(340, 513, TYPE.PATH);
	static Point path7= new Point(340, 330, TYPE.PATH);

	static Point path8= new Point(275, 330, TYPE.PATH);
	static Point path9= new Point(229, 330, TYPE.PATH);
	static Point path10= new Point(229, 393, TYPE.PATH);

	//static Point path10= new Point(818, 645);


	static ArrayList<Point> path = new ArrayList<Point>();
	static{
		path.add(path1);
		path.add(path2);
		path.add(path3);
		path.add(path4);
		path.add(path5);
		path.add(path6);
		path.add(path7);
		path.add(path8);
		path.add(path9);
		path.add(path10);


	}
	
    private void addLane(String laneId, int sourceLocNo, int destLocNo,
         int duration) {
       Edge lane = new Edge(laneId,nodes.get(sourceLocNo), nodes.get(destLocNo), duration);
       edges.add(lane);
    }
  
	float EPSILON = 0.001f;

	private ArrayList<Vertex> nodes;

	private ArrayList<Edge> edges;

	private Graph graph;
		
	public Map(Context context) {
		super(context);
		c=context;
		setup();
	}

	public Map(Context context, AttributeSet attrs) {
		super(context, attrs);
		c=context;
		setup();
	}

	public Map(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		c=context;
		setup();
	}

	@SuppressLint("NewApi")
	private void setup()
	{
		nodes = new ArrayList<Vertex>();
	    edges = new ArrayList<Edge>();
	    for (int i = 0; i < path.size(); i++) {
	      Vertex location = new Vertex("" + i, "Node_" + i, path.get(i));
	      nodes.add(location);
	    }

	    addLane("Edge_0", 0,1,1);
	    addLane("Edge_1", 1,2,1);
	    addLane("Edge_2", 2,3,1);
	    addLane("Edge_3", 3,4,1);
	    addLane("Edge_4", 4,5,1);
	    addLane("Edge_5", 5,6,1);	   
	    addLane("Edge_6", 6,7,1);	   
	    addLane("Edge_7", 7,8,1);	   
	    addLane("Edge_8", 8,9,1);	   

	    // Lets check from location Loc_1 to Loc_10
	    graph = new Graph(nodes, edges);
	    
		// init paint
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(Color.parseColor("#FF0000"));
		paint.setStrokeWidth(3);
		paint.setStyle(Style.STROKE);
		setLayerType(LAYER_TYPE_HARDWARE, paint);
	}

	public void setBorderColor(int borderColor)
	{		
		if(paintBorder != null)
			paintBorder.setColor(borderColor);
		this.invalidate();
	}
			
	@SuppressLint("DrawAllocation")
	@Override
    public void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);

		
		if(navigate){
			NavigateDijkstra(canvas);
		}else{
			if(whereAmI){
				WhereAmI(canvas);
			}
			if(WhereToGo){
				FindPlace(canvas);	
			}
		}
		
	}
			
	public void setNavPoints(Point f, Point t, String nam){
		LinkedList<Vertex> shortPath = new LinkedList<Vertex>();

		From = f;
		To	 = t;
		Edge s = null;
		Edge e = null;
		Point st = null;
		Point en = null;
		float distStart = Float.MAX_VALUE;
		float distEnd = Float.MAX_VALUE;
		
		for(Edge ed : edges){
			 //getNearPoints(f);
			Point str = getClosest(ed,f);
			Point end = getClosest(ed,t);

			float Start= (float) distance(f, str);
			float End= (float) distance(t, end);
			if(Start < distStart){
				distStart=Start;
				st=str;
				s=ed;
					
			}
			if(End< distEnd){
				distEnd=End;
				en=end;
				e=ed;
			}
				
		}
		
		if(s!= null && e!=null){
	
			//Source
			Point startSource = s.getSource().getPoint();
			Point startDest = s.getDestination().getPoint();
			
			//Dest
			Point endSource = e.getSource().getPoint();
			Point endDest = e.getDestination().getPoint();
			
		
			int srtInx =0; 
			int edInx=0;
			
			int ss = path.indexOf(startSource);
			int sd = path.indexOf(startDest);
	
			
			int es = path.indexOf(endSource);
			int ed = path.indexOf(endDest);
			
			Toast.makeText(c, "Roads: "+1, Toast.LENGTH_SHORT).show();
	
			float dfss= (float) distance(st , startSource);
			float dfsd= (float) distance(st , startDest);
			
			float dfes= (float) distance(en , endSource);
			float dfed= (float) distance(en , endDest);
			
			DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);
			int count = 0;
			LinkedList<LinkedList<Vertex>> roads = new LinkedList<LinkedList<Vertex>>();
			if(ss!=sd && ss!=es && ss !=ed && sd!=es && sd!=ed && es!=ed){	
				for(Edge edge: edges){
					Vertex vs = edge.getSource();
					Vertex vd = edge.getDestination();
					if(nodes.get(ss) ==  vs || nodes.get(sd) == vs){
						if(vd == nodes.get(es) || vd == nodes.get(ed)){
							count++;	
							LinkedList<Vertex> road= new LinkedList<Vertex>(); 
							road.add(vs);
							road.add(vd);
							roads.add(road);
	
						}
					}else if(nodes.get(es) == vs || nodes.get(ed) == vs){
						if(vd == nodes.get(ss) || vd == nodes.get(sd)){
							count++;
							LinkedList<Vertex> road= new LinkedList<Vertex>(); 
							road.add(vs);
							road.add(vd);
							roads.add(road);
						}
					}
				}
			}
	
			//get  smaller or bigger
			if(ss == es && sd == ed){
				//Toast.makeText(c, "1", Toast.LENGTH_SHORT).show();
	
				if(dfes<dfss)
					srtInx = 1;
				shortPath.add(nodes.get(ss));
				shortPath.add(nodes.get(ed));
			}else if(ss == es){
				//Toast.makeText(c, "2", Toast.LENGTH_SHORT).show();
	
				shortPath.add(nodes.get(sd));
				shortPath.add(nodes.get(ss));
				shortPath.add(nodes.get(ed));
			}else if(ss == ed){
				//Toast.makeText(c, "3", Toast.LENGTH_SHORT).show();
	
				srtInx = 1;
				shortPath.add(nodes.get(es));
				shortPath.add(nodes.get(ed));
				shortPath.add(nodes.get(sd));
			}else if(sd == es){
				//Toast.makeText(c, "4", Toast.LENGTH_SHORT).show();
	
				edInx = 1 ;
				shortPath.add(nodes.get(ss));
				shortPath.add(nodes.get(es));
				shortPath.add(nodes.get(ed));
			}else if(sd == ed){
				//Toast.makeText(c, "5", Toast.LENGTH_SHORT).show();
	
			    if(ss< es){
			    	edInx=1;
			    	shortPath.add(nodes.get(ss));
					shortPath.add(nodes.get(sd));
					shortPath.add(nodes.get(es));
				}else{
					srtInx=1;
			    	shortPath.add(nodes.get(es));
					shortPath.add(nodes.get(ed));
					shortPath.add(nodes.get(ss));
				}
			}else if(count>1 && count <5){
				//Toast.makeText(c, "Roads: "+count, Toast.LENGTH_SHORT).show();
	
				Toast.makeText(c, "6", Toast.LENGTH_SHORT).show();
	
				if(roads!=null)
				{
					LinkedList<Vertex> rr =new LinkedList<Vertex>();
					float length = Float.MAX_VALUE;
					for(LinkedList<Vertex> r : roads){
						Point vs = r.get(0).getPoint();
						Point ve = r.get(1).getPoint(); 
						Path p = new Path();
	
						int vsi= path.indexOf(vs);
						int vei = path.indexOf(ve);
						if(vsi == ss || vsi == sd)
						    p.moveTo(st.x, st.y);
						else
							p.moveTo(en.x, en.y);
	
						p.lineTo(vs.x, vs.y);
						p.lineTo(ve.x, ve.y);
						if(vei == ss || vei == sd)
						    p.lineTo(st.x, st.y);
						else
							p.lineTo(en.x, en.y);
	
						PathMeasure pm = new PathMeasure(p, false);
						float pl = pm.getLength();
						if(pl<length)
						{
							rr.clear();
							rr.addAll(r);
							length=pl;	
						}
					}
					
					Point vs = rr.get(0).getPoint();
					Point ve = rr.get(1).getPoint(); 
					int vsi= path.indexOf(vs);
					int vei = path.indexOf(ve);
					if(vsi == ss){
						shortPath.add(nodes.get(sd));
						shortPath.add(nodes.get(ss));
					}
					else if(vsi == sd){
						shortPath.add(nodes.get(ss));
						shortPath.add(nodes.get(sd));
					}
					else if(vsi == es){
						srtInx=1;
						shortPath.add(nodes.get(ed));
						shortPath.add(nodes.get(es));
					}
				    else if(vsi == ed){
				    	srtInx=1;
						shortPath.add(nodes.get(es));
						shortPath.add(nodes.get(ed));
					}
					
					if(vei == ss){
						shortPath.add(nodes.get(ss));
						shortPath.add(nodes.get(sd));
					}
					else if(vei == sd){
						shortPath.add(nodes.get(sd));
						shortPath.add(nodes.get(ss));
					}
					else if(vei == es){	
						shortPath.add(nodes.get(es));
						shortPath.add(nodes.get(ed));
					}
				    else if(vei == ed){	
						shortPath.add(nodes.get(ed));
						shortPath.add(nodes.get(es));
					}
					
				}
			}else{
				
				Toast.makeText(c, "7", Toast.LENGTH_SHORT).show();
				//ss =6 ,sd =9 , es = 7,ed =12 9 to 7
				if(sd < es){
					srtInx = ss;
				    edInx = ed;
				}else{
				    srtInx = sd;
				    edInx = es;
				}
				
				
				dijkstra.execute(nodes.get(Math.min(srtInx, edInx)));
				shortPath = dijkstra.getPath(nodes.get(Math.max(srtInx, edInx)));
				if(sd<es){
					if(ss<es){
						  if(!shortPath.contains(nodes.get(sd)))
					    	shortPath.add(0,nodes.get(sd));
						  
						  if(!shortPath.contains(nodes.get(es)))
							    shortPath.add(nodes.get(es));	   
					}else{
	
						  if(!shortPath.contains(nodes.get(sd)))
					    	shortPath.add(nodes.get(sd));
						  
						  if(!shortPath.contains(nodes.get(es)))
							    shortPath.add(0,nodes.get(es));	
					}
				    
				 
				}else{
					if(ss<es){
						 if(!shortPath.contains(nodes.get(ss)))
						    	shortPath.add(0,nodes.get(ss));
							  
						  if(!shortPath.contains(nodes.get(ed)))
						    shortPath.add(nodes.get(ed));
							    
					}else{
						 if(!shortPath.contains(nodes.get(ss)))
						    	shortPath.add(nodes.get(ss));
							  
						  if(!shortPath.contains(nodes.get(ed)))
						    shortPath.add(0,nodes.get(ed));
						
					}
				 
				}
			    
			    		   
			}
					
			if(shortPath!=null && shortPath.size()>0){
			    if(srtInx > edInx)
			    Collections.reverse(shortPath);
			    
			    assert(shortPath!=null);
			    assert(shortPath.size() > 0);
			    Path pa = new Path();
			    
			    Point first = shortPath.get(0).getPoint();
			    pa.moveTo(first.x, first.y); ;
			    for (Vertex vertex : shortPath) {
			        System.out.println(vertex.getName());
			    	Point p = vertex.getPoint();
			    	pa.lineTo(p.x, p.y);
			    }
			    //System.out.println(dijkstra.getShortestDistance(nodes.get(7)));
		
			    strt = findMinDistanceVector(From.x,From.y,pa);
				start= disForHere;
				dist = findMinDistanceVector(To.x,To.y,pa);
				end  = disForHere;
				
				leng = Math.abs(start-end);
				
				lent = start<end?start:end;
				mText = nam;
	
				whereAmI = false;
				WhereToGo= false;
				navigate = true;
				this.invalidate();
			}
		}
	}
	
	public void setNavPointsOldl(Point f, Point t, String nam){
		LinkedList<Vertex> shortPath = new LinkedList<Vertex>();

		whereAmI = false;
		WhereToGo= false;
		navigate = true;
		From = f;
		To	 = t;
		Edge s = null;
		Edge e = null;
		Point st = null;
		Point en = null;
		float distStart = Float.MAX_VALUE;
		float distEnd = Float.MAX_VALUE;
		for(Edge ed : edges){
			 //getNearPoints(f);
			Point str = getClosest(ed,f);
			Point end = getClosest(ed,t);

			float Start= (float) distance(f, str);
			float End= (float) distance(t, end);
			if(Start < distStart){
				distStart=Start;
				st=str;
				s=ed;
					
			}
			if(End< distEnd){
				distEnd=End;
				en=end;
				e=ed;
			}
				
		}

		Point startSource = s.getSource().getPoint();
		Point startDest = s.getDestination().getPoint();
		Point endSource = e.getSource().getPoint();
		Point endDest = e.getDestination().getPoint();
		
		
		
		/*
		float Start= (float) distance(st , startSource);
		float End= (float) distance(en, startSource);
		if(End > Start){
			srtInx = ss;
		    edInx = ed;
		}else{
		    srtInx = es;
		    edInx = sd;
		}
		*/
		int srtInx =0; 
		int edInx=0;
		
		int ss = path.indexOf(startSource);
		int es = path.indexOf(endSource);

		int sd = path.indexOf(startDest);
		int ed = path.indexOf(endDest);
		

		float dfss= (float) distance(st , startSource);
		float dfsd= (float) distance(st , startDest);
		
		float dfes= (float) distance(en , endSource);
		float dfed= (float) distance(en , endDest);
		
		DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);
		int count = 0;
		LinkedList<LinkedList<Vertex>> roads = new LinkedList<LinkedList<Vertex>>();
		if(ss!=sd && ss!=es && ss !=ed && sd!=es && sd!=ed && es!=ed){	
			for(Edge edge: edges){
				Vertex vs = edge.getSource();
				Vertex vd = edge.getDestination();
				if(nodes.get(ss) ==  vs || nodes.get(sd) == vs){
					if(vd == nodes.get(es) || vd == nodes.get(ed)){
						count++;	
						LinkedList<Vertex> road= new LinkedList<Vertex>(); 
						road.add(vs);
						road.add(vd);
						roads.add(road);

					}
				}else if(nodes.get(es) == vs || nodes.get(ed) == vs){
					if(vd == nodes.get(ss) || vd == nodes.get(sd)){
						count++;
						LinkedList<Vertex> road= new LinkedList<Vertex>(); 
						road.add(vs);
						road.add(vd);
						roads.add(road);
					}
				}
			}
		}
		//get  smaller or bigger
		if(ss == es && sd == ed){
			//Toast.makeText(c, "1", Toast.LENGTH_SHORT).show();

			if(dfes<dfss)
				srtInx = 1;
			shortPath.add(nodes.get(ss));
			shortPath.add(nodes.get(ed));
		}else if(ss == es){
			//Toast.makeText(c, "2", Toast.LENGTH_SHORT).show();

			if(sd>ed)
				srtInx=1;
			shortPath.add(nodes.get(sd));
			shortPath.add(nodes.get(ss));
			shortPath.add(nodes.get(ed));
		}else if(ss == ed){
			//Toast.makeText(c, "3", Toast.LENGTH_SHORT).show();

			srtInx = 1;
			shortPath.add(nodes.get(es));
			shortPath.add(nodes.get(ed));
			shortPath.add(nodes.get(sd));
		}else if(sd == es){
			//Toast.makeText(c, "4", Toast.LENGTH_SHORT).show();

			edInx = 1 ;
			shortPath.add(nodes.get(ss));
			shortPath.add(nodes.get(es));
			shortPath.add(nodes.get(ed));
		}else if(sd == ed){
			//Toast.makeText(c, "5", Toast.LENGTH_SHORT).show();

		    if(ss< es){
		    	edInx=1;
		    	shortPath.add(nodes.get(ss));
				shortPath.add(nodes.get(sd));
				shortPath.add(nodes.get(es));
			}else{
				srtInx=1;
		    	shortPath.add(nodes.get(es));
				shortPath.add(nodes.get(ed));
				shortPath.add(nodes.get(ss));
			}
		}else if(count>1 && count <5){
			//Toast.makeText(c, "Roads: "+count, Toast.LENGTH_SHORT).show();

			//Toast.makeText(c, "6", Toast.LENGTH_SHORT).show();

			if(roads!=null)
			{
				LinkedList<Vertex> rr =new LinkedList<Vertex>();
				float length = Float.MAX_VALUE;
				for(LinkedList<Vertex> r : roads){
					Point vs = r.get(0).getPoint();
					Point ve = r.get(1).getPoint(); 
					Path p = new Path();

					int vsi= path.indexOf(vs);
					int vei = path.indexOf(ve);
					if(vsi == ss || vsi == sd)
					    p.moveTo(st.x, st.y);
					else
						p.moveTo(en.x, en.y);

					p.lineTo(vs.x, vs.y);
					p.lineTo(ve.x, ve.y);
					if(vei == ss || vei == sd)
					    p.lineTo(st.x, st.y);
					else
						p.lineTo(en.x, en.y);

					PathMeasure pm = new PathMeasure(p, false);
					float pl = pm.getLength();
					if(pl<length)
					{
						rr.clear();
						rr.addAll(r);
						length=pl;	
					}
				}
				
				Point vs = rr.get(0).getPoint();
				Point ve = rr.get(1).getPoint(); 
				int vsi= path.indexOf(vs);
				int vei = path.indexOf(ve);
				if(vsi == ss){
					shortPath.add(nodes.get(sd));
					shortPath.add(nodes.get(ss));
				}
				else if(vsi == sd){
					shortPath.add(nodes.get(ss));
					shortPath.add(nodes.get(sd));
				}
				else if(vsi == es){
					srtInx=1;
					shortPath.add(nodes.get(ed));
					shortPath.add(nodes.get(es));
				}
			    else if(vsi == ed){
			    	srtInx=1;
					shortPath.add(nodes.get(es));
					shortPath.add(nodes.get(ed));
				}
				
				if(vei == ss){
					shortPath.add(nodes.get(ss));
					shortPath.add(nodes.get(sd));
				}
				else if(vei == sd){
					shortPath.add(nodes.get(sd));
					shortPath.add(nodes.get(ss));
				}
				else if(vei == es){	
					shortPath.add(nodes.get(es));
					shortPath.add(nodes.get(ed));
				}
			    else if(vei == ed){	
					shortPath.add(nodes.get(ed));
					shortPath.add(nodes.get(es));
				}
				
			}
		}else{
			
			//Toast.makeText(c, "7", Toast.LENGTH_SHORT).show();
			//Toast.makeText(c, "Roads: "+count, Toast.LENGTH_SHORT).show();

			if(sd < es){
				srtInx = ss;
			    edInx = ed;
			}else{
			    srtInx = sd;
			    edInx = es;
			}
			
			
			dijkstra.execute(nodes.get(Math.min(srtInx, edInx)));
			shortPath = dijkstra.getPath(nodes.get(Math.max(srtInx, edInx)));
			if(sd<es){
				if(ss<es){
					  if(!shortPath.contains(nodes.get(sd)))
				    	shortPath.add(0,nodes.get(sd));
					  
					  if(!shortPath.contains(nodes.get(es)))
						    shortPath.add(nodes.get(es));	   
				}else{

					  if(!shortPath.contains(nodes.get(sd)))
				    	shortPath.add(nodes.get(sd));
					  
					  if(!shortPath.contains(nodes.get(es)))
						    shortPath.add(0,nodes.get(es));	
				}
			    
			 
			}else{
			  if(!shortPath.contains(nodes.get(ss)))
		    	shortPath.add(nodes.get(ss));
			  
			  if(!shortPath.contains(nodes.get(ed)))
			    shortPath.add(0,nodes.get(ed));
			    
			}
		    
		    		   
		}
				
		
	    if(srtInx > edInx)
	    Collections.reverse(shortPath);
	    
	    assert(shortPath!=null);
	    assert(shortPath.size() > 0);
	    Path pa = new Path();
	    
	    Point first = shortPath.get(0).getPoint();
	    pa.moveTo(first.x, first.y); ;
	    for (Vertex vertex : shortPath) {
	        //System.out.println(vertex.getName());
	    	Point p = vertex.getPoint();
	    	pa.lineTo(p.x, p.y);
	    }
	    //System.out.println(dijkstra.getShortestDistance(nodes.get(7)));

	    strt = findMinDistanceVector(From.x,From.y,pa);
		start= disForHere;
		dist = findMinDistanceVector(To.x,To.y,pa);
		end  = disForHere;
		
		leng = Math.abs(start-end);
		
		lent = start<end?start:end;
		mText = nam;
		invalidate();
	}
	
	
	/*
	 if(End > Start){//original
		if(sd < es){
			srtInx = Math.max(ss, sd);//ss
			edInx = Math.min(es, ed); //ed	
		}else{
			srtInx = Math.min(ss, sd);//ss
			edInx = Math.max(es, ed);//ss
		}
	}
	else{
		srtInx = Math.min(es, ed);//ss
		edInx = Math.min(ss, sd);//es	
	} 
	 
	  if(ss < es && ss < sd && ss < ed){
		   srtInx = path.indexOf(startSource);
		}else{
		   srtInx = path.indexOf(endSource);
		}
		
		if(ed > es && ed > sd && ed > ss){
		   edInx = path.indexOf(endDest);
		}else{
		   edInx = path.indexOf(startDest);
		}
	*/
	
   //System.out.println(srtInx);
   // edInx=0;

	/* 
	if(End > Start){//original
		if(sd < es){
			shortPath.add(0, s.getSource());
			shortPath.add(e.getDestination());	
		}else{
			shortPath.add(0, e.getSource());
			shortPath.add(s.getDestination());	
		}
	}
	else{
		shortPath.add(0, es > ed ? e.getSource() : e.getDestination());
		shortPath.add(ss < sd ? s.getSource() : s.getDestination());
	}
	*/
  /*
   //Fix for missing end point
   dijkstra.execute(nodes.get(Math.min(Math.min(ss, sd), Math.min(ed, es))));
   LinkedList<Vertex> shortPath = dijkstra.getPath(nodes.get(Math.max(Math.max(ss, sd), Math.max(ed, es))));
    
   
   if(!shortPath.contains(s.getDestination()))
   	shortPath.add(0,s.getDestination());
   
   if(!shortPath.contains(s.getSource()))
   	shortPath.add(0,s.getSource());
   
   if(!shortPath.contains(e.getDestination()))
   	shortPath.add(e.getDestination());
   
   if(!shortPath.contains(e.getSource()))
   	shortPath.add(e.getSource());
   //Fix ending here
   */
	
	public void setFromPoint(Point f){
		From = f;
		whereAmI = true;
		navigate = false;
		this.invalidate();
	}
	
	public void setToPoint(Point t, String s){
		To = t;
		mText = s;
		WhereToGo = true;
		navigate = false;
		this.invalidate();
	}
	private void WhereAmI(Canvas canvas) {
		float[] dist = mapPoint(From.x,From.y);
		WhereAmI(canvas, dist);
	}
	private void FindPlace(Canvas canvas) {
		float[] dist = mapPoint(To.x,To.y);
		FindPlace(canvas,dist);
	}
	private void WhereAmI(Canvas canvas, float[] dist) {
		canvas.drawBitmap(b, dist[0] -  (float) b.getWidth()/2, dist[1] -  (float) b.getHeight() , new Paint());		
	}	
	private void FindPlace(Canvas canvas, float[] dist) {
		drawTextOnCanvas(canvas, mText, dist);
	}
	
	private void NavigateDijkstra(Canvas canvas){
		
		float mapStrt[] = mapPoint(strt[0],strt[1]);
		
		float mapDist[] = mapPoint(dist[0],dist[1]);
				
		// added this for animation
		float s = start<end?start:end;
		final float e = start<end?end:start;
		float width = 24.0f;

		if(lent < e ){
			lent+= width +36;
		}
		if(lent > e){
			lent = e;
		}
		//can delete to here
		
		//draw the course
		Path dst = new Path();
		pm.getSegment(s, lent , dst, true);
		
		dst.transform(iMatrix);
		
		//Path arrow = makeCircle( width);
		Path arrow = makeConvexArrow(width, 14.0f);
		
		PathEffect effect = new PathDashPathEffect(
			    arrow,    // "stamp"
			    36.0f,                            // advance, or distance between two stamps
			    offset ,                             // phase, or offset before the first stamp
			    PathDashPathEffect.Style.MORPH); // how to transform each stamp
		
		// Apply the dash effect
		paint.setPathEffect(effect);


		canvas.drawPath(dst, paint);
		
		//draw pin points
		WhereAmI(canvas, mapStrt);
		FindPlace(canvas, mapDist);	
		
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				if(lent>= e){
					lent = e;
					//offset = offset== 18.0f? 0.0f: 18.0f;
				}
				postInvalidate();
			}
		}, 1000);
		
	}
	
	
	
	private Point getClosest(Edge ed, Point f) {
		 Point source = ed.getSource().getPoint();
		 Point destination = ed.getDestination().getPoint();
		 return getClosestPointOnSegment(source.x, source.y, destination.x,destination.y, f.x, f.y);
	}
	public static Point getClosestPointOnSegment(int sx1, int sy1, int sx2, int sy2, int px, int py)
	{
		    double xDelta = sx2 - sx1;
		    double yDelta = sy2 - sy1;
	
		    if ((xDelta == 0) && (yDelta == 0))
		    {
		      throw new IllegalArgumentException("Segment start equals segment end");
		    }
	
		    double u = ((px - sx1) * xDelta + (py - sy1) * yDelta) / (xDelta * xDelta + yDelta * yDelta);
	
		    final Point closestPoint;
		    if (u < 0)
		    {
		      closestPoint = new Point(sx1, sy1,TYPE.PATH);
		    }
		    else if (u > 1)
		    {
		      closestPoint = new Point(sx2, sy2,TYPE.PATH);
		    }
		    else
		    {
		      closestPoint = new Point((int) Math.round(sx1 + u * xDelta), (int) Math.round(sy1 + u * yDelta),TYPE.PATH);
		    }
	
		    return closestPoint;
	}
	
	public float[] findMinDistanceVector(float x, float y, Path course) {
		 
		 pm.setPath(course, false);
		 length = pm.getLength();
		 float distanceVectorOld = Float.MAX_VALUE;
		 float distanceVectorNew = 0;
		 float[] minXY = new float[2];
		 for (float distance = 0; distance < length;distance++) {
			 float[] xy = new float[2];
			 float[] ten = new float[2];
			 
			 pm.getPosTan(distance, xy, ten);

			 distanceVectorNew = (float) dist(x, y, xy[0], xy[1]);
		      if (distanceVectorNew < distanceVectorOld) {
		    	  disForHere = distance;
		          minXY[0] = xy[0];
		          minXY[1] = xy[1];
		          distanceVectorOld = distanceVectorNew;

		      }
		 }
		 return minXY;
	}

	public double dist(float x1, float y1, float x2, float y2) {
	    float distX = x1 - x2;
	    float distY = y1 - y2;

	    // Pythagora's theorem
	    return Math.sqrt(distX * distX + distY * distY);
	}
	
	private double distance(Point p, Point q) {
        double dx = p.x - q.x; // horizontal difference
        
        double dy = p.y - q.y; // vertical difference
        double dist = Math.sqrt(dx * dx + dy * dy); // distance using Pythagoras
                                                                                                // theorem
        return dist;
	}
	
	private boolean isPointOnLine(Point point, Point start, Point end) {
        double d1=distance(point,start);
        double d2=distance(point,end);
        double d = distance (start,end);
        return  (Math.abs(d-(d1+d2))<3);
	}

	@Override
	public void setImageMatrix(Matrix matrix){
		super.setImageMatrix(matrix);
		//Image Matrix
		iMatrix = getImageMatrix(); 
		
	}

	public int convertDpToPixel(float dp) {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return (int) px;
    }
	
	@Override
	public void onSizeChanged(int w, int h, int oldw, int oldh){
	 	super.onSizeChanged(w, h, oldw, oldh);		
	}

	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
	}
	
	private float[] mapPoint(float x, float y){
		float[] src = {x ,y};
	    float[] dist = new float[2];
		iMatrix.mapPoints(dist,src);
		return dist;
	}
	
	
	
	private void drawTextOnCanvas(Canvas canvas, String text, float[] dist) {
         // maybe color the bacground..
         //canvas.drawPaint(paint);

         // Setup a textview like you normally would with your activity context
         BoldGillsansTextView tv = new BoldGillsansTextView(c);

         // setup text
         tv.setText(text);

         // maybe set textcolor
         tv.setTextColor(Color.WHITE);
         tv.setGravity(Gravity.CENTER_VERTICAL);
         tv.setPadding(0, 0, 65, 0);
         tv.setSingleLine();
         tv.setTextSize(14);
         tv.setMaxEms(6);
         tv.setEllipsize(TruncateAt.END);
         // you have to enable setDrawingCacheEnabled, or the getDrawingCache will return null
         tv.setDrawingCacheEnabled(true);
         //paint.measureText(text); 
         // we need to setup how big the view should be..which is exactly as big as the canvas
         tv.measure(MeasureSpec.makeMeasureSpec(300, MeasureSpec.AT_MOST), MeasureSpec.makeMeasureSpec(150, MeasureSpec.EXACTLY));
         tv.setBackgroundResource(R.drawable.map_store_pin_new);
         // assign the layout values to the textview
         tv.layout(0, 0, tv.getMeasuredWidth(), tv.getMeasuredHeight());

         // draw the bitmap from the drawingcache to the canvas
         Bitmap b = tv.getDrawingCache();
         canvas.drawBitmap(b, dist[0] -  (float) ((tv.getMeasuredWidth()-65)/2) , dist[1] -  (float) tv.getMeasuredHeight(), new Paint());
       //  Region r = new Region(left, top, right, bottom)
         // disable drawing cache
         tv.setDrawingCacheEnabled(false);
	}
	
	public void setText(String text) {
	     this.mText = text;
	}
	
	private Path makeConvexArrow(float length, float height) {
	    Path p = new Path();
	    p.moveTo(0.0f, -height / 2.0f);
	    p.lineTo(length - height / 4.0f, -height / 2.0f);
	    p.lineTo(length, 0.0f);
	    p.lineTo(length - height / 4.0f, height / 2.0f);
	    p.lineTo(0.0f, height / 2.0f);
	    p.lineTo(0.0f + height / 4.0f, 0.0f);
	    p.close();
	    return p;
	}
	
	private Path makeCircle(float raduis) {
	    Path p = new Path();
	    p.addCircle(0, 0, raduis, Direction.CW);
	    p.close();
	    return p;
	}
	
}
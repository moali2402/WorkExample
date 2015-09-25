package dev.vision.shopping.center.views;

/*
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

import de.vogella.algorithms.dijkstra.model.DijkstraAlgorithm;
import de.vogella.algorithms.dijkstra.model.Edge;
import de.vogella.algorithms.dijkstra.model.Graph;
import de.vogella.algorithms.dijkstra.model.Point;
import de.vogella.algorithms.dijkstra.model.Vertex;
import dev.vision.shopping.center.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path.Direction;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;
import android.graphics.PathMeasure;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.Handler;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.FloatMath;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.Toast;

public class OldMap extends ImageView {

	Context c ;

	private Paint paint;
	private Paint paintBorder;
	static Options op;
	static {
		op = new Options();
		op.inSampleSize= 2;
	}
	Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.pin_whereami, op);


	private Matrix iMatrix;          //Image Matrix
	private Path course = new Path();
	private Path pathMatrixed; 		 //Matrixed Path
	
	private boolean whereAmI;
	private boolean navigate;
	private boolean WhereToGo;
	private PathMeasure pm;


	private float rationX;
	private float rationY;
	private float drawLeft;
	private float drawTop;
	private float  drawWidth;
	private float drawHeight;
	private float length;
	private float strt[];
	private float dist[];
	private float disForHere;	
	private float start;
	private float end;
	
	private String mText;

	private float offset = 0.0f;

	static Point From= new Point(439, 633);
	static Point To= new Point(1297, 411);

	static Point path1= new Point(161, 715);
	static Point path2= new Point(527, 674);
	static Point path3= new Point(604, 696);
	static Point path4= new Point(604, 652);
	static Point path5= new Point(637, 670);
	static Point path6= new Point(818, 645);
	static Point path7= new Point(1124, 390);
	static Point path8= new Point(1618, 553);
	static Point path9= new Point(1331, 842);
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
	
	private boolean isPointOnLine(Point point, Point start, Point end) {
        double d1=distance(point,start);
        double d2=distance(point,end);
        double d = distance (start,end);
        //Log.d("AndAuthor","d1="+d1+",d2="+d2+"=="+d);
        if (Math.abs(d-(d1+d2))<3) {
                return true;
        } else {
                return false;
        }
	}

	private double distance(Point p, Point q) {
        double dx = p.x - q.x; // horizontal difference
        
        double dy = p.y - q.y; // vertical difference
        double dist = Math.sqrt(dx * dx + dy * dy); // distance using Pythagoras
                                                                                                // theorem
        return dist;
	}
	
	public OldMap(Context context) {
		super(context);
		c=context;

		setup();
		//setNavPoints(From, To);

	}

	public OldMap(Context context, AttributeSet attrs) {
		super(context, attrs);
		c=context;

		setup();
	}

	public OldMap(Context context, AttributeSet attrs, int defStyle) {
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
	    addLane("Edge_2", 1,3,1);
	    addLane("Edge_3", 2,4,1);
	    addLane("Edge_4", 3,4,1);
	    addLane("Edge_5", 4,5,1);
	    addLane("Edge_6", 5,6,2);
	    addLane("Edge_7", 5,8,4);
	    addLane("Edge_8", 6,7,4);
	    addLane("Edge_9", 7,8,2);
	   // addLane("Edge_8", 7,4,4);

	    // Lets check from location Loc_1 to Loc_10
	    graph = new Graph(nodes, edges);
	    
		// init paint
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(Color.parseColor("#FF0000"));
		paint.setStrokeWidth(3);
		paint.setStyle(Style.STROKE);
		setLayerType(LAYER_TYPE_HARDWARE, paint);
		InitCourse();
	}

	public void setBorderColor(int borderColor)
	{		
		if(paintBorder != null)
			paintBorder.setColor(borderColor);
		this.invalidate();
	}
	
	void InitCourse(){
		boolean first = true;

		course = new Path();
		for(Edge po : edges){
			if(first){
				course.moveTo(po.getSource().getPoint().x, po.getSource().getPoint().y);
				first = false; 
			}
			course.moveTo(po.getSource().getPoint().x, po.getSource().getPoint().y);
			course.lineTo(po.getDestination().getPoint().x, po.getDestination().getPoint().y);
		}
		//course.close();
		length = 0;

		calculateLength();
	}
	
	
		
	@SuppressLint("DrawAllocation")
	@Override
    public void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);

		//course was here
		
		
		if(navigate){
			//Navigate(canvas);
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
		
	/*
	public void setNavPoints(Point f, Point t, String nam){

		whereAmI = false;
		WhereToGo= false;
		navigate = true;
		From = f;
		To	 = t;
		strt = getNearPoints(From);
		start= disForHere;
		dist = getNearPoints(To);
		end  = disForHere;
		leng = Math.abs(start-end);
		
		lent = start<end?start:end;
		mText = nam;
		invalidate();
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
		      closestPoint = new Point(sx1, sy1);
		    }
		    else if (u > 1)
		    {
		      closestPoint = new Point(sx2, sy2);
		    }
		    else
		    {
		      closestPoint = new Point((int) Math.round(sx1 + u * xDelta), (int) Math.round(sy1 + u * yDelta));
		    }
	
		    return closestPoint;
	}
	
	public void setNavPoints(Point f, Point t, String nam){

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
		
		float Start= (float) distance(st , startSource);
		float End= (float) distance(en, startSource);
		int srtInx; 
		int edInx;
		
		if(End > Start){//original
			srtInx = path.indexOf(startSource);
			edInx = path.indexOf(endDest);
		}
		else{
			srtInx = path.indexOf(startDest);
			edInx = path.indexOf(endSource);
		}
		DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);
	    dijkstra.execute(nodes.get(Math.min(srtInx, edInx)));
	    LinkedList<Vertex> shortPath = dijkstra.getPath(nodes.get(Math.max(srtInx, edInx)));
	   
	    if(srtInx > edInx)
	    Collections.reverse(shortPath);
	    
	    assert(shortPath!=null);
	    assert(shortPath.size() > 0);
	    Path pa = new Path();
	    
	    Point first = shortPath.get(0).getPoint();
	    pa.moveTo(first.x, first.y); ;
	    for (Vertex vertex : shortPath) {
	     // System.out.println(vertex.getPoint());
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
	
	private Point getClosest(Edge ed, Point f) {
		 Point source = ed.getSource().getPoint();
		 Point destination = ed.getDestination().getPoint();
		 return getClosestPointOnSegment(source.x, source.y, destination.x,destination.y, f.x, f.y);
	}

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
	float leng=0;
	float lent = 0;
	private void Navigate(Canvas canvas) {
		float mapStrt[] = mapPoint(strt[0],strt[1]);
		
		float mapDist[] = mapPoint(dist[0],dist[1]);
		boolean inverse = start<end? false: true;
		
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
		
		//PathMeasure pm = new PathMeasure(dst, false);
		//leng = pm.getLength();
		//Path arrow = makeCircle( width);

		Path arrow = makeConvexArrow(width, 14.0f);
		//float values[] = new float[9];
		//iMatrix.getValues(values);
		Matrix mMatrix = new Matrix();
		//mMatrix.setTranslate(0, 0);
		//mMatrix.setScale(values[Matrix.MSCALE_X] > 2 ? 2 : values[Matrix.MSCALE_X], 1* values[Matrix.MSCALE_Y] > 2 ? 2 : values[Matrix.MSCALE_Y]);
		
		if(inverse){
			RectF bounds = new RectF();
			arrow.computeBounds(bounds, true);
			mMatrix.postRotate(180, 
			                   (bounds.right + bounds.left)/2, 
			                   (bounds.bottom + bounds.top)/2);
		}
		arrow.transform(mMatrix);

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
	
	
	void NavigateDijkstra(Canvas canvas){
		
		
		//boolean inverse = start<end? false: true;
		
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
		
		//PathMeasure pm = new PathMeasure(dst, false);
		//leng = pm.getLength();
		//Path arrow = makeCircle( width);

		Path arrow = makeConvexArrow(width, 14.0f);
		//float values[] = new float[9];
		//iMatrix.getValues(values);
		Matrix mMatrix = new Matrix();
		//mMatrix.setTranslate(0, 0);
		//mMatrix.setScale(values[Matrix.MSCALE_X] > 2 ? 2 : values[Matrix.MSCALE_X], 1* values[Matrix.MSCALE_Y] > 2 ? 2 : values[Matrix.MSCALE_Y]);
		
		
		arrow.transform(mMatrix);

		PathEffect effect = new PathDashPathEffect(
			    arrow,    // "stamp"
			    36.0f,                            // advance, or distance between two stamps
			    offset ,                             // phase, or offset before the first stamp
			    PathDashPathEffect.Style.MORPH); // how to transform each stamp
		
		// Apply the dash effect
		paint.setPathEffect(effect);


		canvas.drawPath(dst, paint);
		
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
	private float[] getNearPoints(Point p) {
		float nearP[] = findMinDistanceVector(p.x,p.y, course);
		return nearP;
	}
	public float[] findMinDistanceVector(float x, float y, Path course) {
		 
		 pm.setPath(course, false);
		 float distanceVectorOld = Float.MAX_VALUE;
		 float distanceVectorNew = 0;
		 float[] minXY = new float[2];
		 for (float distance = 0; distance < length;distance++) {
			 float[] xy = new float[2];
			 float[] ten = new float[2];
			 
			 pm.getPosTan(distance, xy, ten);
			// Toast.makeText(c, String.format(" %f, %f", distance,length), Toast.LENGTH_SHORT).show();

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
		 //Toast.makeText(c, String.format(" %f - %f", distX,distY), Toast.LENGTH_SHORT).show();

	    // Pythagora's theorem
	    return FloatMath.sqrt(distX * distX + distY * distY);
	}
	
	public int distInt(int x1, int y1, int x2, int y2) {

	    int distX = x1 - x2;
	    int distY = y1 - y2;
		 //Toast.makeText(c, String.format(" %f - %f", distX,distY), Toast.LENGTH_SHORT).show();

	    // Pythagora's theorem
	    return (int) Math.sqrt(distX * distX + distY * distY);
	}
	
	@Override
	public void setImageMatrix(Matrix matrix){
		super.setImageMatrix(matrix);
		//Image Matrix
		iMatrix = getImageMatrix(); 
		
		pathMatrixed = new Path(course);
		pathMatrixed.transform(iMatrix);
	}

	public int convertDpToPixel(float dp) {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return (int) px;
    }
	
	public void onSizeChanged(int w, int h, int oldw, int oldh){
	 	super.onSizeChanged(w, h, oldw, oldh);		
	}

	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int wi = getDrawable().getIntrinsicWidth();
		int hi = getDrawable().getIntrinsicHeight();
		drawWidth = getMeasuredWidth();
		drawHeight = getMeasuredHeight();
		//getImageMatrix();
		
		double bitmapRatio  = ((double) wi )/hi;
		double imageViewRatio  = ((double) getMeasuredWidth())/ getMeasuredHeight();
		drawLeft = 0;
		drawTop = 0;
		
		if(bitmapRatio > imageViewRatio)
		{
			drawHeight = (float) ((imageViewRatio/bitmapRatio) * getMeasuredHeight());
			drawTop = ((getMeasuredHeight() - drawHeight)/2);
		}else{
			
			drawWidth = (float) ((bitmapRatio/imageViewRatio) * getMeasuredWidth());
			drawLeft = (getMeasuredWidth() - drawWidth)/2;
		}

			
		rationX = (float) wi / (float) drawWidth;
		rationY = (float) hi / (float) drawHeight;
		
	}
	
	private float[] mapPoint(float x, float y){
		float[] src = {x ,y};
	    float[] dist = new float[2];
		iMatrix.mapPoints(dist,src);
		return dist;
	}
	
	public void calculateLength(){
	    pm = new PathMeasure(course, false);
		int pathCont=0;
		while(pm.nextContour()){
		  pathCont++;
		  length += pm.getLength();
		}
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
         tv.measure(MeasureSpec.makeMeasureSpec(300, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(160, MeasureSpec.EXACTLY));
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
*/
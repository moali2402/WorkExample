package dev.vision.shopping.center.views;


import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import de.vogella.algorithms.dijkstra.model.Edge;
import de.vogella.algorithms.dijkstra.model.Graph;
import de.vogella.algorithms.dijkstra.model.Point;
import de.vogella.algorithms.dijkstra.model.Vertex;
import de.vogella.algorithms.dijkstra.model.Point.TYPE;

public class Map4 extends BasicMap {


	Point path1= new Point(740, 245, TYPE.PATH);
	Point path2= new Point(740, 280, TYPE.PATH);
	Point path3= new Point(740, 320, TYPE.PATH);
	Point path4= new Point(715, 320, TYPE.PATH);
	Point path5= new Point(715, 513, TYPE.PATH);
	Point path6= new Point(340, 513, TYPE.PATH);
	Point path7= new Point(340, 330, TYPE.PATH);
	Point path8= new Point(275, 330, TYPE.PATH);
	Point path9= new Point(229, 330, TYPE.PATH);
	Point path10= new Point(229, 393, TYPE.PATH);

		
	public Map4(Context context) {
		super(context);
		setup();
	}

	public Map4(Context context, AttributeSet attrs) {
		super(context, attrs);
		setup();
	}

	public Map4(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setup();

	}

	@Override
	public void setup()
	{
		path = new ArrayList<Point>();
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
	
}
package dev.vision.shopping.center.map;

import java.io.Serializable;
import java.util.ArrayList;

import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.FloatMath;
import android.util.SparseArray;

public class Floor {
	
	class SplitPoint extends Point implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 7640298561758343816L;
		
		ArrayList<Point> endPoints = new ArrayList<Point>();
		
		public SplitPoint(int x , int y, Point...arg0){
			super(x, y);
			for(Point point : arg0){
				endPoints.add(point);
			}
		}
		
		public int getPossibilitySize(){
			return endPoints.size();
		}
		
		public Point getEndPoint(int index){
			return endPoints.get(index);
		}
		
		public Path getNextPath(int index){
			 Point end = getEndPoint(index);
			 Path path = new Path();
			 path.moveTo(x, y);
			 path.lineTo(end.x, end.y);
			 
			 return path;
		}
		
		
	}
	
	class Course extends Path implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = -4047873074134678304L;

		ArrayList<SplitPoint> splitPoints = new ArrayList<SplitPoint>();
		ArrayList<Point> allPoint = new ArrayList<Point>();
		PathMeasure pm = new PathMeasure(this, false);

		Region r = new Region();

		private float length;
		private float disForHere;

		public Course(){}
		
		public void addPoint(Point p){
			allPoint.add(p);
		}
		
		public void addSplitPoint(Point p, Point...args){
			allPoint.add(p);
			SplitPoint sp = new SplitPoint(p.x, p.y, args);
			splitPoints.add(sp);
		}
		
		public void drawCourse(){
			reset();
			Point start = allPoint.get(0);
			moveTo(start.x, start.y);
			for(Point p : allPoint){
				lineTo(p.x, p.y);
			}
			pm.setPath(this, false);
			
			
			RectF rectF = new RectF();
		    computeBounds(rectF, true);
			r = new Region();
			
		    r.setPath(this, new Region((int) rectF.left, (int) rectF.top, (int) rectF.right, (int) rectF.bottom));
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
		    return FloatMath.sqrt(distX * distX + distY * distY);
		}
	}
	
	SparseArray<Point> stores = new SparseArray<Point>();
	
	public void addStore(int StoreNumber, Point p){
		stores.append(StoreNumber, p);
	}
	
	
}

package dev.vision.shopping.center.api;

import java.io.Serializable;
import java.util.ArrayList;

import de.vogella.algorithms.dijkstra.model.Point;

public class CopyOfBaseLocation implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ArrayList<Location> l = new ArrayList<Location>();

	/**
	 * @return the location
	 */
	public Point getLocation(int floor) {
		for(Location loc : l){
			if(loc.getLevel() == floor)
			return loc.getPoint();
		}
		return null;
	}
	/**
	 * @param location the location to set
	 */
	public void addLocation(int level, Point p) {
		Location location = new Location();
		location.setLevel(level);
		location.setPoint(p);
		this.l.add(location);
	}
	
	public class Location {
		private Point location;
		private int level;

		/**
		 * @return the level
		 */
		public int getLevel() {
			return level;
		}
		/**
		 * @param level the level to set
		 */
		public void setLevel(int level) {
			this.level = level;
		}
		

		/**
		 * @return the location
		 */
		public Point getPoint() {
			return location;
		}
		/**
		 * @param location the location to set
		 */
		public void setPoint(Point location) {
			this.location = location;
		}
	}
}

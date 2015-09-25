package dev.vision.shopping.center.api;

import java.io.Serializable;
import java.util.ArrayList;

import de.vogella.algorithms.dijkstra.model.Point;
import de.vogella.algorithms.dijkstra.model.Point.TYPE;

public class BaseLocation implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Point location;
	private int level;
	private int id;

	/**
	 * @return the level
	 */
	public int getID() {
		return id;
	}
	/**
	 * @param level the level to set
	 */
	public void setID(int id) {
		this.id = id;
	}
	
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
	public Point getLocation() {
		return location;
	}
	/**
	 * @param location the location to set
	 */
	public void setLocation(Point location) {
		this.location = location;
	}
	
	public TYPE getType() {
		return location.type;
	}
}

package de.vogella.algorithms.dijkstra.model;

import java.io.Serializable;


/**
 * Point holds two integer coordinates
 */
public class Point implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 3059113613682490753L;
	public int x;
    public int y;
    public TYPE type;

    public Point() {}

    public Point(int x, int y, TYPE t) {
        this.x = x;
        this.y = y;
        this.type = t;
    }

    public Point(Point src, TYPE t) {
        this.x = src.x;
        this.y = src.y;
        this.type = t;
    }

    /**
     * Set the point's x and y coordinates
     */
    public void set(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Negate the point's coordinates
     */
    public final void negate() {
        x = -x;
        y = -y;
    }

    /**
     * Offset the point's coordinates by dx, dy
     */
    public final void offset(int dx, int dy) {
        x += dx;
        y += dy;
    }

    /**
     * Returns true if the point's coordinates equal (x,y)
     */
    public final boolean equals(int x, int y) {
        return this.x == x && this.y == y;
    }

    @Override public boolean equals(Object o) {
        if (o instanceof Point) {
            Point p = (Point) o;
            return this.x == p.x && this.y == p.y;
        }
        return false;
    }

    @Override public int hashCode() {
        return x * 32713 + y;
    }

    @Override public String toString() {
        return "Point(" + x + ", " + y+ ")";
    }
    
    public enum TYPE{
    	STORE, PATH, CAR, CUSTOMER_SERVICE, ESCALATOR, ELEVATOR, WC, ATM;
    }
}
package dev.vision.shopping.center.map;
/**
 * Point on 2D landscape
 * 
 * @author Roman Kushnarenko (sromku@gmail.com)</br>
 */
public class Point
{
	public Point(double x, double y)
	{
		this.x = x;
		this.y = y;
	}

	public double x;
	public double y;

	@Override
	public String toString()
	{
		return String.format("(%d,%d)", x, y);
	}
}
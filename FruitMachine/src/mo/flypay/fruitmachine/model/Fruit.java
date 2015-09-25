/**
 * 
 */
package mo.flypay.fruitmachine.model;

import android.graphics.Bitmap;

/**
 * @author Mohamed
 *
 */
public class Fruit {
	
	Bitmap image;
	String name;
	
	public Fruit(Bitmap im, String name) {
		this.image = im;
		this.name = name;
	}
	
	/**
	 * @return the resid
	 */
	public Bitmap getImage() {
		return image;
	}
	/**
	 * @param resid the resid to set
	 */
	public void setResid(Bitmap im) {
		this.image = im;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

}

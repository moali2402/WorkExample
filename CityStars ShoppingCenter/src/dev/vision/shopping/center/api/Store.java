package dev.vision.shopping.center.api;

import java.io.Serializable;

public class Store extends BaseLocation implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String number;
	private boolean isFavorite;
	private String category;

	private String store_image;

	private boolean isFeatured;

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
	/**
	 * @return the number
	 */
	public String getNumber() {
		return number;
	}
	/**
	 * @param number the number to set
	 */
	public void setNumber(String number) {
		this.number = number;
	}
	/**
	 * @return the isFavorite
	 */
	public boolean isFavorite() {
		return isFavorite;
	}
	/**
	 * @param isFavorite the isFavorite to set
	 */
	public void setFavorite(boolean isFavorite) {
		this.isFavorite = isFavorite;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}
	
	public String getStoreImage() {
		return store_image;
	}
	
	public void setStoreImage(String s) {
		store_image = s;
	}
	
	public void SetFeatured(boolean isF) {
		isFeatured = isF;
	}
	
	public boolean isFeatured() {
		return isFeatured;
	}

}

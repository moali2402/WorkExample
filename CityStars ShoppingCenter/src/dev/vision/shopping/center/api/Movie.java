package dev.vision.shopping.center.api;

import android.net.Uri;

public class Movie {

	String name;
	String description;
	String showingHours;
	String ticketPrice;
	String poster;
	
	public Movie(String name, String desc, String price, String hours, String post) {
		this.name= name;
		description = desc;
		ticketPrice = price;
		showingHours=hours;
		poster = post;
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
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the showingHours
	 */
	public String getShowingHours() {
		return showingHours;
	}
	/**
	 * @param showingHours the showingHours to set
	 */
	public void setShowingHours(String showingHours) {
		this.showingHours = showingHours;
	}
	/**
	 * @return the ticketPrice
	 */
	public String getTicketPrice() {
		return ticketPrice;
	}
	/**
	 * @param ticketPrice the ticketPrice to set
	 */
	public void setTicketPrice(String ticketPrice) {
		this.ticketPrice = ticketPrice;
	}
	
	public void setPoster(String poster) {
		this.poster = poster;
	}
	
	public String getPoster() {
		return poster;
	}
		
}

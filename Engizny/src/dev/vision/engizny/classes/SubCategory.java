package dev.vision.engizny.classes;

import dev.vision.engizny.parse.ParseSub;

public class SubCategory {
	
	private String name;
	private String image;
	private ParseSub ob;
	
	public SubCategory(){
	}
	
	public SubCategory(ParseSub s){
		ob = s;
		name = s.getName();
		if(s.getImage() != null)
		image = s.getImage().getUrl();
	}

	public String getName() {
		return name;
	}
	
	public String getImageUrl() {
		return image;
	}

	public String getObjectId() {
		return ob.getObjectId();
	}
	

}

package dev.vision.shopping.center.social;

import org.json.JSONObject;
import dev.vision.shopping.center.interfaces.SocialInterface;

public class InstagramPost implements SocialInterface{
	
	private String id;
	private String message;
	private String timestamp;
	private String link;
	private Images images;
	
	public static String PAGE_ID = "1635170610";
	public static String API = "https://api.instagram.com/v1/users/"+PAGE_ID+"/media/recent?client_id=fca833ab85e74daf901b496653d52828";
	//user including image "https://api.instagram.com/v1/users/1635170610?client_id=fca833ab85e74daf901b496653d52828&callback=?"
	public InstagramPost(){}
	
	public InstagramPost(JSONObject j){
		super();
		
		setId(j.optString("id"));
		setMessage(j.optJSONObject("caption"));
		setLink(j.optString("link"));
		setImages(j.optJSONObject("images"));
		setCreatedAt(j.optString("created_time"));

	}

	private void setImages(JSONObject optJSONObject) {
		images = new Images(optJSONObject);
	}
	
	public Images getImages() {
		return images;
	}

	private void setLink(String link) {
		this.link = link;
	}

	private void setId(String id){
		this.id = id;
	}

	private void setMessage(JSONObject obj) {
		message = obj.optString("text");
	}
	
	
	private void setCreatedAt(String timestamp) {
		this.timestamp = timestamp;
	}
	
	public String getMessage() {
		return message != null ? message : "";
	}
		
	public String getRedirectLink(){
		return this.link;
	}

	public String getInLink() {
//		return "twitter://user?user_id="+PAGE_ID;
//		return "fb://page/"+page_id+";
// 		return "fb://post/"+id;
		return "twitter://statuses/show/"+PAGE_ID;
	}

	@Override
	public String getStory() {
		return "";
	}

	@Override
	public String getPageId() {
		return PAGE_ID;
	}

	@Override
	public String getProfilePic() {
		return "https://graph.facebook.com/" + PAGE_ID + "/picture?type=normal";
	}
	
	public class Images{
		
		public Image LOW;
		public Image THUMB;
		public Image STANDARD;
		
		public Images(JSONObject j){
			LOW = new Image(j.optJSONObject("low_resolution"));
			THUMB = new Image(j.optJSONObject("thumbnail"));
			STANDARD = new Image(j.optJSONObject("standard_resolution"));
			STANDARD.URL = STANDARD.URL.replace("/e15", "/s640x640/e15");

		}

		
		public class Image{
			public String URL;
	
			public Image(JSONObject j){
				URL = j.optString("url");
			}
		}
	}
}

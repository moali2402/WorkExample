package dev.vision.shopping.center.social;

import org.json.JSONObject;

import dev.vision.shopping.center.interfaces.SocialInterface;

public class FacebookPost implements SocialInterface {
	
	private String id;
	private String post_id;
	private String page_id;
	private String message;
	private String story;
	private String timestamp;
	
	public static String PAGE_ID = "179428048815726";

	
	public FacebookPost(){}
	
	public FacebookPost(JSONObject j){
		super();
		
		setId(j.optString("id"));
		setMessage(j.optString("message"));
		setStory(j.optString("story"));
		setCreatedAt(j.optString("created_time"));

	}

	private void setId(String id){
		this.id = id;
		if(id != null && !id.trim().isEmpty()){
			String[] ids = id.split("_");
			page_id = ids[0];
			post_id = ids[1];
		}
	}

	private void setMessage(String mes) {
		message = mes;
	}
	
	private void setStory(String story) {
		this.story = story;
	}

	private void setCreatedAt(String timestamp) {
		this.timestamp = timestamp;
	}
	
	public String getMessage() {
		return message != null ? message : "";
	}
	
	public String getStory() {
		return story != null ? story : "";
	}
	
	public String getRedirectLink(){
		return "https://www.facebook.com/"+ page_id +"/posts/" + post_id;
	}

	public String getInLink() {
		return "fb://post/"+id;
//		return "fb://page/"+page_id+";
	}

	@Override
	public String getPageId() {
		return page_id;
	}
	
	@Override
	public String getProfilePic() {
		return "https://graph.facebook.com/" + PAGE_ID + "/picture?type=normal";
	}
}

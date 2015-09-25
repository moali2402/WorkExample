package dev.vision.shopping.center.social;

import org.json.JSONObject;

import dev.vision.shopping.center.interfaces.SocialInterface;

public class TwitterPost implements SocialInterface{
	
	private String id;
	private String message;
	private String story;
	private String timestamp;
	
	public static String PAGE_ID = "2960087559";
	
	public TwitterPost(){}
	
	public TwitterPost(JSONObject j){
		super();
		
		setId(j.optString("id_str"));
		setMessage(j.optString("text"));
		setCreatedAt(j.optString("created_at"));
		
	}

	private void setId(String id){
		this.id = id;
	}

	private void setMessage(String mes) {
		message = mes;
	}
	
	private void setCreatedAt(String timestamp) {
		this.timestamp = timestamp;
	}
	
	public String getMessage() {
		return message != null ? message : "";
	}
		
	public String getRedirectLink(){
		return "https://twitter.com/"+ PAGE_ID +"/status/"+id;
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
	
}

package dev.vision.shopping.center.views;

import dev.vision.shopping.center.R;
import android.content.Context;
import android.graphics.Color;
import android.os.Debug;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

 public class NonUnderlinedClickableSpan extends ClickableSpan {

    int type;// 0-hashtag , 1- mention, 2- url link
    String text;// Keyword or url
    String time;
	private Context ctx;

    public NonUnderlinedClickableSpan(Context c, String text, int type) {
    	this.ctx = c;
        this.text = text;
        this.type = type;
        this.time = time;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
    	//adding colors 
        if (type == 1)
            ds.setColor(Color.RED);
        else if (type == 2)
            ds.setColor(Color.BLUE);
        else
            ds.setColor(Color.BLUE);
        ds.setUnderlineText(false);
        // ds.setTypeface(Typeface.DEFAULT_BOLD);
    }

    @Override
    public void onClick(View v) {

        if (type == 0) {
            //pass hashtags to activity using intents 
        } else if (type == 1) {
             //do for mentions 
        } else {

        }
    }
}
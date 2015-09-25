package dev.vision.shopping.center;

import dev.vision.shopping.center.adapter.ChooseFromAdapter;
import dev.vision.shopping.center.api.Store;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils.TruncateAt;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.jmpergar.awesometext.AwesomeTextHandler;

public class ChooseFrom_Activity extends Activity{
	
    private static final String MENTION_PATTERN = "(@[\\p{L}0-9-&-_ ]+)";

	
    public void onCreate(Bundle savedInstanceState){
    	super.onCreate(savedInstanceState);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.choose_from);
		
		Store s = (Store) getIntent().getExtras().get("store_n");
		final ListView lv = (ListView) findViewById(R.id.list);
		final ChooseFromAdapter adapter = new ChooseFromAdapter(this, Static.Stores);
		lv.setAdapter(adapter);
		
		EditText to = (EditText) findViewById(R.id.to);
		final EditText from = (EditText) findViewById(R.id.from);
        final AwesomeTextHandler taw = new AwesomeTextHandler();
        

        taw.addViewSpanRenderer(MENTION_PATTERN, new MentionSpanRenderer())
            .setView(to);
        to.setKeyListener(null); 
        to.setClickable(false); 
        to.setFocusable(false);
        to.setSingleLine();
        to.setEllipsize(TruncateAt.END);
        to.setMovementMethod(null);
        taw.setText("TO: @"+s.getName() + " - Level " + s.getLevel());
		
		from.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence c, int arg1, int arg2, int arg3) {
				
				adapter.getFilter().filter(c);
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {

			}
		});
		lv.setTextFilterEnabled(true);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int i,
					long arg3) {
                Intent intent=new Intent();  
                intent.putExtra("store",(Store) adapter.getItem(i));  
                if (getParent() == null) {
                	setResult(RESULT_OK,intent);
                } else {
                    getParent().setResult(RESULT_OK,intent);
                }
                  
                  
                finish();
				
			}
		});
    }
    

    
    class MentionSpanRenderer implements AwesomeTextHandler.ViewSpanRenderer {
    	//AwesomeTextHandler.ViewSpanClickListener
        private final static int textSizeInDips = 14;

    	 @Override
         public View getView(String text, Context context) {
             TextView mentionView = new TextView(context);
             mentionView.setText(text.substring(1));
             mentionView.setBackgroundResource(R.drawable.common_hashtags_background);
             mentionView.setTextSize(ScreenUtils.dipsToPixels(context, textSizeInDips));
             mentionView.setTextColor(Color.WHITE);
             return mentionView;
         }

    }
}

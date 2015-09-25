/**
 * 
 */
package dev.vision.shopping.center;

import de.vogella.algorithms.dijkstra.model.Point;
import dev.vision.shopping.center.api.Store;
import dev.vision.shopping.center.views.BoldGillsansTextView;
import dev.vision.shopping.center.views.GillsansTextView;
import dev.vision.shopping.center.views.Map;
import uk.co.senab.photoview.PhotoViewAttacher;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;

/**
 * @author Mo
 *
 */
public class CopyOfStore_Activity extends Activity {
	
	private PhotoViewAttacher mAttacher;
	private Map mImageView;
    Store s;
	@Override
	public void onCreate(Bundle savedInstanceState){
        setTheme(R.style.AppTheme);

       
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.store_page);

		BoldGillsansTextView name = (BoldGillsansTextView) findViewById(R.id.bold_name);
		GillsansTextView level = (GillsansTextView) findViewById(R.id.level);
		ImageView favourited = (ImageView) findViewById(R.id.ImageView01);
		ImageView navigate = (ImageView) findViewById(R.id.nav);
		BoldGillsansTextView call = (BoldGillsansTextView) findViewById(R.id.call);

		mImageView = (Map) findViewById(R.id.Map);
		
		Bundle extras = getIntent().getExtras();
		s= (Store) extras.getSerializable("store");
		final String nam = s.getName();
		String lev="";// = s.getLevel();


		name.setText(nam);
		level.setText("Level " +lev);
		favourited.setImageResource(R.drawable.favorite_selector);
		if(Static.favoStore.contains(s))
			favourited.setActivated(true);
		else
			favourited.setActivated(false);

		if(s!=null)
			mImageView.setToPoint(s.getLocation(), nam);

		navigate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				//844 ,535 microsoft, hm 484 ,543
				//mImageView.setNavPoints( new Point(1462 ,607),s.getLocation(), nam);
				startActivityForResult(new Intent(CopyOfStore_Activity.this,ChooseFrom_Activity.class),55);
				
			}
		});
			
		call.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
			
				Intent intent = new Intent(Intent.ACTION_CALL);
		
				intent.setData(Uri.parse("tel:" + "8000"));
				startActivity(intent);
			}
		});
		

		mAttacher = new PhotoViewAttacher(mImageView);

	    mAttacher.setMaximumScale((float) 3.5);
	    mAttacher.setMediumScale((float) 2);

	}
	
	@Override  
    protected void onActivityResult(int requestCode, int resultCode, Intent data)  
    {  
              super.onActivityResult(requestCode, resultCode, data);  
                  
              if(requestCode==55 && resultCode ==Activity.RESULT_OK)  
              {  
                 Store f=(Store) data.getSerializableExtra("store");   
                 if(s!=null && f!=null)
 				 mImageView.setNavPoints(f.getLocation(),s.getLocation(), s.getName());
      
              }  
  
    }  

}

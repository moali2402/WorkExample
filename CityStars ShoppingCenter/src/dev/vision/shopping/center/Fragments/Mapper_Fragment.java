package dev.vision.shopping.center.Fragments;

import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import de.vogella.algorithms.dijkstra.model.Point;
import dev.vision.shopping.center.R;
import dev.vision.shopping.center.Static;
import dev.vision.shopping.center.api.BaseLocation;
import dev.vision.shopping.center.api.Conveyor;
import dev.vision.shopping.center.api.Store;
import dev.vision.shopping.center.interfaces.MapSetup;
import dev.vision.shopping.center.views.BasicMap;
import dev.vision.shopping.center.views.Map;
import uk.co.senab.photoview.PhotoViewAttacher;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * @author Mo
 *
 */
public class Mapper_Fragment extends Fragment implements MapSetup {
	
	
	
	private PhotoViewAttacher mAttacher;
	private BasicMap mImageView;
	private BaseLocation s;
	private BaseLocation f;
	private int level;
	
    public static Mapper_Fragment newInstance(int level, Store s) {
    	Mapper_Fragment f = new Mapper_Fragment();
    	Bundle data = new Bundle();
    	data.putSerializable("store", s);
    	data.putInt("level", level);
    	f.setArguments(data);
        return f;
    }

    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
    	
    	Bundle extras = getArguments();
    	level = extras.getInt("level");
		try {

	    	Class<?> myClass = Class.forName("dev.vision.shopping.center.views.Map"+level);

	    	Constructor constructor =
	    			myClass.getConstructor(new Class[]{Context.class});


	    	Object instanceOfMyClass = constructor.newInstance(getActivity().getApplicationContext());
			mImageView = (BasicMap) instanceOfMyClass;
			mImageView.setLevel(level);
		} catch (Exception e) {
			e.printStackTrace();
		}

	    mAttacher = new PhotoViewAttacher(mImageView);

	    mAttacher.setMaximumScale((float) 3.5);
	    mAttacher.setMediumScale((float) 2);

		
	    return mImageView;
	}

    
    
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		

		setMap(level);
		draw();
	}
	
	private void draw() {
		if(s == null && f == null){
			eraseAll();
		}else if(s!=null && f!=null){
			setNav(f,s);
		}else{
			if(s!=null){
				plotTo(s);
			}
			if(f!=null){
				plotFrom(f);
			}
		}		
	}

	private void eraseAll() {
		mImageView.eraseAll();
	}

	private void setMap(int no) {
	    String uri = "level"+no;

	    int imageResource = getActivity().getApplicationContext().getResources().getIdentifier(uri, "drawable", getActivity().getApplicationContext().getPackageName());
	    mImageView.setImageResource(imageResource);
	    //loadBitmap(imageResource, mImageView);
	}
	
	public void loadBitmap(int resId, ImageView imageView) {
        BitmapWorkerTask task = new BitmapWorkerTask(mImageView);
        task.execute(resId);
    }
	
	class BitmapWorkerTask extends AsyncTask<Integer, Void, Bitmap> {
	    private final WeakReference<ImageView> imageViewReference;
	    private int data = 0;

	    public BitmapWorkerTask(ImageView imageView) {
	        // Use a WeakReference to ensure the ImageView can be garbage collected
	        imageViewReference = new WeakReference<ImageView>(imageView);
	    }

	    // Decode image in background.
	    @Override
	    protected Bitmap doInBackground(Integer... params) {
	        data = params[0];
	        return decodeSampledBitmapFromResource(getResources(), data, 100, 100);
	    }

	    // Once complete, see if ImageView is still around and set bitmap.
	    @Override
	    protected void onPostExecute(Bitmap bitmap) {
	        if (imageViewReference != null && bitmap != null) {
	            final ImageView imageView = imageViewReference.get();
	            if (imageView != null) {
	                imageView.setImageBitmap(bitmap);
	            }
	        }
	    }
	    
	    public Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
	            int reqWidth, int reqHeight) {

	        // First decode with inJustDecodeBounds=true to check dimensions
	        final BitmapFactory.Options options = new BitmapFactory.Options();
	        options.inJustDecodeBounds = true;
	        BitmapFactory.decodeResource(res, resId, options);

	        // Calculate inSampleSize
	        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

	        // Decode bitmap with inSampleSize set
	        options.inJustDecodeBounds = false;
	        return BitmapFactory.decodeResource(res, resId, options);
	    }
	    
	    public int calculateInSampleSize(
	            BitmapFactory.Options options, int reqWidth, int reqHeight) {
	    // Raw height and width of image
	    final int height = options.outHeight;
	    final int width = options.outWidth;
	    int inSampleSize = 1;

	    if (height > reqHeight || width > reqWidth) {

	        final int halfHeight = height / 2;
	        final int halfWidth = width / 2;

	        // Calculate the largest inSampleSize value that is a power of 2 and keeps both
	        // height and width larger than the requested height and width.
	        while ((halfHeight / inSampleSize) > reqHeight
	                && (halfWidth / inSampleSize) > reqWidth) {
	            inSampleSize *= 2;
	        }
	    }

	    return inSampleSize;
	}
	}

	
	@Override
	public void setTo(BaseLocation t) {
		this.s = t;
	}
	
	@Override
	public void setFrom(BaseLocation f) {
		this.f = f;
		//refresh();
	}
	
	public void setFrom(int id) {
		for( Conveyor temp : Static.Conveyors){
			if(temp.getID() == id && temp.getLevel() == level){
			  this.f = temp;
			}
	   }
	   //refresh();
	}
	
	public void setTo(int id) {
		for( Conveyor temp : Static.Conveyors){
			if(temp.getID() == id && temp.getLevel() == level){
			  this.s = temp;
			}
	   }
	}
	
	public int fromNearest() {
		Conveyor c = getNearstTo(s);
		this.f = c;
		return c.getID();
	}

	public int toNearest() {
		Conveyor c = getNearstTo(f);
		this.s = c;
		//refresh();
		return c.getID();
	}

	private Conveyor getNearstTo(BaseLocation f) {
		Conveyor c = null;
		float distanceVectorOld = Float.MAX_VALUE;
		float distanceVectorNew = 0;
		for( Conveyor temp : Static.Conveyors){
			if(temp.getLevel() == level){
				distanceVectorNew = (float) distance(temp.getLocation(), f.getLocation());
		        if (distanceVectorNew < distanceVectorOld) {
		          c= temp;
		          distanceVectorOld = distanceVectorNew;
		       }
			}
	   }

       return c;
	}
	
	private double distance(Point p, Point q) {
        double dx = p.x - q.x; // horizontal difference
        
        double dy = p.y - q.y; // vertical difference
        double dist = Math.sqrt(dx * dx + dy * dy); // distance using Pythagoras
                                                                                                // theorem
        return dist;
	}

	private void plotFrom(BaseLocation from) {
		mImageView.setFromPoint(from.getLocation());
	}
	
	private void plotTo(BaseLocation to) {
		if(to instanceof Store)
			mImageView.setToPoint(to.getLocation(), ((Store)to).getName());
		else if(to instanceof Conveyor)
			mImageView.setToPoint(to.getLocation(), "");
	}
	
	@Override
	public void setNav(BaseLocation f, BaseLocation t) {
		if(t instanceof Store)
			mImageView.setNavPoints(f.getLocation(), t.getLocation(), ((Store)t).getName());
		else if(t instanceof Conveyor)
			mImageView.setNavPoints(f.getLocation(), t.getLocation(), "");
	}
	
	public void refresh(){
		FragmentManager fm = getFragmentManager();
		if(fm != null)
	        fm.beginTransaction()
	           .detach(this)
	           .attach(this)
	           .commit();
	}

	@Override
	public void CustomerService() {
		if(mImageView != null)
		mImageView.CustomerService();
	}

	@Override
	public void WC() {
		if(mImageView != null)
		mImageView.WC();		
	}

	@Override
	public void Escalators() {
		if(mImageView != null)
		mImageView.Escalators();				
	}

	@Override
	public void Elevators() {
		if(mImageView != null)
		mImageView.Elevators();				
	}

	@Override
	public void ATM() {
		if(mImageView != null)
		mImageView.ATM();			
	}

	@Override
	public void RESET() {
		if(mImageView != null)
		mImageView.RESET();			
	}
}
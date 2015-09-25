package dev.vision.shopping.center.map;
/*
import java.lang.ref.WeakReference;
import java.util.ArrayList;

import de.vogella.algorithms.dijkstra.model.Edge;
import de.vogella.algorithms.dijkstra.model.Graph;
import de.vogella.algorithms.dijkstra.model.Point;
import de.vogella.algorithms.dijkstra.model.Vertex;
import dev.vision.shopping.center.R;
import dev.vision.shopping.center.Static;
import dev.vision.shopping.center.api.BaseLocation;
import dev.vision.shopping.center.api.Conveyor;
import dev.vision.shopping.center.api.Store;
import dev.vision.shopping.center.interfaces.MapSetup;
import dev.vision.shopping.center.views.Map;
import uk.co.senab.photoview.PhotoViewAttacher;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PathMeasure;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;


public class Mapper_Fragment extends Fragment implements MapSetup {
	
	
	
	private PhotoViewAttacher mAttacher;
	private Map mImageView;
	private BaseLocation s;
	private BaseLocation f;
	private int level = 1;
	

	private Matrix iMatrix;          //Image Matrix
	private boolean whereAmI;
	private boolean navigate;
	private boolean WhereToGo;
	private PathMeasure pm =new PathMeasure();

	private float length;
	private float strt[];
	private float dist[];
	private float disForHere;	
	private float start;
	private float end;
	
	private String mText;

	private float offset = 0.0f;
	private float leng=0;
	private float lent = 0;

	static Point From;
	static Point To;

	static Point path1= new Point(740, 245);
	static Point path2= new Point(740, 280);
	static Point path3= new Point(740, 320);
	static Point path4= new Point(715, 320);
	static Point path5= new Point(715, 513);
	static Point path6= new Point(340, 513);
	static Point path7= new Point(340, 330);

	static Point path8= new Point(275, 330);
	static Point path9= new Point(229, 330);
	static Point path10= new Point(229, 393);

	static ArrayList<Point> path = new ArrayList<Point>();
	static{
		path.add(path1);
		path.add(path2);
		path.add(path3);
		path.add(path4);
		path.add(path5);
		path.add(path6);
		path.add(path7);
		path.add(path8);
		path.add(path9);
		path.add(path10);


	}
	
    private void addLane(String laneId, int sourceLocNo, int destLocNo,
         int duration) {
       Edge lane = new Edge(laneId,nodes.get(sourceLocNo), nodes.get(destLocNo), duration);
       edges.add(lane);
    }
  
	float EPSILON = 0.001f;

	private ArrayList<Vertex> nodes;

	private ArrayList<Edge> edges;

	private Graph graph;
		
	
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
    	View v = inflater.inflate(R.layout.mapper_map, container, false);

		mImageView = (Map) v.findViewById(R.id.Map);

	    mAttacher = new PhotoViewAttacher(mImageView);

	    mAttacher.setMaximumScale((float) 3.5);
	    mAttacher.setMediumScale((float) 2);

		
	    return v;
	}

    
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		

		setMap(level);


		if(s!=null && f!=null){
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
	
	private void setMap(int no) {
	    String uri = "level"+no;

	    int imageResource = getResources().getIdentifier(uri, "drawable", getActivity().getPackageName());
	    Toast.makeText(getActivity(), ""+imageResource, Toast.LENGTH_SHORT).show();
	    mImageView.setImageResource(imageResource);
	    //loadBitmap(imageResource, mImageView);
	}
	/*
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
	}
	
	public void setFrom(int id) {
		for( Conveyor temp : Static.Conveyors){
			if(temp.getID() == id && temp.getLevel() == level){
			  this.f = temp;
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
	
}
*/
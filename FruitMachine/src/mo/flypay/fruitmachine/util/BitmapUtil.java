package mo.flypay.fruitmachine.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BitmapUtil {
	
	public static Bitmap getBitmap(Context context, int resId){
    	return BitmapFactory.decodeResource(context.getResources(), resId);
    }

}

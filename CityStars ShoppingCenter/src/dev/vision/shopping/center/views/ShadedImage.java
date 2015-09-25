package dev.vision.shopping.center.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

public class ShadedImage extends ImageView {


	private Paint paint;
	private Paint paintBorder;
	Context c;
	

	public ShadedImage(Context context) {
		super(context);
		c=context;

		setup();
	}

	public ShadedImage(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		setup();
	}

	public ShadedImage(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		c=context;
		
		setup();
	}

	@SuppressLint("NewApi")
	private void setup()
	{
		// init paint
		paint = new Paint();
		paint.setAntiAlias(true);

		setLayerType(LAYER_TYPE_HARDWARE, paintBorder);

	}


	

	public void setBorderColor(int borderColor)
	{		
		if(paintBorder != null)
			paintBorder.setColor(borderColor);
		this.invalidate();
	}
	
	@SuppressLint("DrawAllocation")
	@Override
    public void onDraw(Canvas canvas)
	{
		Shader shader = new LinearGradient(0, getHeight(), 0, (float) (getHeight()/1.5), Color.BLACK, Color.TRANSPARENT, Shader.TileMode.CLAMP);
		//shader.setLocalMatrix(trans);

		paint.setShader(shader);
		canvas.drawRect(new RectF(0, (float) (getHeight()/1.5), getWidth(), getHeight()), paint);
	
		super.onDraw(canvas);

	}
	
	public void onSizeChanged(int w, int h, int oldw, int oldh){
		super.onSizeChanged(w, h, oldw, oldh);		
	}

	
}
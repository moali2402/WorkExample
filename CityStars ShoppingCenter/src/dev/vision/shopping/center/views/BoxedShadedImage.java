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
import android.graphics.Path;
import android.graphics.Path.Direction;
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

public class BoxedShadedImage extends ImageView {


	private Paint paint;
	private Paint paintBorder;
	Context c;
	

	public BoxedShadedImage(Context context) {
		super(context);
		c=context;

		setup();
	}

	public BoxedShadedImage(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		setup();
	}

	public BoxedShadedImage(Context context, AttributeSet attrs, int defStyle) {
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
		super.onDraw(canvas);

		int w = getMeasuredWidth();
		int h = getMeasuredHeight();
		paint.setColor(Color.parseColor("#50FFFFFF"));
		Path p = new Path();
		p.moveTo(0,0);
		p.addRect(0, 0 , w/8, w/8, Direction.CW);
		p.addRect(w/8, w/8 , w/8 + w/7, w/8 + w/7,  Direction.CW);
		p.addRect((w/8 + w/7) -40, (w/8 + w/7) -40  , (w/8 + w/7) + w/4, (w/8 + w/7) + w/4,  Direction.CW);
		p.addRect((w/8 + w/7) + w/4 -40, w/8 , (w/8 + w/7) + w/4 + w/8, w/8 + w/7,  Direction.CW);
		p.addRect(0, (w/8 + w/7) + w/4 , w/8, (w/8 + w/7) + w/4 + w/7,  Direction.CW);
		p.close();
		canvas.drawPath(p, paint);
	}
	
	public void onSizeChanged(int w, int h, int oldw, int oldh){
		super.onSizeChanged(w, h, oldw, oldh);		
	}

	
}
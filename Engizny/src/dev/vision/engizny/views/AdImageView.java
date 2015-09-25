package dev.vision.engizny.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class AdImageView extends ImageView
{

    public AdImageView(final Context context)
    {
        super(context);
    }

    public AdImageView(final Context context, final AttributeSet attrs)
    {
        super(context, attrs);
    }

    @SuppressLint("NewApi") public AdImageView(final Context context, final AttributeSet attrs, final int defStyle)
    {
        super(context, attrs, defStyle);
    }


    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec)
    {
        //setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    	setMeasuredDimension(getMeasuredWidth(),getMeasuredWidth()/2);
    }

}
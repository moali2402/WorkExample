package dev.vision.engizny.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class SquareLinearLayout extends LinearLayout
{

    public SquareLinearLayout(final Context context)
    {
        super(context);
    }

    public SquareLinearLayout(final Context context, final AttributeSet attrs)
    {
        super(context, attrs);
    }

    @SuppressLint("NewApi") public SquareLinearLayout(final Context context, final AttributeSet attrs, final int defStyle)
    {
        super(context, attrs, defStyle);
    }


    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec)
    {
        //setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);

    	//setMeasuredDimension(getWidth(),getWidth());
    }

}
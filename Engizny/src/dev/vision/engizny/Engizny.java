package dev.vision.engizny;

import android.graphics.Canvas;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.CanvasTransformer;

public class Engizny extends CustomAnimation {

	public Engizny() {
		// see the class CustomAnimation for how to attach 
		// the CanvasTransformer to the SlidingMenu
		super(new CanvasTransformer() {
			@Override
			public void transformCanvas(Canvas canvas, float percentOpen) {
				float scale = (float) (percentOpen*0.25 + 0.75);
				canvas.scale(scale, scale, canvas.getWidth()/2, canvas.getHeight()/2);
			}
		});
	}


	
}

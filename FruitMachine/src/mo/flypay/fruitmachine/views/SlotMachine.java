/**
 * 
 */
package mo.flypay.fruitmachine.views;

import java.util.Random;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import mo.flypay.fruitmachine.R;
import mo.flypay.fruitmachine.datasource.Source;
import mo.flypay.fruitmachine.wheel.widget.adapters.FruitMachineAdapter;
import mo.flypay.fruitmachine.wheel.widget.interfaces.OnSlotMachineListener;
import mo.flypay.fruitmachine.wheel.widget.interfaces.OnSlotChangedListener;
import mo.flypay.fruitmachine.wheel.widget.interfaces.OnSlotScrollListener;

/**
 * @author Mohamed
 *
 */
public class SlotMachine extends LinearLayout{


	private OnSlotMachineListener scrollListener;
	private int spinning = 0;
	private final static int def_no_of_slots = 3;
	private int no_of_slots = def_no_of_slots;
	//private Paint paint;
	private Source itemSource;

	
	public SlotMachine(Context context) {
		super(context);
		init();
	}
	
	public SlotMachine(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}


	@SuppressLint("NewApi")
	public SlotMachine(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}


	@SuppressLint("NewApi")
	public SlotMachine(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		init();
	}

	private void init() {
		//setWillNotDraw(false);
		//paint = new Paint();
		//paint.setAntiAlias(true);


		setDrawingCacheEnabled(true);		
	}

	@Override
	public void onDraw(Canvas canvas){
		super.onDraw(canvas);
		
	/*	 To Be completed 
	 *   Programmatic Frame for FruitMachine
	 *   
			paint.setColor(Color.parseColor("#eeeeee"));
			paint.setStyle(Style.STROKE);
			paint.setStrokeWidth(80);
			canvas.drawRect(0, 0, getWidth(),getHeight(), paint);
			
	
			paint.setColor(Color.parseColor("#08d4d3"));
			paint.setStyle(Style.STROKE);
			paint.setStrokeWidth(40);
			canvas.drawRoundRect(new RectF(40, 40, getWidth()-40, getHeight()-40), 25f, 25f, paint);
	*/
	}
	
	
	public void setItemsSource(Source source){
		this.itemSource = source;
	}
	
	public void setNoOfSlots(int slots){
		this.no_of_slots = slots>0 ? slots : def_no_of_slots ;
	}
	
	public void setup(){
		if(itemSource != null){
			removeAllViews();
			for(int i= 0; i < no_of_slots; i++){
				initSlot();
			}
		}
	}
	

	private void initSlot() {
		SlotView slot = new SlotView(getContext());
		slot.setViewAdapter(new FruitMachineAdapter(getContext(), itemSource));
		slot.setCurrentItem(new Random().nextInt(itemSource.getFruitList().size()));
		slot.setChangingListener(changedListener);
		slot.setScrollingListener(scrolledListener);
		slot.setEnabled(false);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams( LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
		params.weight = 1.0f;
		addView(slot, params);
	}
	
	public void setSlotListener(OnSlotMachineListener scrollListener){
		this.scrollListener = scrollListener;
	}

	public void removeScrollListener(){
		this.scrollListener = null;
	}
	
	public void spin(){
		destroyDrawingCache();
		Random r = new Random();
		int d = r.nextInt(getChildCount()) + 1;
		for(int i = 0; i < getChildCount(); i++)
		{
			View v =  getChildAt(i);
			if(v instanceof SlotView )
				spin( (SlotView) v , (d+i) * 1500 );
		}
	}
	
	private void spin(SlotView wheel, int time) {
		wheel.scroll(-350 + (int)(Math.random() * 50), time);
	}
	

	// Wheel scrolled listener
	OnSlotScrollListener scrolledListener = new OnSlotScrollListener() {
		@Override
		public void onScrollingStarted(SlotView wheel) {
			spinning++;
			if(scrollListener != null && spinning == 1)
				scrollListener.onSpinningStarted();
		}
		@Override
		public void onScrollingFinished(SlotView wheel) {
			//animateSelectedItem(wheel);
			spinning--;
			if(scrollListener != null && isFinished()){
				scrollListener.onSpinningFinished();
				checkForPrize();
			}
		}
				
	};
		
	// Wheel changed listener
	private OnSlotChangedListener changedListener = new OnSlotChangedListener() {
		@Override
		public void onChanged(SlotView wheel, int oldValue, int newValue) {
			if(isFinished())
				checkForPrize();
		}
	};
	
	
	@SuppressWarnings("unused")
	private void animateSelectedItem(SlotView wheel) {
		View v= wheel.getMiddleItem();
		Animation a = loadAnimation();
		v.startAnimation(a);
	}
	
	
	private boolean isFinished() {
		return spinning == 0;	 
	}
	
	private void checkForPrize() {
		if(scrollListener != null){
			if(isAWinner())
				scrollListener.onWon();
			else
				scrollListener.onLost();
		}			
	}
	
	/**
	 * Check if spin is a winning spin
	 */
	private boolean isAWinner() {
		int index = -1;
		for(int i = 0; i < getChildCount(); i++)
		{
			View v =  getChildAt(i);
			if(v instanceof SlotView ){
				int val = ((SlotView) v).getCurrentItem();
				if( index == -1 ){
					index = val;
				}else if(index != val){
					return false;
				}
			}
		}
		return index != -1;
	}

	public int getPrize() {
		for(int i = 0; i < getChildCount(); i++)
		{
			View v =  getChildAt(i);
			if(v instanceof SlotView ){
				if(v instanceof SlotView )
					return ((SlotView) v).getCurrentItem();
			}
		}
		return -1;
	}

	//load animation for spinning end
	private Animation loadAnimation() {
		return AnimationUtils.loadAnimation(getContext(), R.anim.pop_in);
	}
	
	//Get Snapshot from view for History
	public Bitmap getSnapShot() {
		buildDrawingCache(); 
	    return Bitmap.createBitmap(getDrawingCache());
	}
	
}

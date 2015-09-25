package mo.flypay.fruitmachine;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import mo.flypay.fruitmachine.datasource.DummySource;
import mo.flypay.fruitmachine.datasource.Source;
import mo.flypay.fruitmachine.model.HistoryItem;
import mo.flypay.fruitmachine.util.BitmapUtil;
import mo.flypay.fruitmachine.views.SlotMachine;
import mo.flypay.fruitmachine.wheel.widget.adapters.ResultAdapter;
import mo.flypay.fruitmachine.wheel.widget.interfaces.OnSlotMachineListener;

public class Activity_FruitMachine extends Activity implements OnSlotMachineListener {

	private static final String LOST = "LOST";
	private static final String WON = "WON A FREE ";

	final int DIALOG_LAYOUT_ID = R.layout.dialog;

	private SlotMachine slotMachine;
	private Button spin;
	private ListView listView;
	private ArrayList<HistoryItem> history = new ArrayList<HistoryItem>();
	private ResultAdapter resultAdapter;
	private Source source;
	private Dialog dialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.slot_machine_layout);
		
		spin = (Button)findViewById(R.id.btn_mix);
		slotMachine = (SlotMachine) findViewById(R.id.slot_machine);
		listView = (ListView) findViewById(R.id.listView1);
		resultAdapter = new ResultAdapter(this, history);
		
		listView.setEmptyView(findViewById(R.id.no_history));
		listView.setAdapter(resultAdapter);
			
		spin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				slotMachine.spin();
			}
		});
		
		initMachine();
	}
	

	private void initMachine() {
		source = new DummySource(this);
		slotMachine.setSlotListener(this);
		slotMachine.setItemsSource(source);
		slotMachine.setNoOfSlots(3);
		slotMachine.setup();
	}

	//Work on supporting Oriientation Change
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        //Work around for orientation - until implementing layout-land design
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
    		((LinearLayout) findViewById(R.id.LinearLayout1)).setOrientation(LinearLayout.VERTICAL);
        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
    		((LinearLayout) findViewById(R.id.LinearLayout1)).setOrientation(LinearLayout.HORIZONTAL);
        }
       
    }
	
	
	@Override
	public void onSpinningStarted() {
		spin.setEnabled(false);
	}
	@Override
	public void onSpinningFinished() {
		spin.setEnabled(true);
	}

	@Override
	public void onWon() {
		won();
	}
	
	@Override
	public void onLost() {
		lost();
	}

	private void won() {
		int prizeIndex = slotMachine.getPrize();
		String prizeName = source.getFruit(prizeIndex).getName();
		addToHistory(WON + prizeName);
		showWonDialog(prizeIndex, prizeName);
	}

	private void lost() {
		addToHistory(LOST);
		showLostDialog();
	}

	private void showWonDialog(int prizeIndex, String prizeName) {
		showResultDialog("Free "+prizeName, "CLAIM!", source.getFruit(prizeIndex).getImage(), R.drawable.button_claim);				
	}
	
	private void showLostDialog() {
		showResultDialog("Try Again","PLAY AGAIN", BitmapUtil.getBitmap(this,R.drawable.img_try_again) , R.drawable.button_tryagain);				
	}

	private void addToHistory(String result) {
		Bitmap b = 	slotMachine.getSnapShot();
		HistoryItem item = new HistoryItem(b, result);
		history.add(0,item);
		resultAdapter.notifyDataSetChanged();
		listView.setSelection(0);
	}

	//Create Custom Alert-Dialog
	private void showResultDialog(String title, String buttonTxt, Bitmap bitmap, int buttonBack) {
		dialog = new Dialog(this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(DIALOG_LAYOUT_ID);
		TextView text = (TextView) dialog.findViewById(R.id.textView1);
		Button dialogButton = (Button) dialog.findViewById(R.id.button1);
		ImageView image = (ImageView) dialog.findViewById(R.id.imageView1);


		text.setText(title);
		
		image.setImageBitmap(bitmap);
		image.setAnimation(loadAnimation());
		
		dialogButton.setText(buttonTxt);
		dialogButton.setBackgroundResource(buttonBack);

		// if button is clicked, close the dialog
		dialogButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		dialog.show();
	}

	private Animation loadAnimation() {
		return AnimationUtils.loadAnimation(this, R.anim.pop_in);
	}
		
}

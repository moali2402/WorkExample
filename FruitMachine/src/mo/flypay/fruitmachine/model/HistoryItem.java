package mo.flypay.fruitmachine.model;

import java.util.Date;

import android.graphics.Bitmap;
import android.text.format.DateFormat;

/**
 * @author Mohamed
 *
 */
public class HistoryItem {
	
	private Date date;
	private Bitmap snapshot;
	private String result; 
	
	/**
     * Constructor for HistoryItem
     */
    public HistoryItem(Bitmap snapshot, String result)
    {
        this.date = new Date();
        this.snapshot = snapshot;
        this.result = result;
    }
    

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the snapshot
	 */
	public Bitmap getSnapshot() {
		return snapshot;
	}

	/**
	 * @param snapshot the snapshot to set
	 */
	public void setSnapshot(Bitmap snapshot) {
		this.snapshot = snapshot;
	}

	/**
	 * @return the result
	 */
	public String getResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(String result) {
		this.result = result;
	}

	/**
	 * String representation of History Item
	 */
	@Override
    public String toString(){
        return "Date: " +DateFormat.format("dd/MM/yyyy HH:mm", date) + "\nResult:  " + result;
    }

}

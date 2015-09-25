package dev.vision.shopping.center;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.google.android.gms.internal.bl;

import dev.vision.shopping.center.Fragments.CarLocationFragment;
import dev.vision.shopping.center.se.anyro.nfc_reader.NdefMessageParser;
import dev.vision.shopping.center.se.anyro.nfc_reader.record.CityStarsNFC;
import dev.vision.shopping.center.se.anyro.nfc_reader.record.ParsedNdefRecord;
import dev.vision.shopping.center.se.anyro.nfc_reader.record.SmartPoster;
import dev.vision.shopping.center.se.anyro.nfc_reader.record.TextRecord;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.MifareUltralight;
import android.os.Parcelable;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class NFC {

	private static NfcAdapter mAdapter;
	private static boolean SUPPORTED;
	private static PendingIntent mPendingIntent;
	private static NdefMessage mNdefPushMessage;
	private static Context c;

	public static NfcAdapter getInstance(Context c){
		NFC.c = c;
		if(mAdapter == null){
			mAdapter = NfcAdapter.getDefaultAdapter(c);
			if(mAdapter != null){
				setup();
			}
		}
		if(mAdapter != null){
			SUPPORTED = true;
		}
        return mAdapter;
	}
	
	public static boolean isSupported(Context c){
		NfcAdapter mAdapter = NfcAdapter.getDefaultAdapter(c);
	    return mAdapter != null;
	}

	private static void setup(){

		mPendingIntent = PendingIntent.getActivity(c, 0,
	                new Intent(c, ((CustomAnimation)c).getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
	    mNdefPushMessage = new NdefMessage(new NdefRecord[] { newTextRecord("Message from NFC Reader :-)", Locale.ENGLISH, true) });
	       
        mAdapter.setNdefPushMessage(mNdefPushMessage, ((Activity)c));
	}

    public static NdefRecord newTextRecord(String text, Locale locale, boolean encodeInUtf8) {
        byte[] langBytes = locale.getLanguage().getBytes(Charset.forName("US-ASCII"));

        Charset utfEncoding = encodeInUtf8 ? Charset.forName("UTF-8") : Charset.forName("UTF-16");
        byte[] textBytes = text.getBytes(utfEncoding);

        int utfBit = encodeInUtf8 ? 0 : (1 << 7);
        char status = (char) (utfBit + langBytes.length);

        byte[] data = new byte[1 + langBytes.length + textBytes.length];
        data[0] = (byte) status;
        System.arraycopy(langBytes, 0, data, 1, langBytes.length);
        System.arraycopy(textBytes, 0, data, 1 + langBytes.length, textBytes.length);

        return new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, new byte[0], data);
    }
    

    public static void resolve(Intent intent) {
        String action = intent.getAction();
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
            Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            NdefMessage[] msgs;
            if (rawMsgs != null) {
                msgs = new NdefMessage[rawMsgs.length];
                for (int i = 0; i < rawMsgs.length; i++) {
                    msgs[i] = (NdefMessage) rawMsgs[i];
                }
            } else {
                // Unknown tag type
                byte[] empty = new byte[0];
                byte[] id = intent.getByteArrayExtra(NfcAdapter.EXTRA_ID);
                Parcelable tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
                byte[] payload = dumpTagData(tag).getBytes();
                NdefRecord record = new NdefRecord(NdefRecord.TNF_UNKNOWN, empty, id, payload);
                NdefMessage msg = new NdefMessage(new NdefRecord[] { record });
                msgs = new NdefMessage[] { msg };
            }
            // Setup the views
            buildTagViews(msgs);
        }
    }

    private static String dumpTagData(Parcelable p) {
        StringBuilder sb = new StringBuilder();
        Tag tag = (Tag) p;
        byte[] id = tag.getId();
        sb.append("Tag ID (hex): ").append(getHex(id)).append("\n");
        sb.append("Tag ID (dec): ").append(getDec(id)).append("\n");
        sb.append("ID (reversed): ").append(getReversed(id)).append("\n");

        String prefix = "android.nfc.tech.";
        sb.append("Technologies: ");
        for (String tech : tag.getTechList()) {
            sb.append(tech.substring(prefix.length()));
            sb.append(", ");
        }
        sb.delete(sb.length() - 2, sb.length());
        for (String tech : tag.getTechList()) {
            if (tech.equals(MifareClassic.class.getName())) {
                sb.append('\n');
                MifareClassic mifareTag = MifareClassic.get(tag);
                String type = "Unknown";
                switch (mifareTag.getType()) {
                case MifareClassic.TYPE_CLASSIC:
                    type = "Classic";
                    break;
                case MifareClassic.TYPE_PLUS:
                    type = "Plus";
                    break;
                case MifareClassic.TYPE_PRO:
                    type = "Pro";
                    break;
                }
                sb.append("Mifare Classic type: ");
                sb.append(type);
                sb.append('\n');

                sb.append("Mifare size: ");
                sb.append(mifareTag.getSize() + " bytes");
                sb.append('\n');

                sb.append("Mifare sectors: ");
                sb.append(mifareTag.getSectorCount());
                sb.append('\n');

                sb.append("Mifare blocks: ");
                sb.append(mifareTag.getBlockCount());
            }

            if (tech.equals(MifareUltralight.class.getName())) {
                sb.append('\n');
                MifareUltralight mifareUlTag = MifareUltralight.get(tag);
                String type = "Unknown";
                switch (mifareUlTag.getType()) {
                case MifareUltralight.TYPE_ULTRALIGHT:
                    type = "Ultralight";
                    break;
                case MifareUltralight.TYPE_ULTRALIGHT_C:
                    type = "Ultralight C";
                    break;
                }
                sb.append("Mifare Ultralight type: ");
                sb.append(type);
            }
        }

        return sb.toString();
    }

    private static String getHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = bytes.length - 1; i >= 0; --i) {
            int b = bytes[i] & 0xff;
            if (b < 0x10)
                sb.append('0');
            sb.append(Integer.toHexString(b));
            if (i > 0) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    private static long getDec(byte[] bytes) {
        long result = 0;
        long factor = 1;
        for (int i = 0; i < bytes.length; ++i) {
            long value = bytes[i] & 0xffl;
            result += value * factor;
            factor *= 256l;
        }
        return result;
    }

    private static long getReversed(byte[] bytes) {
        long result = 0;
        long factor = 1;
        for (int i = bytes.length - 1; i >= 0; --i) {
            long value = bytes[i] & 0xffl;
            result += value * factor;
            factor *= 256l;
        }
        return result;
    }

    static void buildTagViews(NdefMessage[] msgs) {
        if (msgs == null || msgs.length == 0) {
            return;
        }

        // Parse the first message in the list
        // Build views for all of the sub records
        //Date now = new Date();
        List<ParsedNdefRecord> records = NdefMessageParser.parse(msgs[0]);
        final int size = records.size();
        for (int i = 0; i < size; i++) {
            //TextView timeView = new TextView(this);
            //timeView.setText(TIME_FORMAT.format(now));
            //content.addView(timeView, 0);
            ParsedNdefRecord record = records.get(i);
            if(record.isCityStars()){
            	CityStarsNFC csnfc = record.getCityStars();
            	if(csnfc != null && c instanceof CustomAnimation){
            		Static.saveLocation(csnfc);
					Fragment car_lo = CarLocationFragment.NewInstance(csnfc);
					((CustomAnimation) c).switchContent(car_lo);			
            	}
            }
            //content.addView(record.getView(this, inflater, content, i), 1 + i);
            //content.addView(inflater.inflate(R.layout.tag_divider, content, false), 2 + i);
        }
    }
    

    private static void showWirelessSettingsDialog() {    	
        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle(R.string.nfc_title);
        builder.setMessage(R.string.nfc_disabled);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                c.startActivity(intent);
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                //finish();
            }
        });
        builder.create().show();
        return;
    }

	private static void isEnabled(Context c) {
		if(!mAdapter.isEnabled())
			showWirelessSettingsDialog();
	}

	public static void onResume(Context c) {
		if(SUPPORTED){
			isEnabled(c);
	        mAdapter.enableForegroundDispatch((Activity) c, mPendingIntent, null, null);	
		}		
	}
	
	public static void onPause(Context c) {
		if(SUPPORTED){
            mAdapter.disableForegroundDispatch((Activity) c);
            //mAdapter.disableForegroundNdefPush(this);
        }		
	}

}
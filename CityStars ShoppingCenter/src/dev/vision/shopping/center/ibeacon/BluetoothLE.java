package dev.vision.shopping.center.ibeacon;

import java.util.Arrays;

import org.altbeacon.beacon.*;

import android.content.Context;

public class BluetoothLE {
	public void transmit(Context c){
		Beacon beacon = new Beacon.Builder()
		                .setId1("00000000-7777-5555-3333-000000000000")
		                .setId2("1")
		                .setId3("2")
		                .setManufacturer(0x004c)
		                .setTxPower(-59)
		                .setDataFields(Arrays.asList(new Long[]{0l}))
		                .build();
	
		BeaconTransmitter mBeaconTransmitter = new BeaconTransmitter(c, new BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"));
        mBeaconTransmitter.startAdvertising(beacon);
	}
}


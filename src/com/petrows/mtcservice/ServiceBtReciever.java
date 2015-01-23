package com.petrows.mtcservice;

import java.util.ArrayList;
import java.util.Date;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.provider.ContactsContract;
import android.text.format.Time;

public class ServiceBtReciever extends BroadcastReceiver {

	public static ServiceBtReciever dataInst = null;

	public class BtHistoryRecord {
		String phone;
		String contactName;
		Date callDate;
		public BtHistoryRecord(String ph, Date t) { phone = ph; callDate = t; }
	}

	public static ArrayList<BtHistoryRecord> historyData = new ArrayList<BtHistoryRecord>();
	public static int historyDataRev = 0;

	public void genTestData(Context ctx) {
		if (null == dataInst)
			dataInst = new ServiceBtReciever();
		historyDataRev++;
		historyData.clear();
		
		Date tmNow = new Date();
		// tmNow.
		
		historyData.add(new BtHistoryRecord("+78001002424", tmNow));
		historyData.add(new BtHistoryRecord("1002424", tmNow));
		historyData.add(new BtHistoryRecord("+78001002424", tmNow));
		historyData.add(new BtHistoryRecord("1234567", tmNow));
		historyData.add(new BtHistoryRecord("88001002424", tmNow));
	}

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		// TODO Auto-generated method stub

	}

	public static String FormatStringAsPhoneNumber(String input) {
		input = input.replaceAll("[^\\d]", "");
		String output;
		switch (input.length()) {
		case 7:
			output = String.format("%s-%s", input.substring(0, 3),
					input.substring(3, 7));
			break;
		case 10:
			output = String.format("(%s) %s-%s", input.substring(0, 3),
					input.substring(3, 6), input.substring(6, 10));
			break;
		case 11:
			output = String.format("+%s (%s) %s-%s", input.substring(0, 1),
					input.substring(1, 4), input.substring(4, 7),
					input.substring(7, 11));
			break;
		default:
			return input;
		}
		return output;
	}
	
	public static String getContactDisplayNameByNumber(Context ctx, String number) {
	    Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number));
	    String name = "";

	    ContentResolver contentResolver = ctx.getContentResolver();
	    Cursor contactLookup = contentResolver.query(uri, new String[] {BaseColumns._ID,
	            ContactsContract.PhoneLookup.DISPLAY_NAME }, null, null, null);

	    try {
	        if (contactLookup != null && contactLookup.getCount() > 0) {
	            contactLookup.moveToNext();
	            name = contactLookup.getString(contactLookup.getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
	            //String contactId = contactLookup.getString(contactLookup.getColumnIndex(BaseColumns._ID));
	        }
	    } finally {
	        if (contactLookup != null) {
	            contactLookup.close();
	        }
	    }

	    return name;
	}

}

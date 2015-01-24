package com.petrows.mtcservice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.microntek.mtcser.BTServiceInf;
import android.net.Uri;
import android.os.IBinder;
import android.os.RemoteException;
import android.provider.BaseColumns;
import android.provider.ContactsContract;

import org.apache.http.impl.cookie.DateParseException;
import org.apache.http.impl.cookie.DateUtils;

import android.telephony.PhoneNumberUtils;
import android.util.Log;

public class ServiceBtReciever extends BroadcastReceiver {
	final static String TAG = "ServiceBtReciever";

	public Context context;
	
	public class BtHistoryRecord {
		String phone;
		Date callDate;
		public BtHistoryRecord(String ph, Date t) 
		{ 
			phone = ph; 
			callDate = t;
		}
	}
	public ArrayList<BtHistoryRecord> historyData = new ArrayList<BtHistoryRecord>();
	public int historyDataRev = 0;
	
	public class BtPhonebookRecord {
		String name;
		String phone;
		public BtPhonebookRecord(String n, String ph) 
		{
			name = n;
			phone = ph; 
		}
	}
	public ArrayList<BtPhonebookRecord> phonebookData = new ArrayList<BtPhonebookRecord>();
	
	public void setHistoryData(List<String> histRaw)
	{
		historyDataRev++;
		historyData.clear();
		Log.d(TAG, "Loading " + String.valueOf(histRaw.size()) + " as history data!");
		for (int x=0; x<histRaw.size(); x++)
		{
			String[] recordParts = histRaw.get(x).split("\\^");
			if (recordParts.length < 4) continue;
			Date dt = new Date();
		    try {
		    	dt = DateUtils.parseDate(recordParts[2] + " " + recordParts[3], new String[]{"yyyy/MM/dd HH:mm:ss"}, null);
			} catch (DateParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    String phoneNumber = recordParts[1].replaceAll("[^\\d\\+]", "");
		    historyData.add(0, new BtHistoryRecord(phoneNumber, dt));
		}
	}
	
	private static ServiceBtReciever instance;
	public static ServiceBtReciever get(Context ctx) {
		if (null == instance)
			instance = new ServiceBtReciever(ctx);
		return instance;
	}

	public static void destroy() {
		instance = null;
	}
	
	BTServiceInf btInterface = null;
	
	private ServiceConnection serviceConnection = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {

			btInterface = BTServiceInf.Stub.asInterface(service);
			try {
				btInterface.init();
				// Update list
				updateHistory(); // Gen test data?
				updatePhoneBook(); // Request phonebook update
			} catch (RemoteException e) {				
				e.printStackTrace();
				Log.d(TAG, "Error " + e.getMessage());
			}
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			btInterface = null;
		}
	};

	@Override
	public void onReceive(Context ctx, Intent intent) {		
		if (intent.getAction().equals("com.microntek.bt.report"))
		{
			if (intent.hasExtra("phonebook_record"))
			{
				String[] bookRecord = intent.getStringExtra("phonebook_record").split("\\^");
				if (bookRecord.length > 1)
				{
					String phoneNumber = bookRecord[1].replaceAll("[^\\d\\+]", "");
					phonebookData.add(new BtPhonebookRecord(bookRecord[0], phoneNumber));
				}
			}
			if (intent.hasExtra("phonebook_end"))
			{
				// Show The Data
				Log.d(TAG, "Updated phonebook, records = " + String.valueOf(phonebookData.size()));
				notifyWidgets();
			}				
		}
		if (intent.getAction().equals("com.microntek.bootcheck"))
		{
			String str = intent.getStringExtra("class");
		    if (str.equals("com.microntek.bluetooth") || str.equals("phonecallin") || str.equals("phonecallout"))
		    {
		    	updateHistory(); // Update our history
		    }
		}
	}
	
	public void updateHistory()
	{
		Log.d(TAG, "Updating history...");
		if (null != btInterface)
		{
			try {
				List<String> histRaw = btInterface.getHistoryList();
				setHistoryData(histRaw);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			// Test gen....
			ArrayList<String> tst = new ArrayList<String>();
			tst.add("000CE784500B^+1(502)657-6033^2014/12/28^15:54:57^1");
			tst.add("000CE784500B^+79111234567^2014/12/27^13:13:15^1");
			tst.add("000CE784500B^88001002424^2014/12/28^13:51:52^1");
			tst.add("000CE784500B^+79525553322^2014/12/28^15:54:57^1");
			tst.add("000CE784500B^1112233^2014/12/28^15:54:57^1");
			tst.add("000CE784500B^+1(502)657-6033^2014/12/28^15:54:57^1");
			tst.add("000CE784500B^+1(502)657-6033^2014/12/28^15:54:57^1");
			setHistoryData(tst);
		}
		notifyWidgets(); // kick widgets
	}
	
	public void updatePhoneBook()
	{
		phonebookData.clear();
		try {
			btInterface.syncPhonebook();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void notifyWidgets()
	{
		// Call update all widgets...
		Intent updIntent = new Intent("com.petrows.mtcservice.updatewidgets");
		context.sendBroadcast(updIntent);
	}
	
	private ServiceBtReciever(Context ctx)
	{
		context = ctx;
		
		IntentFilter intf = new IntentFilter();
		intf.addAction( "com.microntek.bootcheck" );
		intf.addAction( "com.microntek.bt.report" );
		context.registerReceiver(this, intf);
		
		try {
			if (!context.bindService(new Intent("com.microntek.btserver"), this.serviceConnection, 1))
			{		
				Log.d(TAG, "Bind error!");
				updateHistory(); // Gen test data?
			} else {
				// Will try to call on ServiceConnected!
			}
		} catch (Exception e) {
			Log.e(TAG, "Service exception " + e.getLocalizedMessage());
		}
	}
	
	public static String FormatStringAsPhoneNumber(String input) {
		if (input.isEmpty()) return "?";
		String leadPlus = "";
		if (input.charAt(0) == '+')
		{
			leadPlus = "+";
			input = input.substring(1);
		} else {
			if (input.charAt(0) == '8' && input.length() == 11)
			{
				// Russian fuck-up number fix
				input = '7' + input.substring(1);
				leadPlus = "+";
			}
		}
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
			output = String.format("%s (%s) %s-%s", input.substring(0, 1),
					input.substring(1, 4), input.substring(4, 7),
					input.substring(7, 11));
			break;
		default:
			return input;
		}
		return leadPlus + output;
	}
	
	public String getContactDisplayNameByNumber(String number) {
	    Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number));
	    String name = "";

	    ContentResolver contentResolver = context.getContentResolver();
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
	    
	    if (name.isEmpty())
	    {
	    	// Try to search in local (BT) phonebook
	    	for (BtPhonebookRecord rec : phonebookData)
	    	{
	    		if (PhoneNumberUtils.compare(rec.phone, number))
	    		{
	    			// Found!
	    			return rec.name;
	    		}
	    	}
	    }

	    return name;
	}

}

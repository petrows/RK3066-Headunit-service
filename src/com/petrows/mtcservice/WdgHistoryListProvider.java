package com.petrows.mtcservice;

import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

import android.appwidget.AppWidgetManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.provider.ContactsContract;
import android.telephony.PhoneNumberUtils;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.text.method.DateTimeKeyListener;
import android.util.Base64;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService.RemoteViewsFactory;

public class WdgHistoryListProvider implements RemoteViewsFactory {
	final String TAG = "HistoryListProvider";
	
	public String getContactDisplayNameByNumber(Context ctx, String number) {
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

	private Context context = null;
	private int appWidgetId;
	
	public WdgHistoryListProvider(Context context, Intent intent) {
		this.context = context;
		appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
				AppWidgetManager.INVALID_APPWIDGET_ID);
		
		Log.d(TAG, "Created new provider");
	}
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		Log.d(TAG, "On create!");
	}

	@Override
	public void onDataSetChanged() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public int getCount() {
		Log.d(TAG, "Get item count!");
		return ServiceBtReciever.historyData.size();
	}

	@Override
	public RemoteViews getViewAt(int position) {

		final RemoteViews remoteView = new RemoteViews(
				context.getPackageName(), R.layout.historywdg_list_fragment);
				
		ServiceBtReciever.BtHistoryRecord rec = ServiceBtReciever.historyData.get(position);
		DateFormat s;
		remoteView.setTextViewText(R.id.wdgHistoryName, ServiceBtReciever.getContactDisplayNameByNumber(this.context, rec.phone));
		remoteView.setTextViewText(R.id.wdgHistoryPhone, ServiceBtReciever.FormatStringAsPhoneNumber(rec.phone));
		//remoteView.setTextViewText(R.id.wdgHistoryPhone, rec.phone);
		// remoteView.setTextViewText(R.id.wdgHistoryDate, "08:30 AM");
		Log.d("pws", "Item at " + position);

		return remoteView;
	}

	@Override
	public RemoteViews getLoadingView() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub		
		return 1;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

}

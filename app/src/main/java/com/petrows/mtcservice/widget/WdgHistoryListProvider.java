package com.petrows.mtcservice;

import android.appwidget.AppWidgetManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.provider.ContactsContract;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService.RemoteViewsFactory;

import com.petrows.mtcservice.receiver.BtReceiver;

public class WdgHistoryListProvider implements RemoteViewsFactory {
	final String TAG = "HistoryListProvider";

	public String getContactDisplayNameByNumber(Context ctx, String number) {
		Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number));
		String name = "";

		ContentResolver contentResolver = ctx.getContentResolver();
		Cursor contactLookup = contentResolver.query(uri, new String[]{BaseColumns._ID,
				ContactsContract.PhoneLookup.DISPLAY_NAME}, null, null, null);

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
		Log.d(TAG, "On create!");
	}

	@Override
	public void onDataSetChanged() {}

	@Override
	public void onDestroy() {
	}

	@Override
	public int getCount() {
		if (null == BtReceiver.get(context)) {
			// No data
			Log.e(TAG, "Service is not active!");
			return 0;
		}

		return BtReceiver.get(context).historyData.size();
	}

	@Override
	public RemoteViews getViewAt(int position) {

		final RemoteViews remoteView = new RemoteViews(
				context.getPackageName(), R.layout.historywdg_list_fragment);

		if (null == BtReceiver.get(context)) {
			// No data
			Log.e(TAG, "Service is not active!");
			return remoteView;
		}

		BtReceiver.BtHistoryRecord rec = BtReceiver.get(context).historyData.get(position);
		remoteView.setTextViewText(R.id.wdgHistoryPhone, BtReceiver.FormatStringAsPhoneNumber(rec.phone));
		String name = BtReceiver.get(context).getContactDisplayNameByNumber(rec.phone);
		if (name.isEmpty()) {
			name = context.getString(R.string.wdg_history_unknown_contact);
		}
		remoteView.setTextViewText(R.id.wdgHistoryName, name);
		remoteView.setTextViewText(R.id.wdgHistoryDate, DateFormat.getTimeFormat(context).format(rec.callDate));

		Intent fillInIntent = new Intent();
		fillInIntent.setData(Uri.parse("tel:" + rec.phone));
		remoteView.setOnClickFillInIntent(R.id.wdgHistoryName, fillInIntent);
		remoteView.setOnClickFillInIntent(R.id.wdgHistoryPhone, fillInIntent);
		remoteView.setOnClickFillInIntent(R.id.wdgHistoryDate, fillInIntent);

		Log.d("pws", "Item at " + position);

		return remoteView;
	}

	@Override
	public RemoteViews getLoadingView() {
		return null;
	}

	@Override
	public int getViewTypeCount() {
		return 1;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

}

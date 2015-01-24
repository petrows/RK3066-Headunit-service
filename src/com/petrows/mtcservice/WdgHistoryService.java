package com.petrows.mtcservice;

import java.io.UnsupportedEncodingException;

import org.json.JSONException;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.widget.RemoteViewsService;

public class WdgHistoryService extends RemoteViewsService {
	final String TAG = "HistoryService";
	@Override
	public RemoteViewsFactory onGetViewFactory(Intent intent) {
		int appWidgetId = intent.getIntExtra(
				AppWidgetManager.EXTRA_APPWIDGET_ID,
				AppWidgetManager.INVALID_APPWIDGET_ID);
		
		ServiceBtReciever.get(this.getApplicationContext());

		return new WdgHistoryListProvider(this.getApplicationContext(), intent);
	}

	
}

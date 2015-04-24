package com.petrows.mtcservice.widget;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.widget.RemoteViewsService;

import com.petrows.mtcservice.WdgHistoryListProvider;
import com.petrows.mtcservice.receiver.BtReceiver;

public class WdgHistoryService extends RemoteViewsService {
	final String TAG = "HistoryService";

	@Override
	public RemoteViewsFactory onGetViewFactory(Intent intent) {
		int appWidgetId = intent.getIntExtra(
				AppWidgetManager.EXTRA_APPWIDGET_ID,
				AppWidgetManager.INVALID_APPWIDGET_ID);

		BtReceiver.get(this.getApplicationContext());

		return new WdgHistoryListProvider(this.getApplicationContext(), intent);
	}


}

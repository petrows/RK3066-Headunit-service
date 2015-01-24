package com.petrows.mtcservice;

import java.util.ArrayList;
import java.util.Arrays;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;

public class WdgHistoryWidget extends AppWidgetProvider {
	final String TAG = "HistoryWidget";
	public final String ActionUpdateAll = "UPDATEHISTORY";
		
	static ArrayList<Integer> widgetIds = new ArrayList<Integer>();
	
	public void onUpdateAll(Context context)
	{
		Log.d(TAG, "Update all widgets");
		
		// ServiceBtReciever.get(context).genTestData();
		
		int[] ret = new int[widgetIds.size()];
	    for (int i=0; i < ret.length; i++)
	    {
	        ret[i] = widgetIds.get(i).intValue();
	        Log.d(TAG, "Will update: " + String.valueOf(widgetIds.get(i).intValue()));
	        AppWidgetManager.getInstance(context).notifyAppWidgetViewDataChanged(widgetIds.get(i).intValue(), R.id.wdgHistoryMainList);
	    }
	}
	
	@Override
	public void onReceive(Context context, Intent intent) {
		super.onReceive(context, intent);
		// Update all widgets...		
		onUpdateAll(context);
	}

	@Override
	public void onEnabled(Context context) {
		super.onEnabled(context);
		Log.d(TAG, "onEnabled");
	}
	
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		Log.d(TAG, "onUpdate " + Arrays.toString(appWidgetIds));

		for (int widgetID : appWidgetIds) {
			
			// Add widget ID to our list
			if (!widgetIds.contains(widgetID)) widgetIds.add(widgetID);
			Log.d(TAG, "Update: " + widgetID);
						
			// Widget layout
			RemoteViews widgetView = new RemoteViews(context.getPackageName(),
					R.layout.historywdg_layout);
			
			// RemoteViews Service needed to provide adapter for ListView
			Intent svcIntent = new Intent(context, WdgHistoryService.class);
			
			// passing app widget id to that RemoteViews Service
			svcIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetID);
			
			// setting a unique Uri to the intent
			// don't know its purpose to me right now
			svcIntent.setData(Uri.parse(svcIntent
					.toUri(Intent.URI_INTENT_SCHEME)));
			
			// setting adapter to listview of the widget
			widgetView.setRemoteAdapter(R.id.wdgHistoryMainList, svcIntent);
			
			// setting an empty view in case of no data
			// widgetView.setEmptyView(R.id.listView1, R.id.);

			// Update widget
			appWidgetManager.updateAppWidget(widgetID, widgetView);
			// appWidgetManager.notifyAppWidgetViewDataChanged(widgetID, R.id.wdgHistoryMainList);
		}
	}
	
	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		super.onDeleted(context, appWidgetIds);
		Log.d(TAG, "onDeleted " + Arrays.toString(appWidgetIds));
		for (int widgetID : appWidgetIds) {
			for (Integer x=0; x<widgetIds.size(); x++)
			{
				if (widgetIds.get(x) == widgetID)
				{
					widgetIds.remove(x);
					break;
				}
			}
		}
	}

	@Override
	public void onDisabled(Context context) {
		super.onDisabled(context);
		Log.d(TAG, "onDisabled");
	}
}

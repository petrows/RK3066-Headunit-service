package com.petrows.mtcservice;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RemoteViews;
import android.widget.TextView;

public class NotificationService extends NotificationListenerService {

    private final static String TAG = "NotificationService";

    public static boolean isInit = false;

    @Override
    public void onCreate() {
        if( isInit ) return;

        isInit = true;

        Log.d(TAG, "NotificationService onCreate");

        Settings.get(this).startMyServices();
    }

    @Override
    public void onDestroy() {
        isInit = false;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if( Settings.getServiceEnable() ) {
            Log.d(TAG, "MTCService Notification onStartCommand");

            Settings.get( this ).announce( this, startId );

            return( START_STICKY );
        }
        stopSelf();
        return( START_NOT_STICKY );
    }

    public void onListenerConnected() {
        for( StatusBarNotification sbn: getActiveNotifications() ) {
            Settings.get( null ).showToast( sbn.getPackageName() );
            Settings.get( null ).mySleep( 3000 );
        }
    }

    public void onNotificationPosted( StatusBarNotification sbn ) {
        if( sbn.getPackageName().equals( "com.maxmpz.audioplayer" ) ) {
            RemoteViews remoteView = sbn.getNotification().contentView;
            Context context = getApplicationContext();

            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ViewGroup localView = (ViewGroup) inflater.inflate(remoteView.getLayoutId(), null);
            remoteView.reapply(context, localView);
            TextView tv = (TextView) localView.findViewById(android.R.id.title);
            Settings.get( context ).showToast( tv.getText().toString() );
        }
        //Settings.get( getApplicationContext() ).showToast( new String( "==>" + sbn.getPackageName() + "<==" ) );
    }

    public void onNotificationRemoved( StatusBarNotification sbn ) {}

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return( null );
    }
}
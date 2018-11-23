package com.sn.railway.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.sn.railway.main.Splash_Activity;

import java.util.HashMap;
import java.util.Map;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    Map<String,String> map = new HashMap<String, String>();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        if (remoteMessage.getData().size() > 0) {
            map = remoteMessage.getData();
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            sendNotification(new HashMap<String, String>(map),remoteMessage.getNotification().getBody());
        }

    }

    private void sendNotification(HashMap<String,String> map, String messageBody) {
        Intent intent = new Intent(this, Splash_Activity.class);
        intent.putExtra("map", map);
        intent.setAction(System.currentTimeMillis()+"");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);



    }


}
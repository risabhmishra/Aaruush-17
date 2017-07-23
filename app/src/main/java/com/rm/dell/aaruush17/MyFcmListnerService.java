package com.rm.dell.aaruush17;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by Arko Chatterjee on 19-07-2017.
 */

public class MyFcmListnerService extends FirebaseMessagingService {
    private static String TAG = "MyFcmListenerService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Bundle bundle = new Bundle();
        bundle.putString("msgBody", remoteMessage.getNotification().getBody());

        Intent new_intent = new Intent();
        new_intent.setAction("ACTION_STRING_ACTIVITY");
        new_intent.putExtra("msg", bundle);

        sendBroadcast(new_intent);


    }
}

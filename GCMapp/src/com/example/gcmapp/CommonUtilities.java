package com.example.gcmapp;

import android.content.Context;
import android.content.Intent;



public final class CommonUtilities {

static final String SENDER_ID = "xxxxxxxxxxxxxx";

static final String SERVER_URL = "http://ec2-xxx-xx-xx-xx.compute-1.amazonaws.com:8080/gcm-demo";

static final String TAG = "GCMPushNotification";

static final String DISPLAY_MESSAGE_ACTION =
    "com.example.gcmapp.DISPLAY_MESSAGE";


static final String EXTRA_MESSAGE = "message";


static void displayMessage(Context context, String message) {
	Intent intent = new Intent(DISPLAY_MESSAGE_ACTION);
	intent.putExtra(EXTRA_MESSAGE, message);
	context.sendBroadcast(intent);
}



}

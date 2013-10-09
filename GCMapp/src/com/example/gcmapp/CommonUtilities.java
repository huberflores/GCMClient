package com.example.gcmapp;

import android.content.Context;
import android.content.Intent;


/*
 * SENDER_ID = https://code.google.com/apis/console/#project:662678XXXXXX:services
 */


public final class CommonUtilities {

//Modify this two variables	
static final String SENDER_ID = "662678XXXXXX";
static final String SERVER_URL = "http://ec2-xxx-xx-xx-xx.compute-1.amazonaws.com:8080/gcm-demo";

static final String TAG = "GCMPushNotification";

static final String DISPLAY_MESSAGE_ACTION = "com.example.gcmapp.DISPLAY_MESSAGE";

static final String EXTRA_MESSAGE = "message";

static void displayMessage(Context context, String message) {
	Intent intent = new Intent(DISPLAY_MESSAGE_ACTION);
	intent.putExtra(EXTRA_MESSAGE, message);
	context.sendBroadcast(intent);
}



}

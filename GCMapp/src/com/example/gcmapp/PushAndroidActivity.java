package com.example.gcmapp;

import com.google.android.gcm.GCMRegistrar;
import static com.example.gcmapp.CommonUtilities.DISPLAY_MESSAGE_ACTION;
import static com.example.gcmapp.CommonUtilities.EXTRA_MESSAGE;
import static com.example.gcmapp.CommonUtilities.SENDER_ID;
import static com.example.gcmapp.CommonUtilities.SERVER_URL;


import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class PushAndroidActivity extends Activity implements android.view.View.OnClickListener  {

	 TextView mDisplay;
	 AsyncTask<Void, Void, Void> mRegisterTask;
	 Button GCMRegister, GCMUnregister;

	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        checkNotNull(SERVER_URL, "SERVER_URL");
	        checkNotNull(SENDER_ID, "SENDER_ID");
	        // Make sure the device has the proper dependencies.
	        GCMRegistrar.checkDevice(this);
	        // Make sure the manifest was properly set - comment out this line
	        // while developing the app, then uncomment it when it's ready.
	        GCMRegistrar.checkManifest(this);
	        setContentView(R.layout.main);
	        
	        GCMRegister = (Button) findViewById(R.id.button1);
	        GCMRegister.setOnClickListener(this);
	        
	        GCMUnregister = (Button) findViewById(R.id.button2);
	        GCMUnregister.setOnClickListener(this);
	        
	        mDisplay = (TextView) findViewById(R.id.display);
	        registerReceiver(mHandleMessageReceiver,
	                new IntentFilter(DISPLAY_MESSAGE_ACTION));
	        
	    }
	     

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
       
            /*
            case R.id.options_register:
                GCMRegistrar.register(this, SENDER_ID);
                return true;
            case R.id.options_unregister:
                GCMRegistrar.unregister(this);
                return true;
             */
            case R.id.options_clear:
                mDisplay.setText(null);
                return true;
            case R.id.options_exit:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    
    private void checkNotNull(Object reference, String name) {
        if (reference == null) {
            throw new NullPointerException(
                    getString(R.string.error_config, name));
        }
    }
    
    private final BroadcastReceiver mHandleMessageReceiver =
        new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) {
        String newMessage = intent.getExtras().getString(EXTRA_MESSAGE);
        mDisplay.append(newMessage + "\n");
    }
    };
    
    @Override
    protected void onPause() {
    	/*if (mRegisterTask != null) {
            mRegisterTask.cancel(true);
        }*/
        //unregisterReceiver(mHandleMessageReceiver);
        //GCMRegistrar.onDestroy(this);
    	super.onPause();
    }
    
    @Override
    protected void onDestroy() {
        if (mRegisterTask != null) {
            mRegisterTask.cancel(true);
        }
        unregisterReceiver(mHandleMessageReceiver);
        GCMRegistrar.onDestroy(this);
        super.onDestroy();
    }

	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()){
    		case R.id.button1:  GCMRegistrar.register(this, SENDER_ID);
    							//mDisplay.append(getString(R.string.gcm_registered) + "\n");
    			break;
    			
    		case R.id.button2: GCMRegistrar.unregister(this);
    		unregisterReceiver(mHandleMessageReceiver);
    			break;
    		
		}
	}
   

    
}

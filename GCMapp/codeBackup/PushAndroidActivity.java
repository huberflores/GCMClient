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
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

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
	        
	        //Automatic Registration within the activity
	        /*final String regId = GCMRegistrar.getRegistrationId(this);
	        if (regId.equals("")) {
	            // Automatically registers application on startup.
	            GCMRegistrar.register(this, SENDER_ID);
	        } else {
	            // Device is already registered on GCM, needs to check if it is
	            // registered on our server as well.
	            if (GCMRegistrar.isRegisteredOnServer(this)) {
	                // Skips registration.
	                mDisplay.append(getString(R.string.already_registered) + "\n");
	               	                
	            } else {
	                // Try to register again, but not in the UI thread.
	                // It's also necessary to cancel the thread onDestroy(),
	                // hence the use of AsyncTask instead of a raw thread.
	                final Context context = this;
	                mRegisterTask = new AsyncTask<Void, Void, Void>() {

	                    @Override
	                    protected Void doInBackground(Void... params) {
	                        boolean registered =
	                                ServerUtilities.register(context, regId);
	                        // At this point all attempts to register with the app
	                        // server failed, so we need to unregister the device
	                        // from GCM - the app will try to register again when
	                        // it is restarted. Note that GCM will send an
	                        // unregistered callback upon completion, but
	                        // GCMIntentService.onUnregistered() will ignore it.
	                        if (!registered) {
	                            GCMRegistrar.unregister(context);
	                        }
	                        return null;
	                    }

	                    @Override
	                    protected void onPostExecute(Void result) {
	                        mRegisterTask = null; 
	                    }

	                };
	                mRegisterTask.execute(null, null, null);
	            }
	        }*/
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
             * Typically, an application registers automatically, so options
             * below are disabled. Uncomment them if you want to manually
             * register or unregister the device (you will also need to
             * uncomment the equivalent options on options_menu.xml).
             */
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
    	super.onPause();
    	//GCMRegistrar.unregister(this);
    }


	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()){
    		case R.id.button1:  GCMRegistrar.register(this, SENDER_ID);
    							//mDisplay.append(getString(R.string.gcm_registered) + "\n");
    			break;
    			
    		case R.id.button2: GCMRegistrar.unregister(getBaseContext());
    			break;
    		
		}
	}
   

    
}

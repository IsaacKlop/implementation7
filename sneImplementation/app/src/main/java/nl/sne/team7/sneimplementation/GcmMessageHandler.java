package nl.sne.team7.sneimplementation;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

public class GcmMessageHandler extends IntentService {

    private Handler handler;
    public GcmMessageHandler() {
        super("GcmMessageHandler");
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        handler = new Handler();
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();

        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        // The getMessageType() intent parameter must be the intent you received
        // in your BroadcastReceiver.
        String messageType = gcm.getMessageType(intent);

        MainActivity.uuid = extras.getString("uuid");
        MainActivity.username = extras.getString("username");

        showButtons();
        Log.i("GCM", "Received : (" +messageType+")  "+ extras.getString("uuid") + " : " + extras.getString("username"));

        GcmBroadcastReceiver.completeWakefulIntent(intent);

    }

    public void showButtons(){
        handler.post(new Runnable() {
            public void run() {
                Toast.makeText(getApplicationContext(),"Login request received, tap Accept or Decline." , Toast.LENGTH_LONG).show();
            }
        });

    }
}
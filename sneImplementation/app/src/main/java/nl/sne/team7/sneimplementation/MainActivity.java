package nl.sne.team7.sneimplementation;

import java.io.IOException;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends Activity implements OnClickListener {

    Button btnRegId;
    Button btnAccept;
    Button btnDecline;
    EditText editTextUsername;
    GoogleCloudMessaging gcm;
    String regid;
    String PROJECT_NUMBER = "1086996019341";

    public static String uuid;
    public static String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRegId = (Button) findViewById(R.id.btnGetRegId);
        btnAccept = (Button) findViewById(R.id.btnAccept);
        btnDecline = (Button) findViewById(R.id.btnDecline);
        editTextUsername = (EditText) findViewById(R.id.etUsername);

        btnRegId.setOnClickListener(this);
        btnAccept.setOnClickListener(this);
        btnDecline.setOnClickListener(this);
    }

    public void getRegId(){
        new AsyncTask<Void, Void, String>() {

                @Override
                protected String doInBackground(Void... params) {
                    String msg = "";
                    String inputUsername = editTextUsername.getText().toString();
                    try {
                        if (gcm == null) {
                            gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
                        }
                        regid = gcm.register(PROJECT_NUMBER);
                        msg = "Device registered, registration ID=" + regid + ", username = " + inputUsername;
                        Log.i("GCM",  msg);

                    } catch (IOException ex) {
                        msg = "Error :" + ex.getMessage();
                    }
                    register(regid, inputUsername);
                    return msg;
                }

        }.execute(null, null, null);

    }
    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnGetRegId:
                getRegId();
                break;
            case R.id.btnAccept:
                System.out.println("accept");

                if (uuid != null) {

                    new Thread(new Runnable() {
                        public void run() {
                            String method = "login";

                        try{
                            URL url = new URL("http://192.168.56.1:8080/GcmServer2/AppResponse");
                            URLConnection connection = url.openConnection();

                            Log.d("username", username);
                            Log.d("uuid", uuid);

                            connection.setDoOutput(true);
                            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());

                            out.write(method + ":" + username + ":" + uuid);
                            out.close();

                            Log.d("Connection", "Succesful");

                            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                            in.close();

                        }catch(Exception e)
                        {
                            Log.d("Exception",e.toString());
                        }
                    }
                }).start();

                }
                break;
            case R.id.btnDecline:
                System.out.println("decline");
                if (uuid != null) {

                    new Thread(new Runnable() {
                        public void run() {
                            String method = "decline";

                            try{
                                URL url = new URL("http://192.168.56.1:8080/GcmServer2/AppResponse");
                                URLConnection connection = url.openConnection();

                                Log.d("username", username);
                                Log.d("uuid", uuid);

                                connection.setDoOutput(true);
                                OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());

                                out.write(method + ":" + username + ":" + uuid);
                                out.close();

                                Log.d("Connection", "Succesful");

                                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                                in.close();

                            }catch(Exception e)
                            {
                                Log.d("Exception",e.toString());
                            }
                        }
                    }).start();
                }
                break;
        }
    }

    public void register (final String inputRegID, final String inputUsername) {
        System.out.println("register called for " + inputUsername);

        if (inputUsername != null && !inputUsername.equals("")) {

            new Thread(new Runnable() {
                public void run() {
                    String method = "register";

                    try{
                        URL url = new URL("http://192.168.56.1:8080/GcmServer2/AppResponse");
                        URLConnection connection = url.openConnection();

                        Log.d("username", inputUsername);
                        Log.d("regid", inputRegID);

                        connection.setDoOutput(true);
                        OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());

                        out.write(method + ":" + inputUsername + ":" + inputRegID);
                        out.close();

                        Log.d("Connection", "Succesful");

                        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        in.close();

                    }catch(Exception e)
                    {
                        Log.d("Exception",e.toString());
                    }
                }
            }).start();
        }
    }
}
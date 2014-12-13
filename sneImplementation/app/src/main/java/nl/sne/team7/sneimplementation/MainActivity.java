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

//    // test
//    EditText inputValue=null;
//    Integer doubledValue =0;
//    Button doubleMe;
//    // test

    Button btnRegId;
    Button btnAccept;
    Button btnDecline;
    EditText etRegId;
    GoogleCloudMessaging gcm;
    String regid;
    String PROJECT_NUMBER = "1086996019341";

    public static String uuid;
    public static String username;

//    //test
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        inputValue = (EditText) findViewById(R.id.inputNum);
//        doubleMe = (Button) findViewById(R.id.doubleme);
//
//        doubleMe.setOnClickListener(this);
//    }
//    //test

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRegId = (Button) findViewById(R.id.btnGetRegId);
        btnAccept = (Button) findViewById(R.id.btnAccept);
        btnDecline = (Button) findViewById(R.id.btnDecline);
        etRegId = (EditText) findViewById(R.id.etRegId);

        btnRegId.setOnClickListener(this);
        btnAccept.setOnClickListener(this);
        btnDecline.setOnClickListener(this);
    }

//    //test
//    @Override
//    public void onClick(View v) {
//
//        switch (v.getId()){
//            case R.id.doubleme:
//
//                new Thread(new Runnable() {
//                    public void run() {
//
//                        try{
//                            URL url = new URL("http://192.168.56.1:8080/GcmServer2/AppResponse");
//                            URLConnection connection = url.openConnection();
//
//                            String inputString = inputValue.getText().toString();
//                            //inputString = URLEncoder.encode(inputString, "UTF-8");
//
//                            Log.d("inputString", inputString);
//
//                            connection.setDoOutput(true);
//                            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
//                            out.write(inputString);
//                            out.close();
//
//                            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//
//                            String returnString="";
//                            doubledValue =0;
//
//                            while ((returnString = in.readLine()) != null)
//                            {
//                                doubledValue= Integer.parseInt(returnString);
//                            }
//                            in.close();
//                            runOnUiThread(new Runnable() {
//                                public void run() {
//
//                                    inputValue.setText(doubledValue.toString());
//                                }
//                            });
//                        }catch(Exception e)
//                        {
//                            Log.d("Exception",e.toString());
//                        }
//                    }
//                }).start();
//                break;
//        }
//    }
//    //test

    public void getRegId(){
        new AsyncTask<Void, Void, String>() {

                @Override
                protected String doInBackground(Void... params) {
                    String msg = "";
                    try {
                        if (gcm == null) {
                            gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
                        }
                        regid = gcm.register(PROJECT_NUMBER);
                        msg = "Device registered, registration ID=" + regid;
                        Log.i("GCM",  msg);

                    } catch (IOException ex) {
                        msg = "Error :" + ex.getMessage();
                    }
                    return msg;
                }

            @Override
            protected void onPostExecute(String msg) {
                etRegId.setText(msg + "\n");
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

                        try{
                            URL url = new URL("http://192.168.56.1:8080/GcmServer2/AppResponse");
                            URLConnection connection = url.openConnection();

                            Log.d("username", username);
                            Log.d("uuid", uuid);

                            connection.setDoOutput(true);
                            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());

                            out.write(username + ":" + uuid);
                            out.close();

                            Log.d("Connection", "Succesful");

                            //test
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
                //TODO : send code
                System.out.println("decline");
                break;
        }
    }
}
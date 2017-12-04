package cmsc436group.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

//reference from  http://bitsoul.tistory.com/
//reference from https://www.numetriclabz.com/android-post-and-get-request-using-httpurlconnection/



public class MainActivity extends AppCompatActivity {

    private ProgressDialog progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        final Geocoder geo = new Geocoder(this);


        final TextView tv = (TextView) findViewById(R.id.textView4);
        Button b1 = (Button)findViewById(R.id.button1);
        Button b2 = (Button)findViewById(R.id.button2);
        // Button b3 = (Button)findViewById(R.id.button3);
        //Button b4 = (Button)findViewById(R.id.button4);

        final EditText et1 = (EditText)findViewById(R.id.editText1);
        final EditText et2 = (EditText)findViewById(R.id.editText2);
        final EditText et3 = (EditText)findViewById(R.id.editText3);

        Button NVDIButton = (Button) findViewById(R.id.Display);
        NVDIButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent imageIntent = new Intent(MainActivity.this, MapScreen.class);
                Intent intent = new Intent(getBaseContext(), MapScreen.class);


                String str = et3.getText().toString();

                List<Address> list = null;


                try {
                    list = geo.getFromLocationName(
                            str,
                            10);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("test", "Error");
                }

                if (list != null) {
                    if (list.size() == 0) {
                    } else {
                        double longitude = list.get(0).getLongitude();
                        double latitude = list.get(0).getLatitude();
                        //          list.get(0).getCountryName();
                        //          list.get(0).getLatitude();
                        //          list.get(0).getLongitude();

                        intent.putExtra("EXTRA_SESSION_ID", list.get(0).getAddressLine(0));
                        intent.putExtra("LATITUDE", latitude);
                        intent.putExtra("LONGITUDE", longitude);
                        startActivity(intent);
                        //startActivity(imageIntent);
                    }
                }
            }
        });

//        Button NAVButton = (Button)findViewById(R.id.NAV);
//
//        NAVButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent mapIntent = new Intent(MainActivity.this, MapScreen.class);
//                startActivity(mapIntent);
//            }
//        });





        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<Address> list = null;
                try {
                    double d1 = Double.parseDouble(et1.getText().toString());
                    double d2 = Double.parseDouble(et2.getText().toString());

                    list = geo.getFromLocation(d1, d2, 10);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("test", "Error");
                }
                if (list != null) {
                    if (list.size()==0) {
                        tv.setText("No Address Found");
                    } else {
                        tv.setText(list.get(0).getAddressLine(0));
                    }
                }
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getBaseContext(), MapScreen.class);



                List<Address> list = null;


                String str = et3.getText().toString();
                try {
                    list = geo.getFromLocationName(
                            str,
                            10);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("test","Error");
                }

                if (list != null) {
                    if (list.size() == 0) {
                        tv.setText("No Address Found");
                    } else {
                        tv.setText(list.get(0).toString());
                        double longitude = list.get(0).getLongitude();
                        double latitude = list.get(0).getLatitude();
                        tv.setText(list.get(0).getAddressLine(0));
                        //          list.get(0).getCountryName();
                        //          list.get(0).getLatitude();
                        //          list.get(0).getLongitude();

//                        intent.putExtra("EXTRA_SESSION_ID", list.get(0).getAddressLine(0));
//                        startActivity(intent);
                    }
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        this.invalidateOptionsMenu();

    }

    public void sendPostRequest(View View) {
        new PostClass(this).execute();
    }

    public void sendGetRequest(View View) {
        new GetClass(this).execute();
    }


    private class PostClass extends AsyncTask<String, Void, Void> {

        private final Context context;

        public PostClass(Context c){

            this.context = c;
//            this.error = status;
//            this.type = t;
        }

        protected void onPreExecute(){
            progress= new ProgressDialog(this.context);
            progress.setMessage("Loading");
            progress.show();
        }

        @Override
        protected Void doInBackground(String... params) {
            try {

              //  final TextView outputView = (TextView) findViewById(R.id.showOutput);
                URL url = new URL("http://requestb.in/1cs29cy1");

                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                String urlParameters = "fizz=buzz";
                connection.setRequestMethod("POST");
                connection.setRequestProperty("USER-AGENT", "Mozilla/5.0");
                connection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");
                connection.setDoOutput(true);
                DataOutputStream dStream = new DataOutputStream(connection.getOutputStream());
                dStream.writeBytes(urlParameters);
                dStream.flush();
                dStream.close();
                int responseCode = connection.getResponseCode();

                System.out.println("\nSending 'POST' request to URL : " + url);
                System.out.println("Post parameters : " + urlParameters);
                System.out.println("Response Code : " + responseCode);

                final StringBuilder output = new StringBuilder("Request URL " + url);
                output.append(System.getProperty("line.separator") + "Request Parameters " + urlParameters);
                output.append(System.getProperty("line.separator")  + "Response Code " + responseCode);
                output.append(System.getProperty("line.separator")  + "Type " + "POST");
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = "";
                StringBuilder responseOutput = new StringBuilder();
                System.out.println("output===============" + br);
                while((line = br.readLine()) != null ) {
                    responseOutput.append(line);
                }
                br.close();

                output.append(System.getProperty("line.separator") + "Response " + System.getProperty("line.separator") + System.getProperty("line.separator") + responseOutput.toString());

                MainActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                      //  outputView.setText(output);
                        progress.dismiss();
                    }
                });


            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute() {
            progress.dismiss();
        }

    }

    private class GetClass extends AsyncTask<String, Void, Void> {

        private final Context context;

        public GetClass(Context c){
            this.context = c;
        }

        protected void onPreExecute(){
            progress= new ProgressDialog(this.context);
            progress.setMessage("Loading");
            progress.show();
        }

        @Override
        protected Void doInBackground(String... params) {
            try {

            //    final TextView outputView = (TextView) findViewById(R.id.showOutput);
                URL url = new URL("http://requestb.in/1cs29cy1");

                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                String urlParameters = "fizz=buzz";
                connection.setRequestMethod("GET");
                connection.setRequestProperty("USER-AGENT", "Mozilla/5.0");
                connection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");

                int responseCode = connection.getResponseCode();

                System.out.println("\nSending 'POST' request to URL : " + url);
                System.out.println("Post parameters : " + urlParameters);
                System.out.println("Response Code : " + responseCode);

                final StringBuilder output = new StringBuilder("Request URL " + url);
                //output.append(System.getProperty("line.separator") + "Request Parameters " + urlParameters);
                output.append(System.getProperty("line.separator")  + "Response Code " + responseCode);
                output.append(System.getProperty("line.separator")  + "Type " + "GET");
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = "";
                StringBuilder responseOutput = new StringBuilder();
                System.out.println("output===============" + br);
                while((line = br.readLine()) != null ) {
                    responseOutput.append(line);
                }
                br.close();

                output.append(System.getProperty("line.separator") + "Response " + System.getProperty("line.separator") + System.getProperty("line.separator") + responseOutput.toString());

                MainActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                  //      outputView.setText(output);
                        progress.dismiss();

                    }
                });


            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute() {
            progress.dismiss();
        }

    }


}




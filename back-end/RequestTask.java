package com.example.stephen.planet;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import javax.net.ssl.HttpsURLConnection;
import java.util.HashMap;
import java.util.Map;





/**
 * Created by stephen on 11/9/17.
 */
//HashMap<String, String>
public class RequestTask extends AsyncTask<HashMap, Void, Void> {
    static String TAG = "mctaggert";
 //   static String KEY = "9130cf0c4fdf4ea099a172ab08c84525";
    static String URL = "http://10.0.2.2:5000";

    @Override
    protected Void doInBackground(HashMap... params) { //return type would be changed to String in alternative case
        //   getStuff();
        post(params[0]);
        return null;
    }

    // post code and helper function adapted from Sanoop at https://stackoverflow.com/questions/38735004/android-httpurlconnection-send-parameters
    private String post(HashMap param) {
        URL url;
        String response = "";
        try {
            url = new URL(URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(param));
            writer.flush();
            writer.close();
            os.close();
            int responseCode=conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                /* ignore this commented code. Usful if server returns string.
                String line; Currentl
                BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line=br.readLine()) != null) {
                    response+=line;
                }*/
                InputStream input = conn.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                //now you have a bitmap image!.

            }
            else {
                response="";

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        Log.i(TAG, result.toString());
        return result.toString();
    }
}

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;



public class Test {
	static String URL = "http://127.0.0.1:5000"; //http://10.0.2.2:5000";
	private String post() {
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
            HashMap<String, String> mapData = new HashMap();
            mapData.put("requestType", "compareRequest");
            String data = getPostDataString(mapData);
            writer.write(getPostDataString(mapData));

            writer.flush();
            writer.close();
            os.close();
            int responseCode=conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                /* commended code works for strings
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line=br.readLine()) != null) {
                    response+=line;

                }*/
               // Log.i(TAG, "HTTP OK");
                InputStream input = conn.getInputStream();
                //test
                //end test
              //  Log.i(TAG, "AVAILABLE BYTES: "+ Integer.toString(input.available()));
               // File targetFile = new File("thumbstream.png");
                File targetFile = new File("ndvi1.jpg");
                OutputStream outStream = new FileOutputStream(targetFile);
             
                byte[] buffer = new byte[500000 * 1024];
                int bytesRead;
                while ((bytesRead = input.read(buffer)) != -1) {
                	System.out.print(bytesRead);
                    outStream.write(buffer, 0, bytesRead);
                }
                input.close();
                outStream.close();
                System.out.println("Done");
      

            }
            else {
                response="";

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
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
        //Log.i(TAG, result.toString());
        return result.toString();
    }

	
	
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Test t1 = new Test();
		t1.post();

	}

}

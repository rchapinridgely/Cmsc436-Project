package com.example.stephen.planet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    static String TAG = "mctaggert";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //in this case, I'm requesting a thumbnail that's stored on the server, as an example.
        //The main request type for our purposes will be the satImage request, which will require the input
        // of a date and coordinates as well.
        HashMap<String, String> argument = new HashMap();
        argument.put("requestType", "compareRequest");
        new RequestTask().execute(argument);
    }
    
}

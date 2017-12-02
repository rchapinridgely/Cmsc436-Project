package cmsc436group.myapplication;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

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
                        tv.setText(list.get(0).toString());
                    }
                }
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                    }
                }
            }
        });

    }
}




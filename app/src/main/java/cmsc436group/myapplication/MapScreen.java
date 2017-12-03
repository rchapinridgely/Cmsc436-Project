package cmsc436group.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class MapScreen extends AppCompatActivity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_screen);

        Button NVDIButton = (Button)findViewById(R.id.NVDI);
        NVDIButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent submitIntent = new Intent(this, SummaryActivity.class);
               // startActivity(new Intent(MapScreen.this, .class));
            }
        });

        Button NAVButton = (Button)findViewById(R.id.NAV);
        NAVButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent submitIntent = new Intent(this, SummaryActivity.class);
                //startActivity(new Intent(MapScreen.this, .class));
            }
        });
    }
}

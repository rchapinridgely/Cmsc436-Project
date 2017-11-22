package cmsc436.cmsc436_group;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class MapScreen extends AppCompatActivity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init_search);

        Button NVDIButton = findViewById(R.id.NVDI);
        NVDIButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent submitIntent = new Intent(this, SummaryActivity.class);
               // startActivity(new Intent(MapScreen.this, .class));
            }
        });

        Button NAVButton = findViewById(R.id.NAV);
        NAVButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent submitIntent = new Intent(this, SummaryActivity.class);
                //startActivity(new Intent(MapScreen.this, .class));
            }
        });
    }
}

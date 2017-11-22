package cmsc436.cmsc436_group;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;

public class InitSearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init_search);

        Button NVDIButton = findViewById(R.id.NVDI);
        NVDIButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent submitIntent = new Intent(this, SummaryActivity.class);
                startActivity(new Intent(InitSearchActivity.this, MapScreen.class));
            }
        });

        Button NAVButton = findViewById(R.id.NAV);
        NAVButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent submitIntent = new Intent(this, SummaryActivity.class);
                startActivity(new Intent(InitSearchActivity.this, ImageScreen.class));
            }
        });
    }
}

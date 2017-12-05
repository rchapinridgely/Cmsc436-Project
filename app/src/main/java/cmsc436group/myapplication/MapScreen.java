package cmsc436group.myapplication;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Calendar;


//https://stackoverflow.com/questions/17808373/popup-datepicker-for-edittext

public class MapScreen extends AppCompatActivity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_screen);

        ImageView mImageView;
        mImageView = (ImageView) findViewById(R.id.NDVIImage);
        mImageView.setImageResource(getResources().getIdentifier("nav", "drawable", getPackageName()));

//         String str = getIntent().getStringExtra("EXTRA_SESSION_ID");
//final String cityName = str.replaceAll(" ", "_").toLowerCase().trim();
//
//        Toast.makeText(getApplicationContext(),cityName, Toast.LENGTH_LONG).show();


        Button setDate = (Button) findViewById(R.id.setDate);
        setDate.setOnClickListener(new View.OnClickListener() {


            int mYear=0;
            int mMonth=0;
            int mDay =0;

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //To show current date in the datepicker
                Calendar mcurrentDate=Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR);
                mMonth=mcurrentDate.get(Calendar.MONTH);
                mDay=mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker=new DatePickerDialog(MapScreen.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                    /*      Your code   to get date and time    */
                    }
                },mYear, mMonth, mDay);

                int newNDVIthumb = 2 + (int)(Math.random() * ((3 - 2) + 1));

                String newNDVIthumbName = "ndvi_output" + newNDVIthumb;
                ImageView mImageView = (ImageView) findViewById(R.id.NDVIImage);
                mImageView.setImageResource(getResources().getIdentifier(newNDVIthumbName, "drawable", getPackageName()));

                mDatePicker.setTitle("Select date");
                mDatePicker.show();
            }
        });

        Button NDVIButton = (Button)findViewById(R.id.GotoNDVI);
        NDVIButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent submitIntent = new Intent(this, SummaryActivity.class);
               // startActivity(new Intent(MapScreen.this, .class));
                ImageView mImageView;
                mImageView = (ImageView) findViewById(R.id.NDVIImage);
                String str = getIntent().getStringExtra("EXTRA_SESSION_ID");
                float latitude = getIntent().getFloatExtra("LATITUDE", 0);
                float longitude = getIntent().getFloatExtra("LONGITUDE", 0);
                String imageName = "ndvi_output";
                String cityName = str.replaceAll(" ", "_").toLowerCase().trim();
//                if(cityName =="new york ny" ){
//                    imageName = "img1";
//                }
                Toast.makeText(getApplicationContext(),imageName, Toast.LENGTH_LONG).show();

                mImageView.setImageResource(getResources().getIdentifier(imageName, "drawable", getPackageName()));
               // mImageView.setImageResource(R.drawable.test2);

            }
        });

        Button NAVButton = (Button)findViewById(R.id.GotoNAV);
        NAVButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent submitIntent = new Intent(this, SummaryActivity.class);
                //startActivity(new Intent(MapScreen.this, .class));
                 ImageView mImageView;
                mImageView = (ImageView) findViewById(R.id.NDVIImage);
             //   String imageName = "test1.tif";
               // mImageView.setImageResource(R.drawable.test2);
                String str = getIntent().getStringExtra("EXTRA_SESSION_ID");
                String imageName = "nav";
                final String cityName = str.replaceAll(" ", "_").toLowerCase().trim();
                //if(cityName =="new_york_ny" ){
                //    imageName = "img1";
                //}
                mImageView.setImageResource(getResources().getIdentifier(imageName, "drawable", getPackageName()));

            }
        });


        Button backButton = (Button)findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent submitIntent = new Intent(this, SummaryActivity.class);
                //startActivity(new Intent(MapScreen.this, .class));
                finish();
            }
        });

        Button saveButton = (Button)findViewById(R.id.save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent submitIntent = new Intent(this, SummaryActivity.class);
                //startActivity(new Intent(MapScreen.this, .class));
                Toast.makeText(getApplicationContext(), getText(R.string.saved), Toast.LENGTH_LONG).show();

            }
        });

    }


}




















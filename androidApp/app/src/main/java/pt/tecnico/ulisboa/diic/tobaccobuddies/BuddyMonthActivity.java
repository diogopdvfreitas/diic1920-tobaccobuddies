package pt.tecnico.ulisboa.diic.tobaccobuddies;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;

import java.util.ArrayList;

public class BuddyMonthActivity extends AppCompatActivity {

    BarChart barChart;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buddy_page3);



        // Code for Settings button
        ImageButton settingsButton = findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // start a NotificationsActivity
                Intent intent = new Intent(getApplicationContext(), NotificationsActivity.class);
                startActivity(intent);
            }
        });

        // Code for E-Puppy button
        ImageButton ePuppyButton = findViewById(R.id.epuppyButton);
        ePuppyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // start an EPuppyActivity
                Intent intent = new Intent(getApplicationContext(), EPuppyActivity.class);
                startActivity(intent);
            }
        });

        /* Button year = (Button) findViewById(R.id.yearButton);
        year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MenuYearActivity.class);
                startActivity(intent);
            }
        });

        Button week = (Button) findViewById(R.id.weekButton);
        System.out.println(week);
        week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MenuWeekActivity.class);
                startActivity(intent);
            }
        }); */


        ImageButton left = findViewById(R.id.swipe_left);
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MenuWeekActivity.class);
                startActivity(intent);
            }
        });
    }
}

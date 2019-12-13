package pt.tecnico.ulisboa.diic.tobaccobuddies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.github.mikephil.charting.charts.BarChart;

import java.util.ArrayList;

public class MenuWeekActivity extends AppCompatActivity {
    BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_page2_week);

        barChart = (BarChart) findViewById(R.id.barChart);
        ArrayList<Integer> barEntries = new ArrayList<>();
        //this array is where we need to put values from arduino and start creating the barChart
        //setContentView(R.layout.menu_page2_week);

        // Code for Settings button
        ImageButton settingsButton = findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // start a NotificationsActivity
                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
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

        Button year = (Button) findViewById(R.id.yearButton);
        year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MenuYearActivity.class);
                System.out.println(intent);
                startActivity(intent);
            }
        });

        Button month = (Button) findViewById(R.id.monthButton);
        month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MenuMonthActivity.class);
                System.out.println(intent);
                startActivity(intent);
            }
        });

        //this swipe works
        ImageButton swipeLeftButton = findViewById(R.id.swipe_left);
        swipeLeftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BuddyPageActivity.class);
                System.out.println(intent);
                startActivity(intent);
            }
        });

        ImageButton swipeRight = findViewById(R.id.swipe_right);
        swipeRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BuddyMonthActivity.class);
                System.out.println(intent);
                startActivity(intent);
            }
        });
    }
}
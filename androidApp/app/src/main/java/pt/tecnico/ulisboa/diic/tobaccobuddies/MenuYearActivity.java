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

public class MenuYearActivity extends AppCompatActivity {
    BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_page2_year);
        //creating barChart
        barChart = (BarChart) findViewById(R.id.barChart);
        ArrayList<Integer> barEntries = new ArrayList<>();
        //this array is where we need to put values from arduino and start creating the barChart
        Button month = (Button) findViewById(R.id.monthButton);
        month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MenuMonthActivity.class);
                System.out.println(intent);
                startActivity(intent);
            }
        });

        Button week = (Button) findViewById(R.id.weekButton);
        week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MenuWeekActivity.class);
                System.out.println(intent);
                startActivity(intent);
            }
        });


        ImageButton left = findViewById(R.id.swipe_left);
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BuddyPageActivity.class);
                startActivity(intent);
            }
        });

        ImageButton right = findViewById(R.id.swipe_right);
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BuddyMonthActivity.class);
                System.out.println(intent);
                startActivity(intent);
            }
        });

    }
}
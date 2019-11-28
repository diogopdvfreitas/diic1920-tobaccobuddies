package pt.tecnico.ulisboa.diic.tobaccobuddies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.github.mikephil.charting.charts.BarChart;

import java.util.ArrayList;

public class MenuYearActivity extends AppCompatActivity {
    BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_year);
        //creating barChart
        barChart = (BarChart) findViewById(R.id.barChart);
        ArrayList<Integer> barEntries = new ArrayList<>();
        //this array is where we need to put values from arduino and start creating the barChart
        setContentView(R.layout.menu_page2_year);
        Button week = (Button) findViewById(R.id.weekButton2);
        week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MenuWeekActivity.class);
                System.out.println(intent);
                startActivity(intent);
            }
        });

        Button month = (Button) findViewById(R.id.monthButton2);
        System.out.println(month);
        month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MenuMonthActivity.class);
                System.out.println(intent);
                startActivity(intent);
            }
        });

    }
}
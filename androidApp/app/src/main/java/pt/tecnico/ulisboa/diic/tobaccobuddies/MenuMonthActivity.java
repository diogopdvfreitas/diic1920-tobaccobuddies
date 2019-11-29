package pt.tecnico.ulisboa.diic.tobaccobuddies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.github.mikephil.charting.charts.BarChart;

import java.util.ArrayList;

public class MenuMonthActivity extends AppCompatActivity {

    BarChart barChart;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        //creating barChart
        barChart = (BarChart) findViewById(R.id.barChart);
        ArrayList<Integer> barEntries = new ArrayList<>();
        //this array is where we need to put values from arduino and start creating the barChart

        setContentView(R.layout.menu_page2_month);
        Button year = (Button) findViewById(R.id.yearButton);
        year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MenuYearActivity.class);
                System.out.println(intent);
                startActivity(intent);
            }
        });

        Button week = (Button) findViewById(R.id.weekButton);
        System.out.println(week);
        week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MenuWeekActivity.class);
                System.out.println(intent);
                startActivity(intent);
            }
        });
    }
}

package pt.tecnico.ulisboa.diic.tobaccobuddies;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.github.mikephil.charting.charts.BarChart;

import java.util.ArrayList;

public class MenuWeek extends AppCompatActivity{
    BarChart barChart;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_page2_week);

        //Open shared preferences (Settings) to check if the values exist
        checkDefaultSettings();

        // DEBUG used to launch activities to test
        Intent intent = new Intent(this, BuddyPageActivity.class);
        startActivity(intent);

        //creating barChart
        barChart = (BarChart) findViewById(R.id.barChart);
        ArrayList<Integer> barEntries = new ArrayList<>();
        //this array is where we need to put values from arduino and start creating the barChart
        setContentView(R.layout.menu_page2_week);
        Button month = (Button) findViewById(R.id.monthButton);
        month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MenuMonth.class);
                startActivity(intent);
            }
        });


        Button year = (Button) findViewById(R.id.yearButton);

    }

    //Open shared preferences (Settings) to check if the values exist
    private void checkDefaultSettings(){
        final SharedPreferences sharedPreferences = getSharedPreferences(getResources().getString(R.string.my_preferences), Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        if(!sharedPreferences.contains("allNotifications")){
            editor.putBoolean("allNotifications",true);
        }
        if(!sharedPreferences.contains("appVibration")){
            editor.putBoolean("appVibration",true);
        }
        if(!sharedPreferences.contains("packVibration")){
            editor.putBoolean("packVibration",true);
        }
        if(!sharedPreferences.contains("yourLimits")){
            editor.putBoolean("yourLimits",true);
        }
        if(!sharedPreferences.contains("buddyLimits")){
            editor.putBoolean("buddyLimits",true);
        }
        if(!sharedPreferences.contains("progress")){
            editor.putBoolean("progress",true);
        }
        editor.commit();
    }
}

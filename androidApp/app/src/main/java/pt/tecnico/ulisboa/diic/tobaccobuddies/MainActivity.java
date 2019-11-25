package pt.tecnico.ulisboa.diic.tobaccobuddies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Open shared preferences (Settings) to check if the values exist
        checkDefaultSettings();

        // DEBUG used to launch activities to test
        Intent intent = new Intent(this, NotificationsActivity.class);
        startActivity(intent);

    }

    //Open shared preferences (Settings) to check if the values exist
    private void checkDefaultSettings(){
        final SharedPreferences sharedPreferences = getSharedPreferences(getResources().getString(R.string.my_preferences), Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        if(!sharedPreferences.contains("allNotifications")){
            editor.putInt("allNotifications",1);
        }
        if(!sharedPreferences.contains("appVibration")){
            editor.putInt("appVibration",1);
        }
        if(!sharedPreferences.contains("packVibration")){
            editor.putInt("packVibration",1);
        }
        if(!sharedPreferences.contains("yourLimits")){
            editor.putInt("yourLimits",1);
        }
        if(!sharedPreferences.contains("buddyLimits")){
            editor.putInt("buddyLimits",1);
        }
        if(!sharedPreferences.contains("progress")){
            editor.putInt("progress",1);
        }
        editor.commit();
    }



}



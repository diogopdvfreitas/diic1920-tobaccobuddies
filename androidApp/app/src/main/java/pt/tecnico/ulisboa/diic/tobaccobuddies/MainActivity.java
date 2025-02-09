package pt.tecnico.ulisboa.diic.tobaccobuddies;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buddy_page1);

        //Open shared preferences (Settings) to check if the values exist
        checkDefaultSettings();


        // DEBUG used to launch activities to test
        Intent intent = new Intent(this, BuddyPageActivity.class);
        startActivity(intent);



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
        if(!sharedPreferences.contains("notifyBuddy")){
            editor.putBoolean("notifyBuddy", true);
        }
        editor.apply();
    }





}
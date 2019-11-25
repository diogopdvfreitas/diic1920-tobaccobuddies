package pt.tecnico.ulisboa.diic.tobaccobuddies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

public class NotificationsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notifications);

        // Open shared preferences (Settings) and an editor to change them
        final SharedPreferences sharedPreferences = getSharedPreferences(getResources().getString(R.string.my_preferences), Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        // Code for Close (X) button
        Button closeButton = findViewById(R.id.closeButton);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Code for All Notifications button
        final CompoundButton allSwitch = findViewById(R.id.allSwitch);
        allSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("allNotifications: " + allSwitch.isChecked());

                if(allSwitch.isChecked())
                    editor.putInt("allNotifications",1);
                else
                    editor.putInt("allNotifications",0);
                editor.commit();

                System.out.println(sharedPreferences.getAll());
            }
        });

        // Code for App Vibration button
        final CompoundButton appVibration = findViewById(R.id.vibAppSwitch);
        appVibration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("appVibration: " + appVibration.isChecked());

                if(appVibration.isChecked())
                    editor.putInt("appVibration",1);
                else
                    editor.putInt("appVibration",0);
                editor.commit();

                System.out.println(sharedPreferences.getAll());
            }
        });

        // Code for Pack Vibration button
        final CompoundButton packVibration = findViewById(R.id.vibPackSwitch);
        packVibration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("packVibration: " + packVibration.isChecked());

                if(packVibration.isChecked())
                    editor.putInt("packVibration",1);
                else
                    editor.putInt("packVibration",0);
                editor.commit();

                System.out.println(sharedPreferences.getAll());
            }
        });

        // Code for Your Limits button
        final CompoundButton yourLimits = findViewById(R.id.limitsSwitch);
        yourLimits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("yourLimits: " + yourLimits.isChecked());

                if(yourLimits.isChecked())
                    editor.putInt("yourLimits",1);
                else
                    editor.putInt("yourLimits",0);
                editor.commit();

                System.out.println(sharedPreferences.getAll());
            }
        });

        // Code for Buddy Limits button
        final CompoundButton buddyLimits = findViewById(R.id.limitsSwitch2);
        buddyLimits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("buddyLimits: " + buddyLimits.isChecked());

                if(buddyLimits.isChecked())
                    editor.putInt("buddyLimits",1);
                else
                    editor.putInt("buddyLimits",0);
                editor.commit();

                System.out.println(sharedPreferences.getAll());
            }
        });

        // Code for Progress button
        final CompoundButton progress = findViewById(R.id.progressSwitch);
        progress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("progress: " + progress.isChecked());

                if(progress.isChecked())
                    editor.putInt("progress",1);
                else
                    editor.putInt("progress",0);
                editor.commit();

                System.out.println(sharedPreferences.getAll());
            }
        });

    }

}

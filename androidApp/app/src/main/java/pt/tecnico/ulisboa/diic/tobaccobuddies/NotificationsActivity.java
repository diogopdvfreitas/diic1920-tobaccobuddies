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
        allSwitch.setChecked(sharedPreferences.getBoolean("allNotifications", true));
        allSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("allNotifications: " + allSwitch.isChecked());

                editor.putBoolean("allNotifications", allSwitch.isChecked());
                editor.commit();

                System.out.println(sharedPreferences.getAll());
            }
        });

        // Code for App Vibration button
        final CompoundButton appVibration = findViewById(R.id.vibAppSwitch);
        appVibration.setChecked(sharedPreferences.getBoolean("appVibration", true));
        appVibration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("appVibration: " + appVibration.isChecked());

                editor.putBoolean("appVibration", appVibration.isChecked());
                editor.commit();

                System.out.println(sharedPreferences.getAll());
            }
        });

        // Code for Pack Vibration button
        final CompoundButton packVibration = findViewById(R.id.vibPackSwitch);
        packVibration.setChecked(sharedPreferences.getBoolean("packVibration", true));
        packVibration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("packVibration: " + packVibration.isChecked());

                editor.putBoolean("packVibration", packVibration.isChecked());
                editor.commit();

                System.out.println(sharedPreferences.getAll());
            }
        });

        // Code for Your Limits button
        final CompoundButton yourLimits = findViewById(R.id.limitsSwitch);
        yourLimits.setChecked(sharedPreferences.getBoolean("yourLimits", true));
        yourLimits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("yourLimits: " + yourLimits.isChecked());

                editor.putBoolean("yourLimits", yourLimits.isChecked());
                editor.commit();

                System.out.println(sharedPreferences.getAll());
            }
        });

        // Code for Buddy Limits button
        final CompoundButton buddyLimits = findViewById(R.id.limitsSwitch2);
        buddyLimits.setChecked(sharedPreferences.getBoolean("buddyLimits", true));
        buddyLimits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("buddyLimits: " + buddyLimits.isChecked());

                editor.putBoolean("buddyLimits", buddyLimits.isChecked());
                editor.commit();

                System.out.println(sharedPreferences.getAll());
            }
        });

        // Code for Progress button
        final CompoundButton progress = findViewById(R.id.progressSwitch);
        progress.setChecked(sharedPreferences.getBoolean("progress", true));
        progress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("progress: " + progress.isChecked());

                editor.putBoolean("progress", progress.isChecked());
                editor.commit();

                System.out.println(sharedPreferences.getAll());
            }
        });

    }

}
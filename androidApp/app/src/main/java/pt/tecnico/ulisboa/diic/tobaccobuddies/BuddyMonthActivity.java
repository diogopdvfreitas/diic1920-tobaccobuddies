package pt.tecnico.ulisboa.diic.tobaccobuddies;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


public class BuddyMonthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buddy_page3);

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

        // Code for swipe left button
        ImageButton swipeLeftButton = findViewById(R.id.swipe_left);
        swipeLeftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // start the correspondent Activity
                Intent intent = new Intent(getApplicationContext(), MenuWeekActivity.class);
                startActivity(intent);
            }
        });

    }
}

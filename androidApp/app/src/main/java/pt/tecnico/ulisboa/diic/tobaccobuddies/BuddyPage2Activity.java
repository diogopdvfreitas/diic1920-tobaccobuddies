package pt.tecnico.ulisboa.diic.tobaccobuddies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class BuddyPage2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_page2_week);

        // Code for Settings button
        ImageButton settingsButton = findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // start a NotificationsActivity
                Intent intent = new Intent(getApplicationContext(), NotificationsActivity.class);
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

        // Code for swipe right button
        /* ImageButton swipeRightButton = findViewById(R.id.swipe_right);
        swipeRightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // start the correspondent Activity
                Intent intent = new Intent(getApplicationContext(), BuddyPage3Activity.class);
                startActivity(intent);
            }
        }); */

        // Code for swipe left button
        ImageButton swipeLeftButton = findViewById(R.id.swipe_left);
        swipeLeftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // start the correspondent Activity
                Intent intent = new Intent(getApplicationContext(), BuddyPageActivity.class);
                startActivity(intent);
            }
        });

    }
}


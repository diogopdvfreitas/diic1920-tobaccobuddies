package pt.tecnico.ulisboa.diic.tobaccobuddies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class BuddyPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buddy_page1);

        // Code for Settings button
        ImageButton settingsButton = findViewById(R.id.settingsButton2);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // start a NotificationsActivity
                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intent);
            }
        });

        ImageView secondCigarette = findViewById(R.id.you_cigarette2);
        secondCigarette.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HalfLimitReachedActivity.class);
                startActivity(intent);

            }
        });

        ImageView thirdCigarette = findViewById(R.id.you_cigarette3);
        thirdCigarette.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),LimitsReachedActivity.class);
                startActivity(intent);

            }
        });

        ImageView buddyPack = findViewById(R.id.buddy_pack);
        buddyPack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),BuddyHalfLimitReachedActivity.class);
                startActivity(intent);

            }
        });

        ImageView cigaretteBuddy = findViewById(R.id.buddy_cigarette1);
        cigaretteBuddy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),BuddyLimitReachedActivity.class);
                startActivity(intent);

            }
        });




        // Code for swipe right button
        ImageButton swipeRightButton = findViewById(R.id.swipe_right);
        swipeRightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // start the correspondent Activity
                Intent intent = new Intent(getApplicationContext(), MenuWeekActivity.class);
                startActivity(intent);
            }
        });

    }
}

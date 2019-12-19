package pt.tecnico.ulisboa.diic.tobaccobuddies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class BuddyPageActivity extends AppCompatActivity {

    float x1, x2, y1, y2;

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

    }

    public boolean onTouchEvent(MotionEvent touchEvent){
        switch(touchEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1 = touchEvent.getX();
                y1 = touchEvent.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = touchEvent.getX();
                y2 = touchEvent.getY();
                /* if(x1 < x2){
                Intent i = new Intent(BuddyPageActivity.this, SwipeLeft.class);
                startActivity(i);
            }else */
                if(x1 > x2){
                Intent i = new Intent(BuddyPageActivity.this, MenuWeekActivity.class);
                startActivity(i);
            }
            break;
        }
        return false;
    }

}

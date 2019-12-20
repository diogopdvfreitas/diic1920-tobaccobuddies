package pt.tecnico.ulisboa.diic.tobaccobuddies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class BuddyPageActivity extends AppCompatActivity {

    float x1, x2, y1, y2;

    //ImageView graph;
    View menu_page2_week;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buddy_page1);

        menu_page2_week = findViewById(R.id.weekMenu);

        final SharedPreferences sharedPreferences = getSharedPreferences("editor", 0);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        boolean aux = sharedPreferences.getBoolean("tag", false);
        final boolean[] check = {aux};

        editor.putBoolean(String.valueOf(sharedPreferences), aux).apply();

        checkVisibility(check[0]);

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


        ImageView youPack = findViewById(R.id.you_pack);
        youPack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkVisibility(check[0]);
                if(!(check[0])) {
                    check[0] = true;
                    editor.putBoolean("tag", check[0]).commit();
                    findViewById(R.id.you_cigarette4).setVisibility(View.VISIBLE);
                }
                checkVisibility(check[0]);

            }
        });

        ImageView buddyPack = findViewById(R.id.buddy_pack);
        buddyPack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*REMOVE CHECK[0] CODE, THIS IS ONLY FOR TEST PURPOSES*/

                if(check[0]) { //this if needs to only check the buddy_cigarette2 visibility
                    findViewById(R.id.buddy_cigarette3).setVisibility(View.VISIBLE);
                    check[0] = false;
                    editor.putBoolean("tag", check[0]).commit();
                }
                checkVisibility(check[0]);
            }
        });

        ImageView cigaretteBuddy = findViewById(R.id.buddy_cigarette1);
        cigaretteBuddy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),BuddyHalfLimitReachedActivity.class);
                startActivity(intent);

            }
        });

        ImageView cigaretteBuddy2 = findViewById(R.id.buddy_cigarette2);
        cigaretteBuddy2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),BuddyLimitReachedActivity.class);
                startActivity(intent);

            }
        });

    }

    public void checkVisibility(boolean b){
        if(b){
            System.out.println("b is true");
            findViewById(R.id.you_cigarette4).setVisibility(View.VISIBLE);
        }
        else{
            findViewById(R.id.you_cigarette4).setVisibility(View.INVISIBLE);
        }
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

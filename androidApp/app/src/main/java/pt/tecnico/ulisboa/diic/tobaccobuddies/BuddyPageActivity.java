package pt.tecnico.ulisboa.diic.tobaccobuddies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class BuddyPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buddy_page3_2);

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

    }
}

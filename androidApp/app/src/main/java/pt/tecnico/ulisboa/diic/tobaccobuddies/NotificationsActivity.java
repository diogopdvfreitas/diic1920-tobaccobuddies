package pt.tecnico.ulisboa.diic.tobaccobuddies;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ToggleButton;

public class NotificationsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notifications);

        // Code for Close (X) button
        Button closeButton = findViewById(R.id.closeButton);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Code for All Notifications button
        Button allSwitch = findViewById(R.id.allSwitch);
        allSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("allNotifications");
            }
        });

        // Code for App Vibration button
        Button appVibration = findViewById(R.id.vibAppSwitch);
        appVibration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("appVibration");
            }
        });

        // Code for Pack Vibration button
        Button packVibration = findViewById(R.id.vibPackSwitch);
        packVibration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("packVibration");
            }
        });

        // Code for Your Limits button
        Button yourLimits = findViewById(R.id.limitsSwitch);
        yourLimits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("yourLimits");
            }
        });

        // Code for Buddy Limits button
        Button buddyLimits = findViewById(R.id.limitsSwitch2);
        buddyLimits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("buddyLimits");
            }
        });

        // Code for Progress button
        Button progress = findViewById(R.id.progressSwitch);
        progress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("progress");
            }
        });

    }

}

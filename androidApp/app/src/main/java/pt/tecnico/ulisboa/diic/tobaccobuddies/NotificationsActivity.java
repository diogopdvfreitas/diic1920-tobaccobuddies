package pt.tecnico.ulisboa.diic.tobaccobuddies;

import androidx.appcompat.app.AppCompatActivity;

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
            }
        });

        // Code for App Vibration button
        final CompoundButton appVibration = findViewById(R.id.vibAppSwitch);
        appVibration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("appVibration: " + appVibration.isChecked());
            }
        });

        // Code for Pack Vibration button
        final CompoundButton packVibration = findViewById(R.id.vibPackSwitch);
        packVibration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("packVibration: " + packVibration.isChecked());
            }
        });

        // Code for Your Limits button
        final CompoundButton yourLimits = findViewById(R.id.limitsSwitch);
        yourLimits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("yourLimits: " + yourLimits.isChecked());
            }
        });

        // Code for Buddy Limits button
        final CompoundButton buddyLimits = findViewById(R.id.limitsSwitch2);
        buddyLimits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("buddyLimits: " + buddyLimits.isChecked());
            }
        });

        // Code for Progress button
        final CompoundButton progress = findViewById(R.id.progressSwitch);
        progress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("progress: " + progress.isChecked());
            }
        });

    }

}

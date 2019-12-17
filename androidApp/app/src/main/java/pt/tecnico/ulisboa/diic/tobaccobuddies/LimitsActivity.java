package pt.tecnico.ulisboa.diic.tobaccobuddies;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class LimitsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_limits);

        // Open shared preferences (Settings) and an editor to change them
        final SharedPreferences sharedPreferences = getSharedPreferences(getResources().getString(R.string.my_preferences), Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        final int[] count = {0};
        final TextView limits  = findViewById(R.id.textView5);
        limits.setText(String.valueOf(count[0]));
        ImageView arrowUp = findViewById(R.id.arrow_up);
        arrowUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count[0]++;
                limits.setText(String.valueOf(count[0]));
            }});

        ImageView arrowDown = findViewById(R.id.arrow_down);
        arrowDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count[0]--;
                limits.setText(String.valueOf(count[0]));
            }});

        // Code for Close (X) button
        Button closeButton = findViewById(R.id.close_button);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        // Code for Notify Buddy button
        final CompoundButton notifySwitch = findViewById(R.id.checkBox);
        notifySwitch.setChecked(sharedPreferences.getBoolean("notifyBuddy", true));
        notifySwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("notifyBuddy: " + notifySwitch.isChecked());

                editor.putBoolean("notifyBuddy", notifySwitch.isChecked());
                editor.commit();

                System.out.println(sharedPreferences.getAll());
            }
        });

    }
}

package pt.tecnico.ulisboa.diic.tobaccobuddies;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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
        final SharedPreferences mPrefs = getSharedPreferences("limits", 0);
        final SharedPreferences.Editor mEditor = mPrefs.edit();

        final TextView limits  = findViewById(R.id.textView5);

        String limitsValue = mPrefs.getString("tag", String.valueOf(0));
        limits.setText(limitsValue);
        final int[] count = {Integer.valueOf(limitsValue)};

        ImageView arrowUp = findViewById(R.id.arrow_up);
        arrowUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count[0]++;
                mEditor.putString("tag", String.valueOf(count[0])).commit();
                limits.setText(String.valueOf(count[0]));
            }});

        ImageView arrowDown = findViewById(R.id.arrow_down);
        arrowDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count[0] > 0) {
                    count[0]--;
                    mEditor.putString("tag", String.valueOf(count[0])).commit();
                    limits.setText(String.valueOf(count[0]));
                }
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

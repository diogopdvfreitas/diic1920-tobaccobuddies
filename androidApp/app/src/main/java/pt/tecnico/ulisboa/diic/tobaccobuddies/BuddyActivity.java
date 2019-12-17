package pt.tecnico.ulisboa.diic.tobaccobuddies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class BuddyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_buddy);
        final SharedPreferences sharedPreferences = getSharedPreferences("editor", 0);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        // Code for Close (X) button
        Button closeButton = findViewById(R.id.close_button2);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intent);
            }
        });

        //Verification if the user has already a buddy
        boolean aux = sharedPreferences.getBoolean("tag", false);
        final boolean[] hasBuddy = {aux};
        editor.putBoolean(String.valueOf(sharedPreferences), aux).apply();

        ImageView addBuddyButton = findViewById(R.id.add_buddy);
        addBuddyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!(hasBuddy[0])){
                    Intent intent = new Intent(getApplicationContext(), AddBuddyActivity.class);
                    hasBuddy[0] = true;
                    editor.putBoolean("tag", hasBuddy[0]).commit();
                    startActivity(intent);
                }
                else{
                    //criar new activity with a popup saying the user has already a buddy
                    Intent intent = new Intent(getApplicationContext(), BuddyAlreadyExistisActivity.class);
                    startActivity(intent);
                }
            }
        });


        ImageView removeBuddyButton = findViewById(R.id.remove_buddy);
        removeBuddyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((hasBuddy[0])){
                    Intent intent = new Intent(getApplicationContext(), RemoveBuddyActivity.class);
                    hasBuddy[0] = false;
                    editor.putBoolean("tag", hasBuddy[0]).commit();
                    startActivity(intent);
                }
                else{
                    //criar new activity with a popup saying the user has already a buddy
                    Intent intent = new Intent(getApplicationContext(), BuddyNonexistentActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}

package pt.tecnico.ulisboa.diic.tobaccobuddies;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // DEBUG used to launch activities to test
        Intent intent = new Intent(this, NotificationsActivity.class);
        startActivity(intent);

    }



}



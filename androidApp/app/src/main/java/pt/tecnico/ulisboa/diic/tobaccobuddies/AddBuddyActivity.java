package pt.tecnico.ulisboa.diic.tobaccobuddies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class AddBuddyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_buddy);

        ImageButton enterButton = findViewById(R.id.enter);
        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), BuddyAddedActivity.class);
                startActivity(intent);
            }
        });
    }
}

package pt.tecnico.ulisboa.diic.tobaccobuddies;

import android.content.ComponentName;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;
import android.widget.ViewFlipper;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


public class MainActivity extends AppCompatActivity {

    private MainActivityViewModel viewModel;

    boolean mBounded;
    BluetoothService mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buddy_page1);

        startService(new Intent(this, BluetoothService.class));

        //Open shared preferences (Settings) to check if the values exist
        checkDefaultSettings();


        // DEBUG used to launch activities to test
        Intent intent = new Intent(this, BuddyPageActivity.class);
        startActivity(intent);



    }


    //Open shared preferences (Settings) to check if the values exist
    private void checkDefaultSettings(){
        final SharedPreferences sharedPreferences = getSharedPreferences(getResources().getString(R.string.my_preferences), Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        if(!sharedPreferences.contains("allNotifications")){
            editor.putBoolean("allNotifications",true);
        }
        if(!sharedPreferences.contains("appVibration")){
            editor.putBoolean("appVibration",true);
        }
        if(!sharedPreferences.contains("packVibration")){
            editor.putBoolean("packVibration",true);
        }
        if(!sharedPreferences.contains("yourLimits")){
            editor.putBoolean("yourLimits",true);
        }
        if(!sharedPreferences.contains("buddyLimits")){
            editor.putBoolean("buddyLimits",true);
        }
        if(!sharedPreferences.contains("progress")){
            editor.putBoolean("progress",true);
        }
        if(!sharedPreferences.contains("notifyBuddy")){
            editor.putBoolean("notifyBuddy", true);
        }
        editor.apply();
    }

    // Bluetooth Stuff

    ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Toast.makeText(MainActivity.this, "Service is disconnected",  Toast.LENGTH_LONG).show();
            mBounded = false;
            mService = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Toast.makeText(MainActivity.this, "Service is connected", Toast.LENGTH_LONG).show();
            mBounded = true;
            BluetoothService.LocalBinder mLocalBinder = (BluetoothService.LocalBinder) service;
            mService = mLocalBinder.getServiceInstance();
        }
    };



    @Override
    protected void onStart() {
        super.onStart();

        Intent mIntent = new Intent(this, BluetoothService.class);
        bindService(mIntent, mConnection, BIND_AUTO_CREATE);


        if(mService == null){
            System.out.println("----- Main Null -----");
        }
        System.out.println(mConnection);
        System.out.println(mBounded);

        Thread myThread = new Thread(){
            @Override
            public void run() {
                try{Thread.sleep(5000);}catch(Exception e){}
                mService.sendMessage("<Message123>");
            }
        };
        myThread.start();
    }



    @Override
    protected void onStop() {
        super.onStop();
        if (mBounded) {
            unbindService(mConnection);
            mBounded = false;
        }
    };





}
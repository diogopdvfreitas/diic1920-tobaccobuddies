package pt.tecnico.ulisboa.diic.tobaccobuddies;

import android.app.Service;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

import com.harrysoft.androidbluetoothserial.BluetoothManager;
import com.harrysoft.androidbluetoothserial.BluetoothSerialDevice;
import com.harrysoft.androidbluetoothserial.SimpleBluetoothDeviceInterface;

import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class BluetoothService extends Service {

    private SimpleBluetoothDeviceInterface deviceInterface;
    private LocalBinder mBinder = new LocalBinder();

    final SharedPreferences userData = getSharedPreferences(getResources().getString(R.string.user_data), Context.MODE_PRIVATE);
    final SharedPreferences.Editor editor = userData.edit();

    public class LocalBinder extends Binder {
        public BluetoothService getServiceInstance() {
            return BluetoothService.this;
        }
    }

    public BluetoothService() {
        System.out.println("----- Start service -----");

        editor.putStringSet("my_monthly_data",["3","3","0","4","6","5","1","5","4","7","4","6","8","8","5","7","9","6","7","2","9","7","1","6","1","2","10","8","2","1"]);
        editor.putStringSet("buddie_monthly_data", ["7","8","5","5","5","3","5","1","1","10","8","3","7","9","1","2","8","2","5","1","7","1","6","1","8","9","6","5","9","10"]);
        editor.putInt("my_limit", 10);
        editor.putInt("buddie_limit", 15);

        editor.commit();

        BluetoothManager  manager = BluetoothManager.getInstance();
        List<BluetoothDevice> pairedDevices = manager.getPairedDevicesList();
        BluetoothDevice arduino = null;

        for(BluetoothDevice device : pairedDevices){
            if(device.getName().equals("TB Packet")){
                arduino = device;
            }
        }

        manager.openSerialDevice(arduino.getAddress())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onConnected, this::onError);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public void sendMessage(String message){
        if(deviceInterface == null){
            System.out.println("----- Service Null -----");
        }
        deviceInterface.sendMessage(message);
    }

    private void onConnected(BluetoothSerialDevice connectedDevice) {
        // You are now connected to this device!
        // Here you may want to retain an instance to your device:
        deviceInterface = connectedDevice.toSimpleDeviceInterface();

        // Listen to bluetooth events
        deviceInterface.setListeners(this::onMessageReceived, this::onMessageSent, this::onError);

        // Let's send a message:
        deviceInterface.sendMessage("<LIMIT " + userData.getInt("my_limit", -1) + ">");
        deviceInterface.sendMessage("<BUDDIE_LIMIT " + userData.getInt("buddie_limit", -1) + ">");
    }

    private void onMessageSent(String message) {
        // We sent a message! Handle it here.
        Toast.makeText(getApplicationContext(), "Sent a message! Message was: " + message, Toast.LENGTH_LONG).show(); // Replace context with your context instance.
    }

    private void onMessageReceived(String message) {
        // We received a message! Handle it here.
        Toast.makeText(getApplicationContext(), "Received a message! Message was: " + message, Toast.LENGTH_LONG).show(); // Replace context with your context instance.
        String command = message.split(" ")[0];
        switch(command){
            case "SMOKED":
                int numCigsSmoked = Integer.parseInt(message.split(" ")[1]);
                editor.putInt("my_cigs_smoked", numCigsSmoked);
                break;

            case "BUDDIE_SMOKED":
                int numBuddyCigsSmoked = Integer.parseInt(message.split(" ")[1]);
                editor.putInt("buddie_cigs_smoked", numBuddyCigsSmoked);
                break;
        }
        
        editor.commit();
    }

    private void onError(Throwable error) {
        // Handle the error
    }
}
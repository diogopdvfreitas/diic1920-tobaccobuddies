package pt.tecnico.ulisboa.diic.tobaccobuddies;

import android.app.Service;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
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

    public class LocalBinder extends Binder {
        public BluetoothService getServiceInstance() {
            return BluetoothService.this;
        }
    }

    public BluetoothService() {
        System.out.println("----- Start service -----");

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
        deviceInterface.sendMessage("<Hello world!>");
    }

    private void onMessageSent(String message) {
        // We sent a message! Handle it here.
        Toast.makeText(getApplicationContext(), "Sent a message! Message was: " + message, Toast.LENGTH_LONG).show(); // Replace context with your context instance.
    }

    private void onMessageReceived(String message) {
        // We received a message! Handle it here.
        Toast.makeText(getApplicationContext(), "Received a message! Message was: " + message, Toast.LENGTH_LONG).show(); // Replace context with your context instance.
    }

    private void onError(Throwable error) {
        // Handle the error
    }
}
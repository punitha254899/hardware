package com.example.cleaningrobot;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    BluetoothAdapter bluetoothAdapter;
    BluetoothSocket bluetoothSocket;
    OutputStream outputStream;
    BluetoothDevice device;

    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static final String DEVICE_NAME = "HC-05"; // Or HC-06

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        device = findBluetoothDevice(DEVICE_NAME);
        connectToDevice();

        Button forwardBtn = findViewById(R.id.btnForward);
        Button backBtn = findViewById(R.id.btnBack);
        Button leftBtn = findViewById(R.id.btnLeft);
        Button rightBtn = findViewById(R.id.btnRight);
        Button stopBtn = findViewById(R.id.btnStop);

        forwardBtn.setOnClickListener(v -> sendCommand("F"));
        backBtn.setOnClickListener(v -> sendCommand("B"));
        leftBtn.setOnClickListener(v -> sendCommand("L"));
        rightBtn.setOnClickListener(v -> sendCommand("R"));
        stopBtn.setOnClickListener(v -> sendCommand("S"));
    }

    private BluetoothDevice findBluetoothDevice(String name) {
        for (BluetoothDevice btDevice : bluetoothAdapter.getBondedDevices()) {
            if (btDevice.getName().equals(name)) {
                return btDevice;
            }
        }
        return null;
    }

    private void connectToDevice() {
        try {
            bluetoothSocket = device.createRfcommSocketToServiceRecord(MY_UUID);
            bluetoothSocket.connect();
            outputStream = bluetoothSocket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendCommand(String cmd) {
        try {
            outputStream.write(cmd.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

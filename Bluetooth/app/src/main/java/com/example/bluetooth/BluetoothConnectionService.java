package com.example.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.UUID;

public class BluetoothConnectionService {
    private static final String TAG = "BluetoothConnectionServ";
    private static final String appName = "MYAPP";
    private static final UUID MY_UUID_INSECURE = UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66");
    private final BluetoothAdapter mBluetoothAdapter;
    Context mContext;

    private AcceptThread mInsecureAcceptThread;

    public BluetoothConnectionService(Context mContext) {
        this.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        this.mContext = mContext;
    }

    private class AcceptThread extends Thread {
        private final BluetoothServerSocket mServerSocket;
        public AcceptThread(){
            BluetoothServerSocket tmp = null;
            try{
                tmp = mBluetoothAdapter.listenUsingInsecureRfcommWithServiceRecord(appName, MY_UUID_INSECURE);
                Log.d(TAG, "AcceptThread: Setting up Server using: " + MY_UUID_INSECURE);
            } catch (IOException e){

            }
            mServerSocket = tmp;
        }
        public void run(){
            Log.d(TAG, "run: AcceptThread Running.");
            BluetoothServerSocket socket = null;
            try{
                Log.d(TAG, "run: RFCOM server socket accepted connection.");
            }
            catch (IOException e){
                Log.e(TAG, "AcceptThread: IOExeption: " + e.getMessage());
            }
            if(socket != )

        }
    }


}

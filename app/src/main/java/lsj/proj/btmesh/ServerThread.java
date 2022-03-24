package lsj.proj.btmesh;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.core.graphics.drawable.DrawableCompat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;


class ServerThread extends BTConnectThread {
    private final BluetoothSocket mmSocket;
    private final BluetoothDevice mmDevice;

    private Handler btHandler;
    public static String TAG = "BT";
    public String MY_UUID = "";
    public BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    public Button connectButtonFirst;
    public static boolean running=false;
    public Activity toastActivity;
    public ServerThread(BluetoothDevice device, Button connectButtonFirst, String uuid) {
        macAddress=device.getAddress();
        this.MY_UUID=uuid;
        running=true;
        // Use a temporary object that is later assigned to mmSocket
        // because mmSocket is final.
        this.connectButtonFirst=connectButtonFirst;
        BluetoothSocket tmp = null;
        mmDevice = device;


        try {
            // Get a BluetoothSocket to connect with the given BluetoothDevice.
            // MY_UUID is the app's UUID string, also used in the server code.
            tmp = device.createRfcommSocketToServiceRecord(UUID.fromString(MY_UUID));
        } catch (IOException e) {
            Log.e(TAG, "Socket's create() method failed", e);
        }
        mmSocket = tmp;
    }

    public void run() {
        // Cancel discovery because it otherwise slows down the connection.
        bluetoothAdapter.cancelDiscovery();
        Log.println(Log.ASSERT, TAG, "Trying to connect as client");
        try {
            // Connect to the remote device through the socket. This call blocks
            // until it succeeds or throws an exception.
            mmSocket.connect();
        } catch (IOException connectException) {
            // Unable to connect; close the socket and return.
            Log.e(TAG, "Socket could not connect", connectException);
            try {
                mmSocket.close();
            } catch (IOException closeException) {
                Log.e(TAG, "Could not close the client socket", closeException);
            }
            running=false;
            return;
        }

        // The connection attempt succeeded. Perform work associated with
        // the connection in a separate thread.
        Log.println(Log.ASSERT, TAG, "Success");
        manageMyConnectedSocket(mmSocket);
        running=false;
    }

    private void manageMyConnectedSocket(BluetoothSocket mmSocket) {
        setButtonColor(true);
        Date currentTime;
        String messageIn;


        try {
            connectionInStream =mmSocket.getInputStream();
            connectionOutStream =mmSocket.getOutputStream();
            while (true) {
                currentTime= Calendar.getInstance().getTime();
                //write(currentTime.toString().getBytes());
                messageIn=new String(read());
                BTConnectThread.fragment.proliferate(messageIn);
                toastOnUI(messageIn);
                Thread.sleep(1500);
            }
        } catch (Exception e) {
            setButtonColor(false);
            Log.e(TAG, "Socket Disconnect", e);
        }
    }



    public void toastOnUI(String msg){

        Runnable showToast = new Runnable() {
            @Override
            public void run() {
                Toast.makeText(toastActivity, msg,
                        Toast.LENGTH_LONG).show();
            }
        };
        toastActivity.runOnUiThread(showToast);
    }

    public void setButtonColor(boolean conected){

        Runnable changeColor = new Runnable() {
            @Override
            public void run() {

                int color=0;
                if(conected)
                    color=Color.GREEN;
                else
                    color=Color.RED;
                Drawable buttonDraw = connectButtonFirst.getBackground();
                buttonDraw = DrawableCompat.wrap(buttonDraw);
                DrawableCompat.setTint(buttonDraw, color);
                connectButtonFirst.setBackground(buttonDraw);
            }
        };
        toastActivity.runOnUiThread(changeColor);
    }

    // Closes the client socket and causes the thread to finish.
    public void cancel() {
        setButtonColor(false);
        try {
            mmSocket.close();
        } catch (IOException e) {
            Log.e(TAG, "Could not close the client socket", e);
        }
        running=false;
    }
}

package lsj.proj.btmesh;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.renderscript.RenderScript;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.core.graphics.drawable.DrawableCompat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.spec.ECField;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class ServerThread extends BTConnectThread {
    private final BluetoothServerSocket mmServerSocket;

    public String TAG = "Bluetooth_mesh_LDP_1999";
    public String NAME = "BTMesh Server";
    public String MY_UUID = "";
    public static boolean running = false;
    public Button connectButtonSecond;
    public Activity toastActivity;

    public ServerThread(Button connectButtonSecond, String uuid) {

        this.MY_UUID=uuid;
        running = true;
        this.connectButtonSecond = connectButtonSecond;
        // Use a temporary object that is later assigned to mmServerSocket
        // because mmServerSocket is final.
        Log.println(Log.ASSERT, TAG, "Trying to connect as Server");
        BluetoothServerSocket tmp = null;
        try {
            // MY_UUID is the app's UUID string, also used by the client code.
            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            tmp = bluetoothAdapter.listenUsingRfcommWithServiceRecord(NAME, UUID.fromString(MY_UUID));
        } catch (IOException e) {
            Log.e(TAG, "Socket's listen() method failed", e);
        }
        mmServerSocket = tmp;
    }

    public void run() {
        BluetoothSocket socket = null;
        // Keep listening until exception occurs or a socket is returned.
        while (true) {
            try {

                socket = mmServerSocket.accept();
            } catch (IOException e) {
                Log.e(TAG, "Socket's accept() method failed", e);
                break;
            }

            if (socket != null) {
                // A connection was accepted. Perform work associated with
                // the connection in a separate thread.
                manageMyConnectedSocket(socket);
                Log.println(Log.ASSERT, TAG, "Success");
                /*try {
                    mmServerSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;*/
            }
        }
        running = false;
    }

    private void manageMyConnectedSocket(BluetoothSocket mmSocket) {

        //Date currentTime;
        String messageIn;
        setButtonColor(true);
        try {
            connectionInStream=mmSocket.getInputStream();
            connectionOutStream=mmSocket.getOutputStream();
            while (true) {
                //currentTime= Calendar.getInstance().getTime();
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
                Drawable buttonDraw = connectButtonSecond.getBackground();
                buttonDraw = DrawableCompat.wrap(buttonDraw);
                DrawableCompat.setTint(buttonDraw, color);
                connectButtonSecond.setBackground(buttonDraw);
            }
        };
        toastActivity.runOnUiThread(changeColor);
    }


    // Closes the connect socket and causes the thread to finish.
    public void cancel() {
        setButtonColor(false);
        try {
            mmServerSocket.close();
        } catch (IOException e) {

            Log.e(TAG, "Could not close the connect socket", e);
        }
        running = false;
    }
}

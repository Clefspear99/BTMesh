package lsj.proj.btmesh;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

//Things to add:
//Don't pass on messages you've already received / sent
//1. Encryption
//2. Group / single messaging
//3. Contacts / list of people on network
//4. Change to service
//5. Text message interface

public class FirstFragment extends Fragment {
    private static ServerThread serverThread1;
    public static ClientThread clientThread1;
    private static ServerThread serverThread2;
    public static ClientThread clientThread2;
    public final String UUID1="60a886c5-4650-47a9-afd1-2525bd60aa73";
    public final String UUID2="6a90a3a1-4653-4d20-871b-c5c65861cabe";
    private ArrayList<BTConnectThread> threadsList = new ArrayList<BTConnectThread>();
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        BTConnectThread.fragment=this;
        super.onViewCreated(view, savedInstanceState);

        /*Button fragChanger = view.findViewById(R.id.button_first);
        fragChanger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }

        });*/

        Button connectButtonFirst = view.findViewById(R.id.connect_button_client1);
        Drawable buttonDraw = connectButtonFirst.getBackground();
        buttonDraw = DrawableCompat.wrap(buttonDraw);
        DrawableCompat.setTint(buttonDraw, Color.RED);
        connectButtonFirst.setBackground(buttonDraw);

        connectButtonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (serverThread1 ==null || !serverThread1.isAlive()) {
                    BluetoothDevice device = BluetoothAdapter.getDefaultAdapter().getBondedDevices().iterator().next();
                    serverThread1 =  new ServerThread(device, connectButtonFirst, UUID1);
                    serverThread1.toastActivity=getActivity();
                    threadsList.add(serverThread1);
                    serverThread1.start();
                }

            }
        });



        Button connectButtonSecond = view.findViewById(R.id.connect_button_server1);
        buttonDraw = connectButtonSecond.getBackground();
        buttonDraw = DrawableCompat.wrap(buttonDraw);
        DrawableCompat.setTint(buttonDraw, Color.RED);
        connectButtonSecond.setBackground(buttonDraw);

        connectButtonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (clientThread1 ==null || !clientThread1.isAlive()) {
                    clientThread1 =  new ClientThread(connectButtonSecond, UUID1);
                    clientThread1.toastActivity=getActivity();
                    threadsList.add(clientThread1);
                    clientThread1.start();
                }
            }
        });




        Button connectButtonClientSecond = view.findViewById(R.id.connect_button_client2);
        buttonDraw = connectButtonClientSecond.getBackground();
        buttonDraw = DrawableCompat.wrap(buttonDraw);
        DrawableCompat.setTint(buttonDraw, Color.RED);
        connectButtonClientSecond.setBackground(buttonDraw);

        connectButtonClientSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (serverThread2 ==null || !serverThread2.isAlive()) {
                    Iterator it =  BluetoothAdapter.getDefaultAdapter().getBondedDevices().iterator();
                    BluetoothDevice device = (BluetoothDevice) it.next();
                    device = (BluetoothDevice) it.next();
                    serverThread2 =  new ServerThread(device, connectButtonClientSecond, UUID2);
                    serverThread2.toastActivity=getActivity();
                    threadsList.add(serverThread2);
                    serverThread2.start();
                }

            }
        });



        Button connectButtonServerSecond = view.findViewById(R.id.connect_button_server2);
        buttonDraw = connectButtonServerSecond.getBackground();
        buttonDraw = DrawableCompat.wrap(buttonDraw);
        DrawableCompat.setTint(buttonDraw, Color.RED);
        connectButtonServerSecond.setBackground(buttonDraw);

        connectButtonServerSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (clientThread2 ==null || !clientThread2.isAlive()) {
                    clientThread2 =  new ClientThread(connectButtonServerSecond, UUID2);
                    clientThread2.toastActivity=getActivity();
                    threadsList.add(clientThread2);
                    clientThread2.start();
                }
            }
        });


        Button actionButton1 = view.findViewById(R.id.action_button_1);
        actionButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (clientThread1 !=null && clientThread1.isAlive()) {
                    Date currentTime;
                    currentTime= Calendar.getInstance().getTime();
                    try {
                        clientThread1.write(currentTime.toString().getBytes());
                    }catch(IOException e) {
                        Log.e(clientThread1.TAG, "Error sending message over BT", e);
                    }
                    return;
                    }

                if (serverThread1 !=null && serverThread1.isAlive()) {
                    Date currentTime;
                    currentTime= Calendar.getInstance().getTime();
                    try {
                        serverThread1.write(currentTime.toString().getBytes());
                    }catch(IOException e) {
                        Log.e(serverThread1.TAG, "Error sending message over BT", e);
                    }
                    return;
                }





                }

        });

        Button actionButton2 = view.findViewById(R.id.action_button_2);
        actionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (clientThread2 !=null && clientThread2.isAlive()) {
                    Date currentTime;
                    currentTime= Calendar.getInstance().getTime();
                    try {
                        clientThread2.write(currentTime.toString().getBytes());
                    }catch(IOException e) {
                        Log.e(clientThread2.TAG, "Error sending message over BT", e);
                    }
                    return;
                }

                if (serverThread2 !=null && serverThread2.isAlive()) {
                    Date currentTime;
                    currentTime= Calendar.getInstance().getTime();
                    try {
                        serverThread2.write(currentTime.toString().getBytes());
                    }catch(IOException e) {
                        Log.e(serverThread2.TAG, "Error sending message over BT", e);
                    }
                    return;
                }





            }

        });




    }

    //First 6 bytes are the senders mac address
    public void proliferate(String message){
        for(BTConnectThread thread : threadsList){
            if(thread != null && thread.isAlive()){
                try {
                    thread.write(message.getBytes());
                }catch (IOException e){
                    Log.e(ServerThread.TAG, "Error Proliferating message",e);
                }

            }
            else
                threadsList.remove(thread);
        }
    }
}
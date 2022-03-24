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
    private static ClientThread clientThread1;
    public static ServerThread server1Thread;
    private static ClientThread clientThread2;
    public static ServerThread serverThread2;
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


                if (clientThread1 ==null || !clientThread1.isAlive()) {
                    BluetoothDevice device = BluetoothAdapter.getDefaultAdapter().getBondedDevices().iterator().next();
                    clientThread1 =  new ClientThread(device, connectButtonFirst, UUID1);
                    clientThread1.toastActivity=getActivity();
                    threadsList.add(clientThread1);
                    clientThread1.start();
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

                if (server1Thread ==null || !server1Thread.isAlive()) {
                    server1Thread =  new ServerThread(connectButtonSecond, UUID1);
                    server1Thread.toastActivity=getActivity();
                    threadsList.add(server1Thread);
                    server1Thread.start();
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


                if (clientThread2 ==null || !clientThread2.isAlive()) {
                    Iterator it =  BluetoothAdapter.getDefaultAdapter().getBondedDevices().iterator();
                    BluetoothDevice device = (BluetoothDevice) it.next();
                    device = (BluetoothDevice) it.next();
                    clientThread2 =  new ClientThread(device, connectButtonClientSecond, UUID2);
                    clientThread2.toastActivity=getActivity();
                    threadsList.add(clientThread2);
                    clientThread2.start();
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

                if (serverThread2 ==null || !serverThread2.isAlive()) {
                    serverThread2 =  new ServerThread(connectButtonServerSecond, UUID2);
                    serverThread2.toastActivity=getActivity();
                    threadsList.add(serverThread2);
                    serverThread2.start();
                }
            }
        });


        Button actionButton1 = view.findViewById(R.id.action_button_1);
        actionButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (server1Thread !=null && server1Thread.isAlive()) {
                    Date currentTime;
                    currentTime= Calendar.getInstance().getTime();
                    try {
                        //clientThread1.write(currentTime.toString().getBytes());
                        server1Thread.write(test.getBytes());
                    }catch(IOException e) {
                        Log.e(server1Thread.TAG, "Error sending message over BT", e);
                    }
                    return;
                    }

                if (clientThread1 !=null && clientThread1.isAlive()) {
                    Date currentTime;
                    currentTime= Calendar.getInstance().getTime();
                    try {
                        //serverThread1.write(currentTime.toString().getBytes());
                        server1Thread.write(test.getBytes());
                    }catch(IOException e) {
                        Log.e(clientThread1.TAG, "Error sending message over BT", e);
                    }
                    return;
                }





                }

        });

        Button actionButton2 = view.findViewById(R.id.action_button_2);
        actionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (serverThread2 !=null && serverThread2.isAlive()) {
                    Date currentTime;
                    currentTime= Calendar.getInstance().getTime();
                    try {
                        //clientThread2.write(currentTime.toString().getBytes());
                        server1Thread.write(test.getBytes());
                    }catch(IOException e) {
                        Log.e(serverThread2.TAG, "Error sending message over BT", e);
                    }
                    return;
                }

                if (clientThread2 !=null && clientThread2.isAlive()) {
                    Date currentTime;
                    currentTime= Calendar.getInstance().getTime();
                    try {
                        //serverThread2.write(currentTime.toString().getBytes());
                        server1Thread.write(test.getBytes());
                    }catch(IOException e) {
                        Log.e(clientThread2.TAG, "Error sending message over BT", e);
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
                    Log.e(ClientThread.TAG, "Error Proliferating message",e);
                }

            }
            else
                threadsList.remove(thread);
        }
    }

    public final String test = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Natoque penatibus et magnis dis parturient. At lectus urna duis convallis convallis. Urna neque viverra justo nec ultrices dui sapien eget. Est velit egestas dui id ornare arcu odio. Elementum eu facilisis sed odio morbi quis commodo. Sit amet massa vitae tortor. Scelerisque eu ultrices vitae auctor eu augue ut. Odio ut sem nulla pharetra. Facilisis mauris sit amet massa. Leo vel orci porta non pulvinar neque laoreet suspendisse interdum. Porttitor lacus luctus accumsan tortor posuere ac ut. Congue mauris rhoncus aenean vel elit scelerisque mauris pellentesque. Sagittis nisl rhoncus mattis rhoncus urna neque viverra justo. Sed faucibus turpis in eu mi bibendum neque. Morbi blandit cursus risus at. Quis imperdiet massa tincidunt nunc pulvinar. At risus viverra adipiscing at in tellus.\n" +
            "\n" +
            "Id cursus metus aliquam eleifend mi in nulla posuere. Eu sem integer vitae justo eget magna fermentum iaculis. Pharetra pharetra massa massa ultricies mi. Nam libero justo laoreet sit amet cursus sit amet dictum. Dignissim suspendisse in est ante in nibh mauris cursus mattis. Tortor vitae purus faucibus ornare suspendisse sed. Cras pulvinar mattis nunc sed blandit libero. Purus in mollis nunc sed id semper risus in. Sociis natoque penatibus et magnis dis parturient montes nascetur ridiculus. Et ultrices neque ornare aenean. Et pharetra pharetra massa massa ultricies mi. At urna condimentum mattis pellentesque. Bibendum ut tristique et egestas quis. Ultricies mi eget mauris pharetra et ultrices neque ornare. Leo in vitae turpis massa sed elementum tempus egestas. Volutpat commodo sed egestas egestas fringilla phasellus faucibus scelerisque. In ornare quam viverra orci sagittis eu volutpat odio. Purus in mollis nunc sed id semper risus in. Bibendum ut tristique et egestas quis ipsum suspendisse ultrices. Mi sit amet mauris commodo quis imperdiet.\n" +
            "\n" +
            "Adipiscing elit ut aliquam purus sit amet luctus. Enim diam vulputate ut pharetra sit. Leo vel fringilla est ullamcorper eget nulla. Magna sit amet purus gravida quis. Malesuada fames ac turpis egestas integer eget. Molestie a iaculis at erat pellentesque. Risus quis varius quam quisque id diam vel. Mattis rhoncus urna neque viverra justo. Dui nunc mattis enim ut tellus elementum. Id eu nisl nunc mi. Suspendisse faucibus interdum posuere lorem. Phasellus vestibulum lorem sed risus ultricies tristique nulla aliquet. Nec nam aliquam sem et tortor consequat. Iaculis nunc sed augue lacus viverra vitae congue. Risus at ultrices mi tempus imperdiet. Nulla facilisi cras fermentum odio eu feugiat pretium nibh. Sagittis nisl rhoncus mattis rhoncus urna neque. Adipiscing diam donec adipiscing tristique risus nec feugiat in. Praesent semper feugiat nibh sed pulvinar proin. Hac habitasse platea dictumst quisque sagittis.\n" +
            "\n" +
            "Morbi leo urna molestie at. Libero volutpat sed cras ornare arcu dui vivamus. Laoreet suspendisse interdum consectetur libero id faucibus nisl tincidunt. Adipiscing tristique risus nec feugiat in fermentum posuere urna. Quis varius quam quisque id diam. Suspendisse in est ante in nibh mauris cursus mattis. Et odio pellentesque diam volutpat commodo. Malesuada nunc vel risus commodo viverra maecenas accumsan lacus. Pellentesque elit ullamcorper dignissim cras tincidunt lobortis feugiat vivamus at. Vitae turpis massa sed elementum tempus. Pulvinar elementum integer enim neque volutpat. Convallis posuere morbi leo urna molestie at. Urna molestie at elementum eu facilisis sed odio morbi. Lacus luctus accumsan tortor posuere.\n" +
            "\n" +
            "Sed viverra tellus in hac habitasse platea dictumst. Amet consectetur adipiscing elit pellentesque habitant. Gravida quis blandit turpis cursus in hac habitasse. Euismod quis viverra nibh cras. In fermentum et sollicitudin ac orci phasellus egestas. Aliquam vestibulum morbi blandit cursus risus at ultrices mi tempus. Quis hendrerit dolor magna eget est lorem. Faucibus nisl tincidunt eget nullam non nisi est sit. Viverra aliquet eget sit amet tellus. Id cursus metus aliquam eleifend mi in nulla. Turpis nunc eget lorem dolor sed viverra ipsum nunc. Sit amet justo donec enim. Etiam erat velit scelerisque in dictum. Pharetra massa massa ultricies mi quis. Lobortis scelerisque fermentum dui faucibus in ornare.\n" +
            "\n" +
            "Porta non pulvinar neque laoreet suspendisse interdum consectetur libero id. Amet massa vitae tortor condimentum lacinia quis vel eros. Tortor at risus viverra adipiscing. Eget sit amet tellus cras adipiscing enim. Sed odio morbi quis commodo odio aenean sed adipiscing diam. Urna cursus eget nunc scelerisque viverra. Sodales neque sodales ut etiam sit amet nisl. Varius quam quisque id diam vel. Egestas maecenas pharetra convallis posuere morbi leo urna molestie at. Leo in vitae turpis massa. Iaculis at erat pellentesque adipiscing commodo elit at imperdiet dui. Mi in nulla posuere sollicitudin. Morbi enim nunc faucibus a pellentesque sit amet porttitor. Sollicitudin nibh sit amet commodo. Viverra orci sagittis eu volutpat odio facilisis mauris sit. Habitasse platea dictumst vestibulum rhoncus.\n" +
            "\n" +
            "Lacus vel facilisis volutpat est velit egestas dui id ornare. Metus dictum at tempor commodo ullamcorper a lacus vestibulum sed. Blandit volutpat maecenas volutpat blandit aliquam etiam erat velit. Tincidunt praesent semper feugiat nibh sed pulvinar proin gravida. At volutpat diam ut venenatis tellus. Volutpat lacus laoreet non curabitur gravida arcu ac tortor. Ut sem nulla pharetra diam sit amet nisl suscipit adipiscing. Magna sit amet purus gravida quis blandit turpis cursus in. Lectus nulla at volutpat diam ut venenatis tellus. Amet commodo nulla facilisi nullam vehicula ipsum a arcu. Metus vulputate eu scelerisque felis imperdiet proin. Semper quis lectus nulla at volutpat. Ipsum dolor sit amet consectetur adipiscing.\n" +
            "\n" +
            "Sed lectus vestibulum mattis ullamcorper velit sed. Nisl condimentum id venenatis a condimentum. Est ultricies integer quis auctor elit sed. Diam donec adipiscing tristique risus nec. Nisl purus in mollis nunc sed id semper. Sit amet tellus cras adipiscing enim eu. Commodo nulla facilisi nullam vehicula ipsum a arcu. Eget aliquet nibh praesent tristique magna sit. Pulvinar proin gravida hendrerit lectus. Elementum curabitur vitae nunc sed. Lacus luctus accumsan tortor posuere ac ut consequat semper. Bibendum enim facilisis gravida neque convallis. Commodo odio aenean sed adipiscing diam donec. Euismod elementum nisi quis eleifend quam adipiscing.";

}
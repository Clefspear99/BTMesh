package lsj.proj.btmesh;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;

public abstract class BTConnectThread extends Thread{
    public static FirstFragment fragment;
    public byte id=1;
    protected InputStream connectionInStream = null;
    protected  OutputStream connectionOutStream = null;
    protected String macAddress;


    public byte[] read() throws IOException{
        byte[] inBuffer = new byte[1024];
        int byteCount=connectionInStream.read(inBuffer);
        return inBuffer;
    }

    public String readString() throws IOException{
        StringBuilder stringBuilder = new StringBuilder();
        byte[] in;
        int parts=1;

        for(int j=1; j<=parts; j++) {
            in = read();
            parts=(int) in[1];
            for (int i = 4; i < in.length; i++) {
                stringBuilder.append(in[i]);
            }

        }
        return stringBuilder.toString();
    }

    //0 byte is message type, 0 for initial message send, 1 for message proliferation, 2 for initial intro, 3 for intro proliferation

    //1 is total messages,

    //2 is what message it is (i.e. first, second etc)

    //3 byte is unique ID (0 is for unassigned id)


    public void write(int type, int id, byte[] bytes) throws IOException{
        if(bytes.length==0)
            return;

        int parts = bytes.length/1020;
        parts++;
        if(bytes.length%1020==0)
            parts--;

        byte[] arr;
        int max;

        if(parts>1) {
            arr = new byte[1024];
            max = 1024;
        }else{
            arr = new byte[bytes.length+4];
            max=bytes.length+4;
        }



        arr[0]=(byte) type;
        arr[1]=(byte) parts;
        arr[3]=(byte) id;


        int offset;
        for(int j =1; j<=parts; j++){

            arr[2]=(byte) j;
            offset=(j-1)*1021;


            for(int i=3; i<max; i++)
                arr[i]=bytes[i-3+offset];
            connectionOutStream.write(bytes);
        }
    }

    public void write(byte[] bytes) throws IOException{
        write(0, id, bytes);
    }
}

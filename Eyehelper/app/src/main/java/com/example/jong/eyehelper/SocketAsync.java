package com.example.jong.eyehelper;


import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class SocketAsync extends AsyncTask<String, Void, String> {
    public SocketCallback cb;
    public SocketAsync(SocketCallback cb){
         this.cb = cb;
    }
    @Override
    protected String doInBackground(String[] strings) {
        int port = 8888;
        int BUFFER_LENGTH = 128;
        Socket socket = null;
        DataOutputStream dataOutputStream = null;
        BufferedReader bufferedReader = null; // todo: rename to sth right.
        String ipAddress = strings[0];
        String messageToSend = strings[1];
        try {
            socket = new Socket(ipAddress, port);
//            socket = new Socket("10.7.64.225", 8888);
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            dataOutputStream.writeUTF(messageToSend);



            Log.d("socket_info", "be receive");
            byte[] buffer = new byte[BUFFER_LENGTH];
            int bytes_received;
            String receivedMessage = "";
            receivedMessage += bufferedReader.readLine();

//            else{
//                Thread.sleep(1000);
//            }
//            bytes_received = bufferedReader.read(buffer);

//            while ((bytes_received = bufferedReader.read(buffer)) > 0) {
//            receivedMessage += new String(buffer, "UTF-8");
//            Log.d("socket_info", "in while" + receivedMessage);
//            }
//            Log.d("socket_info", receivedMessage);

//            dataOutputStream.flush();
            dataOutputStream.close();
            bufferedReader.close();
            socket.close();

            return receivedMessage;


        } catch (IOException ex) {
            Log.e("socket_error", ex.getMessage());
        }
        return "error";
    }
    @Override
    protected void onPostExecute(String receivedData){
        cb.onTaskCompleted(receivedData);
    }

}
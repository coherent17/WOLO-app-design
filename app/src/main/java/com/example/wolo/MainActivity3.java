package com.example.wolo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class MainActivity3 extends AppCompatActivity {

    String SERVER_IP;
    int SERVER_PORT;
    String temp;
    Socket socket;
    MyCanvas canvas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        canvas = (MyCanvas)findViewById(R.id.myview);

        //get ip address and port from activity1
        Bundle bundle = this.getIntent().getExtras();
        SERVER_IP = (String)bundle.getString("IP");
        SERVER_PORT = (int)bundle.getInt("Port");
        Thread t = new Thread(readData);
        t.start();
    }


    private Runnable readData = new Runnable() {
        public void run() {

            try {
                socket = new Socket(SERVER_IP, SERVER_PORT);
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                while (socket.isConnected()) {
                    temp = br.readLine();
                    if(temp!= null){
                        System.out.println("receive_msg: " + temp);
                        if(temp.length() < 3){
                            int num_box_in_frame = Integer.valueOf(temp);
                            canvas.num_BBox = num_box_in_frame;
                            canvas.lefts = new float[num_box_in_frame];
                            canvas.tops = new float[num_box_in_frame];
                            canvas.rights = new float[num_box_in_frame];
                            canvas.bottoms = new float[num_box_in_frame];
                            canvas.distances = new float[num_box_in_frame];

                            System.out.println(num_box_in_frame);
                            for(int i = 0; i < num_box_in_frame; i++){
                                temp = br.readLine();
                                System.out.println(temp);
                                String[] parts = temp.split(" ");
                                float left = Float.parseFloat(parts[0]);
                                float top = Float.parseFloat(parts[1]);
                                float right = Float.parseFloat(parts[2]);
                                float bottom = Float.parseFloat(parts[3]);
                                float distance = Float.parseFloat(parts[4]);
                                canvas.lefts[i] = left;
                                canvas.tops[i] = top;
                                canvas.rights[i] = right;
                                canvas.bottoms[i] = bottom;
                                canvas.distances[i] = distance;
                            }
                            canvas.invalidate();

                        }
                    }

                }
            } catch (IOException e) {
                System.out.println("Fail");
            }
        }
    };
}
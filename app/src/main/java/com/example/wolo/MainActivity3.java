package com.example.wolo;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class MainActivity3 extends AppCompatActivity {

    String SERVER_IP;
    int SERVER_PORT;
    String temp;
    Socket socket;

    TextView tvRcvMsg;
    ImageView img;
    public static Handler mHandler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        getWindow().setBackgroundDrawableResource(R.drawable.road);

        tvRcvMsg = findViewById(R.id.tvRcvMsg);
        img= findViewById(R.id.img);

        //get ip address and port from activity1
        Bundle bundle = this.getIntent().getExtras();
        SERVER_IP = (String)bundle.getString("IP");
        SERVER_PORT = (int)bundle.getInt("Port");
        Thread t = new Thread(readData);
        t.start();
    }

    private Runnable updateText = new Runnable() {
        public void run() {
            // 加入新訊息並換行
            tvRcvMsg.setText(temp + "\n");
            String uri = "@drawable/" + temp;
            int imageResource = getResources().getIdentifier(uri, null, getPackageName()); //取得圖片Resource位子
            img.setImageResource(imageResource);
        }
    };

    private Runnable readData = new Runnable() {
        public void run() {

            try {
                // 以內定(本機電腦端)IP為Server端
                socket = new Socket(SERVER_IP, SERVER_PORT);

                // 取得網路輸入串流
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                // 當連線後
                while (socket.isConnected()) {
                    // 取得網路訊息
                    temp = br.readLine();

                    // 如果不是空訊息則
                    if(temp!=null){
                        System.out.println(temp);
                        mHandler.post(updateText);
                    }
                }
            } catch (IOException e) {

            }
        }
    };
}
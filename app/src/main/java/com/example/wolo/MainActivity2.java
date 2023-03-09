package com.example.wolo;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity2 extends AppCompatActivity {

    private Handler mMainHandler;
    private Socket socket;
    private ExecutorService mThreadPool;
    String response;

    EditText etIP, etPort;
    TextView tvMessages;
    String SERVER_IP;
    int SERVER_PORT;

    InputStream is;
    InputStreamReader isr ;
    BufferedReader br ;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        etIP = findViewById(R.id.etIP);
        etPort = findViewById(R.id.etPort);
        tvMessages = findViewById(R.id.tvMessages);
        Button btnConnect = findViewById(R.id.btnConnect);
        mThreadPool = Executors.newCachedThreadPool();

        mMainHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                System.out.println(response);
                tvMessages.setText(response);
            }
        };

        btnConnect.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                tvMessages.setText("");
                SERVER_IP = etIP.getText().toString().trim();
                SERVER_PORT = Integer.parseInt(etPort.getText().toString().trim());

                mThreadPool.execute(new Runnable() {
                    @Override
                    public void run() {

                        try {

                            // 创建Socket对象 & 指定服务端的IP 及 端口号
                            socket = new Socket(SERVER_IP, SERVER_PORT);

                            System.out.println(socket.isConnected());

                            is = socket.getInputStream();
                            isr = new InputStreamReader(is);
                            br = new BufferedReader(isr);
                            response = br.readLine();
                            System.out.println("fsgfdg");
                            System.out.println(response);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                });

            }
        });

        tvMessages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 利用线程池直接开启一个线程 & 执行该线程
                mThreadPool.execute(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            is = socket.getInputStream();
                            isr = new InputStreamReader(is);
                            br = new BufferedReader(isr);
                            response = br.readLine();
                            Message msg = Message.obtain();
                            msg.what = 0;
                            mMainHandler.sendMessage(msg);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                });

            }
        });

    }
}
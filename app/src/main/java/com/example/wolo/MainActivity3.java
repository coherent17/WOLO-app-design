package com.example.wolo;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.concurrent.ThreadLocalRandom;

public class MainActivity3 extends AppCompatActivity {

    String SERVER_IP;
    int SERVER_PORT;
    String temp;
    Socket socket;

    ImageView img00, img01, img02;
    ImageView img10, img11, img12;
    ImageView img20, img21, img22, img23;
    ImageView img30, img31, img32, img33;
    ImageView img40, img41, img42, img43, img44;
    ImageView[] IMGS = new ImageView[19];
    public static Handler mHandler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        getWindow().setBackgroundDrawableResource(R.drawable.road);

        img00 = findViewById(R.id.img00);
        img01 = findViewById(R.id.img01);
        img02 = findViewById(R.id.img02);

        img10 = findViewById(R.id.img10);
        img11 = findViewById(R.id.img11);
        img12 = findViewById(R.id.img12);

        img20 = findViewById(R.id.img20);
        img21 = findViewById(R.id.img21);
        img22 = findViewById(R.id.img22);
        img23 = findViewById(R.id.img23);

        img30 = findViewById(R.id.img30);
        img31 = findViewById(R.id.img31);
        img32 = findViewById(R.id.img32);
        img33 = findViewById(R.id.img33);

        img40 = findViewById(R.id.img40);
        img41 = findViewById(R.id.img41);
        img42 = findViewById(R.id.img42);
        img43 = findViewById(R.id.img43);
        img44 = findViewById(R.id.img44);

        IMGS[0] = img00;
        IMGS[1] = img01;
        IMGS[2] = img02;

        IMGS[3] = img10;
        IMGS[4] = img11;
        IMGS[5] = img12;

        IMGS[6] = img20;
        IMGS[7] = img21;
        IMGS[8] = img22;
        IMGS[9] = img23;

        IMGS[10] = img30;
        IMGS[11] = img31;
        IMGS[12] = img32;
        IMGS[13] = img33;

        IMGS[14] = img40;
        IMGS[15] = img41;
        IMGS[16] = img42;
        IMGS[17] = img43;
        IMGS[18] = img44;

        //get ip address and port from activity1
        Bundle bundle = this.getIntent().getExtras();
        SERVER_IP = (String)bundle.getString("IP");
        SERVER_PORT = (int)bundle.getInt("Port");
        Thread t = new Thread(readData);
        t.start();
    }

    private Runnable updateText = new Runnable() {
        public void run() {
            String uri = "@drawable/" + temp;
            int imageResource = getResources().getIdentifier(uri, null, getPackageName()); //取得圖片Resource位子
            int randomNum = ThreadLocalRandom.current().nextInt(0, 18 + 1);
            IMGS[randomNum].setImageResource(imageResource);
            System.out.println("Setting : " + temp + " at IMGS" + randomNum);
        }
    };

    private Runnable ResetImage = new Runnable() {
        public void run() {
            for(int i = 0; i <= 18; i++){
                IMGS[i].setImageResource(0);
            }
            System.out.println("Reset");
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
                mHandler.post(ResetImage);
                while (socket.isConnected()) {
                    // 取得網路訊息
                    temp = br.readLine();

                    if(temp!= null){
                        System.out.println("receive_msg: " + temp);
                        if(!temp.equals("finish")){
                            mHandler.post(updateText);
                        }
                        else{
                            mHandler.post(ResetImage);
                        }
                    }

                }
            } catch (IOException e) {

            }
        }
    };
}
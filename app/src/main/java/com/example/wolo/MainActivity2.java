package com.example.wolo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity2 extends AppCompatActivity {

    private Button entry_button;
    private EditText etIP;
    private EditText etPort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        entry_button = (Button) findViewById(R.id.entry_button);
        etIP = (EditText) findViewById(R.id.etIP);
        etPort = (EditText) findViewById(R.id.etPort);

        entry_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                entryNextPage();
            }
        });
    }

    public void entryNextPage(){
        Intent intent = new Intent(this, MainActivity3.class);
        Bundle bundle = new Bundle();
        bundle.putString("IP", etIP.getText().toString().trim());
        bundle.putInt("Port", Integer.parseInt(etPort.getText().toString().trim()));
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
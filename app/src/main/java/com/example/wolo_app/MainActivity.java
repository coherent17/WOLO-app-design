package com.example.wolo_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button entry_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        entry_button = (Button) findViewById(R.id.entry_button);
        entry_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                entryBlueToothConnectionPage();
            }
        });
    }

    public void entryBlueToothConnectionPage(){
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }
}
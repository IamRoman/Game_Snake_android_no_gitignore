package com.example.SnakeGame;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class informationActivity extends Activity {
    Button buttonBack;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.information_layout);

        buttonBack = (Button)findViewById(R.id.btnBack);

        buttonBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //Intent i = new Intent(getApplicationContext(),mainActivity.class);
                //startActivity(i);
                finish();
            }
        });
    }
}
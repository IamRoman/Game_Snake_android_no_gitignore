package com.example.SnakeGame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class mainActivity extends Activity implements View.OnClickListener {
    Button buttonStart, buttonInformation, buttonExit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        buttonStart = (Button) findViewById(R.id.btnStart);
        buttonInformation = (Button) findViewById(R.id.btnInformation);
        buttonExit = (Button) findViewById(R.id.btnExit);

        buttonStart.setOnClickListener(this);
        buttonInformation.setOnClickListener(this);
        buttonExit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent i;
        switch (view.getId()) {
            case R.id.btnStart:
                //Toast.makeText(getApplicationContext(), "click_Start", Toast.LENGTH_SHORT).show();
                i = new Intent(getApplicationContext(),gameActivity.class);
                startActivity(i);
                //finish();
                break;
            case R.id.btnInformation:
                i = new Intent(getApplicationContext(),informationActivity.class);
                startActivity(i);
                //finish();
                break;
            case R.id.btnExit:
                finish();
                break;
        }
    }
}

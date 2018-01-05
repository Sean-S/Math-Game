package com.example.sean.assignment2;

import android.content.Intent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class Finish_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_finish_);

        TextView textResult = (TextView) findViewById(R.id.textResult);
        textResult.setText("Congraulations, you've completed the game.");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void playagain(View o) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
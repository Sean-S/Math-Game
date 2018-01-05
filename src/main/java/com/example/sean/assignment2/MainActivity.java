//Sean Sexton
//sean.sexton@mycit.ie

package com.example.sean.assignment2;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.view.WindowManager.LayoutParams;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private LinearLayout linearLayout;
    private RadioButton whiteButton;
    private RadioButton blueButton;
    private Level1_Activity level1_activity;
    private SeekBar seekbar;
    private int brightness;     //Store brightness value
    private ContentResolver cResolver;      //handle system's settings
    private Window window;      //Window object, that will store a reference to the current window
    private TextView txtPerc;
    private Context context;
    private Spinner difficulty;
    private String difficultyString;
    private ImageView iv;
    private Matrix matrix = new Matrix();
    private float scale = 1f;
    private ScaleGestureDetector SGD;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        //Inflate Objects
        linearLayout = (LinearLayout) findViewById(R.id.LinearLayoutVertical);
        whiteButton = (RadioButton) findViewById(R.id.white);
        blueButton = (RadioButton) findViewById(R.id.blue);
        difficulty = (Spinner) findViewById(R.id.difficulty);

        level1_activity = new Level1_Activity();

        changeBrightness();

        iv = (ImageView) findViewById(R.id.imageView);
        SGD = new ScaleGestureDetector(this, new ScaleListener());
    }

    public boolean onTouchEvent(MotionEvent ev) {
        SGD.onTouchEvent(ev);
        return true;
    }

    private class ScaleListener extends ScaleGestureDetector.
            SimpleOnScaleGestureListener {

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            scale *= detector.getScaleFactor();
            scale = Math.max(0.1f, Math.min(scale, 5.0f));
            matrix.setScale(scale, scale);
            iv.setImageMatrix(matrix);
            return true;
        }
    }

    //If Play Button is Clicked, Level1 activity
    public void onClickLevel1_Activity(View view) {
        difficultyString = String.valueOf(difficulty.getSelectedItem().toString());
        Intent i = new Intent(this, Level1_Activity.class);
        i.putExtra("Difficulty", difficultyString);
        startActivity(i);
    }

    //If Blue radio button is selected, change background colour to blue
    public void onClickBackgroundColourBlue(View view) {
        linearLayout.setBackgroundColor(Color.rgb(173, 216, 230));
    }

    //If White radio button is selected, change background colour to white
    public void onClickBackgroundColourWhite(View view) {
        linearLayout.setBackgroundColor(Color.WHITE);
    }


    public void changeBrightness() {

        seekbar = (SeekBar) findViewById(R.id.seekBar);
        txtPerc = (TextView) findViewById(R.id.txtPercentage);
        //Set the seekbar range between 0 and 255
        //seek bar settings//
        //sets the range between 0 and 255
        seekbar.setMax(255);
        //set the seek bar progress to 1
        seekbar.setKeyProgressIncrement(1);
        cResolver = this.getApplicationContext().getContentResolver();
        context = getApplicationContext();

        try

        {
            //Get the current system brightness
            brightness = Settings.System.getInt(cResolver, Settings.System.SCREEN_BRIGHTNESS);
        } catch (
                Settings.SettingNotFoundException e
                )

        {
            //Throw an error case it couldn't be retrieved
            Log.e("Error", "Cannot access system brightness");
            e.printStackTrace();
        }

        //Set the progress of the seek bar based on the system's brightness
        seekbar.setProgress(brightness);

        //Register OnSeekBarChangeListener, so it can actually change values
        seekbar.setOnSeekBarChangeListener(new

           SeekBar.OnSeekBarChangeListener() {
               public void onStopTrackingTouch(SeekBar seekBar) {
                   //Set the system brightness using the brightness variable value
                   Settings.System.putInt(cResolver, Settings.System.SCREEN_BRIGHTNESS, brightness);
               }

               public void onStartTrackingTouch(SeekBar seekBar) {
                   //Nothing handled here
               }

               public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                   //Set the minimal brightness level
                   //if seek bar is 20 or any value below
                   if (progress <= 20) {
                       //Set the brightness to 20
                       brightness = 20;
                   } else //brightness is greater than 20
                   {
                       //Set brightness variable based on the progress bar
                       brightness = progress;
                   }
                   //Calculate the brightness percentage
                   float perc = (brightness / (float) 255) * 100;
                   //Set the brightness percentage
                   txtPerc.setText((int) perc + " %");
               }
           });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.playGame:
                difficultyString = String.valueOf(difficulty.getSelectedItem().toString());
                Intent i = new Intent(this, Level1_Activity.class);
                i.putExtra("Difficulty", difficultyString);
                startActivity(i);
                return true;
            case R.id.exit:
                finish();
                System.exit(0);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
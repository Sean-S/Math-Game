package com.example.sean.assignment2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class Level2_Activity extends AppCompatActivity {
    LinearLayout linearLayout2;
    private TextView levelLabel2;
    private TextView scoreLabel2;
    private TextView question2;
    private Button buttonA2;
    private Button buttonB2;
    private Button buttonC2;


    private List<Question> quesList2;
    private int score = 3;
    private int qid = 3;
    private Question currentQ2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_level2_);
        Toast.makeText(getApplicationContext(), "Congrats, you've made it to level 2", Toast.LENGTH_LONG).show();



        //Inflate Objects
        linearLayout2 = (LinearLayout) findViewById(R.id.LinearLayoutLevel12);
        levelLabel2 = (TextView) findViewById(R.id.levelLabel2);
        scoreLabel2 = (TextView) findViewById(R.id.scoreLabel2);
        question2 = (TextView) findViewById(R.id.txtQuestion2);
        buttonA2 = (Button) findViewById(R.id.buttonA2);
        buttonB2 = (Button) findViewById(R.id.buttonB2);
        buttonC2 = (Button) findViewById(R.id.buttonC2);
        //buttonD = (Button) findViewById(R.id.buttonD);

        //Data from main activity
        Bundle dificultyData = getIntent().getExtras();
        int qidValue = dificultyData.getInt("qid");
        if(qidValue == 12)
        {
            qid = 12;
        }


        DBHandler db2 = new DBHandler(this);  // Database class
        quesList2 = db2.getAllQuestions();  // get list of questions
        currentQ2 = quesList2.get(qid); // the current question

        // method which will set the things up for our game
        setQuestionView();
    }

    //Button listeners
    public void onClickButtonA(View view) {
        // passing the button text to other method
        // to check whether the answer is correct or not
        // same for all three buttons
        getAnswer(buttonA2.getText().toString());
    }

    public void onClickButtonB(View view) {
        // passing the button text to other method
        // to check whether the answer is correct or not
        // same for all three buttons
        getAnswer(buttonB2.getText().toString());
    }

    public void onClickButtonC(View view) {
        // passing the button text to other method
        // to check whether the answer is correct or not
        // same for all three buttons
        getAnswer(buttonC2.getText().toString());
    }



    public void getAnswer(String AnswerString) {
        if (currentQ2.getANSWER().equals(AnswerString)) {

            // if conditions matches increase the int (score) by 1
            // and set the text of the score view
            score++;
            if (score == 6) {
                //If Score is 3, launch level 2
                Intent i = new Intent(this, Level3_Activity.class);
                i.putExtra("qid2", qid);
                startActivity(i);
            }
            scoreLabel2.setText("Score : " + score);
        }
        else  {

            // if unlucky start activity and finish the game
            //display in long period of time
            Toast.makeText(getApplicationContext(), "Incorect, answer is " + currentQ2.getANSWER(), Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }

        if (qid < 20) {

            // if questions are not over then do this
            currentQ2 = quesList2.get(qid);
            setQuestionView();
        }



    }

    private void setQuestionView() {
        // the method which will put all things together
        question2.setText(currentQ2.getQUESTION());
        buttonA2.setText(currentQ2.getOPTA());
        buttonB2.setText(currentQ2.getOPTB());
        buttonC2.setText(currentQ2.getOPTC());

        qid++;

    }

}

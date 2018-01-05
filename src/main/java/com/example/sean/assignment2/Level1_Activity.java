package com.example.sean.assignment2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Random;

public class Level1_Activity extends AppCompatActivity {
    LinearLayout linearLayout;
    private TextView levelLabel;
    private TextView scoreLabel;
    private TextView question;
    private Button buttonA;
    private Button buttonB;
    private Button buttonC;

    private List<Question> quesList;
    private int score = 0;
    private int qid = 0;
    private Question currentQ;

    //Progress Bar
    private ProgressBar mProgress;
    private int mProgressStatus = 0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_level1_);


        //Inflate Objects
        linearLayout = (LinearLayout) findViewById(R.id.LinearLayoutLevel1);
        levelLabel = (TextView) findViewById(R.id.levelLabel);
        scoreLabel = (TextView) findViewById(R.id.scoreLabel);
        question = (TextView) findViewById(R.id.txtQuestion);
        buttonA = (Button) findViewById(R.id.buttonA);
        buttonB = (Button) findViewById(R.id.buttonB);
        buttonC = (Button) findViewById(R.id.buttonC);
        //buttonD = (Button) findViewById(R.id.buttonD);

        //Data from main activity
        Bundle dificultyData = getIntent().getExtras();
        String difficulty = dificultyData.getString("Difficulty");
        if(difficulty.equals("Hard"))
        {
            qid = 9;
        }



        DBHandler db = new DBHandler(this);  // Database class
        //db.clearDatabase(quest);
        quesList = db.getAllQuestions();  // get list of questions
        currentQ = quesList.get(qid); // the current question

        // method which will set the things up for our game
        setQuestionView();
    }

        //Button listeners
        public void onClickButtonA(View view) {
        // passing the button text to other method
        // to check whether the answer is correct or not
        // same for all three buttons
        getAnswer(buttonA.getText().toString());
        }

        public void onClickButtonB(View view) {
            // passing the button text to other method
            // to check whether the answer is correct or not
            // same for all three buttons
            getAnswer(buttonB.getText().toString());
        }

        public void onClickButtonC(View view) {
            // passing the button text to other method
            // to check whether the answer is correct or not
            // same for all three buttons
            getAnswer(buttonC.getText().toString());
        }


    public void getAnswer(String AnswerString) {
        if (currentQ.getANSWER().equals(AnswerString)) {
            // if answer is correct, increase the score by 1
            // and set the text of the score view
            score++;
            if (score == 3) {
                //If Score is 3, launch level 2
                Intent i = new Intent(this, Level2_Activity.class);
                i.putExtra("qid", qid);
                startActivity(i);
            }
            scoreLabel.setText("Score : " + score);
        }
        else  {

            // if unlucky start activity and finish the game
            //display in long period of time
            Toast.makeText(getApplicationContext(), "Incorect, answer is " + currentQ.getANSWER(), Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }

        if (qid < 20) {

            // if questions are not over then do this
            currentQ = quesList.get(qid);
            setQuestionView();
        }

    }

        private void setQuestionView() {
            // the method which will put all things together
            question.setText(currentQ.getQUESTION());
            buttonA.setText(currentQ.getOPTA());
            buttonB.setText(currentQ.getOPTB());
            buttonC.setText(currentQ.getOPTC());

            qid++;
        }
}
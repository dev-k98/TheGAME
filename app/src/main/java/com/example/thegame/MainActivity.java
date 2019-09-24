package com.example.thegame;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {


    // creating views
    TextView textView;
    TextView score;
    TextView scoreBoard;
    TextView timer;
    GridLayout grid;
    TextView start;

    //create an array or aarayList to store the values to be shown on button
    ArrayList<Integer> options=new ArrayList<Integer>();
    Random ran=new Random();

    //parameters for calculating the answer and false answers
    int Number1;
    int Number2;
    int incorrectAnswer;
    int location;
    int count=0;
    int questionsAttempted=0;


    public void generate(){

        //setting random numbers to generate questions
        Number1=ran.nextInt(49);
        Number2=ran.nextInt(49);


        textView.setText(Integer.toString(Number1)+"+"+Integer.toString(Number2));


        //getting all the button view
        Button button1=(Button) findViewById(R.id.button1);
        Button button2=(Button) findViewById(R.id.button2);
        Button button3=(Button) findViewById(R.id.button3);
        Button button4=(Button) findViewById(R.id.button4);

        //setting random button the correct answer
        location=ran.nextInt(4);

        //clearing arraylist after every button click
        options.clear();

        //appending buttons the answers
        for(int i=0;i<4;i++){
            if (i==location){
                options.add(Number1+Number2);
            }else {

                incorrectAnswer=ran.nextInt(99);
                while (incorrectAnswer==(Number1+Number2)){
                    incorrectAnswer=ran.nextInt(99);
                }
                options.add(incorrectAnswer);
            }


        }
        button1.setText(Integer.toString(options.get(0)));
        button2.setText(Integer.toString(options.get(1)));
        button3.setText(Integer.toString(options.get(2)));
        button4.setText(Integer.toString(options.get(3)));

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //score text view
        scoreBoard=(TextView) findViewById(R.id.scoreBoard);
        timer=(TextView)findViewById(R.id.timer);
        //generating text field for the question
        textView=(TextView) findViewById(R.id.question);
        grid=(GridLayout) findViewById(R.id.grid);
        start=(TextView)findViewById(R.id.start);
        score=(TextView)findViewById(R.id.score);
        generate();


    }
    //home screen method
    public void Start(View view) {

        textView.setVisibility(View.VISIBLE);
        score.setVisibility(View.VISIBLE);
        scoreBoard.setVisibility(View.VISIBLE);
        timer.setVisibility(View.VISIBLE);
        grid.setVisibility(View.VISIBLE);
        start.setVisibility(View.GONE);



        CountDownTimer time=new CountDownTimer(30100,1000) {
            @Override
            public void onTick(long l) {
                timer.setText((Integer.toString((int)l/1000)));
                if ((l/1000)<10){
                    timer.setText("0"+Integer.toString((int)l/1000));
                }
            }

            @Override
            public void onFinish() {
                textView.setVisibility(View.GONE);
                score.setVisibility(View.GONE);
                scoreBoard.setVisibility(View.GONE);
                timer.setVisibility(View.GONE);
                grid.setVisibility(View.GONE);
                start.setVisibility(View.VISIBLE);
                start.setText(Integer.toString(count)+"/"+Integer.toString(questionsAttempted));
                count=0;
                questionsAttempted=0;
                scoreBoard.setText("00");
                MediaPlayer mediaPlayer=MediaPlayer.create(MainActivity.this,R.raw.timesup);
                mediaPlayer.start();
            }
        }.start();


    }

    public void nextQuestion(View view) {
        if (view.getTag().toString().equals(Integer.toString(location))){
            Toast.makeText(this, "correct", Toast.LENGTH_SHORT).show();
            generate();
            count++;
            if (count<10)
            scoreBoard.setText("0"+Integer.toString(count));
            else scoreBoard.setText(Integer.toString(count));

        }else{
            Toast.makeText(this, "incorrect", Toast.LENGTH_SHORT).show();
            generate();
        }
        questionsAttempted++;
    }
}

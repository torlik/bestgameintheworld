package ck.bestgameintheworld;

import android.content.DialogInterface;
import android.database.Cursor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class QuizActivity extends AppCompatActivity {

    DatabaseHandler handler;
    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Random rnd;
    int correctAnswer;
    int currentQuestion;
    int penalty;
    int elapsedTime;
    long pauseInterval;
    ArrayList<Integer> randomized;
    Chronometer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        rnd = new Random();
        currentQuestion = 0;
        penalty = 0;
        elapsedTime = -1;
        pauseInterval = 0;
        timer = (Chronometer) findViewById(R.id.timer);
        button1 = (Button) findViewById(R.id.answer1);
        button2 = (Button) findViewById(R.id.answer2);
        button3 = (Button) findViewById(R.id.answer3);
        button4 = (Button) findViewById(R.id.answer4);
        button1.setOnClickListener(clickHandler);
        button2.setOnClickListener(clickHandler);
        button3.setOnClickListener(clickHandler);
        button4.setOnClickListener(clickHandler);
        handler = new DatabaseHandler(this);

        randomized = randomizeQuestions(handler.getSize());

        Cursor cursor = handler.getQuestionData(randomized.get(currentQuestion));
        if (cursor != null && cursor.getCount() > 0) {
            fillTexts(cursor);
        }
        timer.setBase(0);
        timer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        pauseInterval = SystemClock.elapsedRealtime();
        timer.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        long timeBetween = (SystemClock.elapsedRealtime() - pauseInterval);
        timer.setBase(timer.getBase() + timeBetween);
        timer.start();
    }

    View.OnClickListener clickHandler = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.answer1 && correctAnswer == 1) {
                currentQuestion++;
                if (currentQuestion < randomized.size()) {
                    Cursor cursor = handler.getQuestionData(randomized.get(currentQuestion));
                    if (cursor != null && cursor.getCount() > 0) {
                        fillTexts(cursor);
                    }
                }
            } else if (view.getId() == R.id.answer2 && correctAnswer == 2) {
                currentQuestion++;
                if (currentQuestion < randomized.size()) {
                    Cursor cursor = handler.getQuestionData(randomized.get(currentQuestion));
                    if (cursor != null && cursor.getCount() > 0) {
                        fillTexts(cursor);
                    }
                }
            } else if (view.getId() == R.id.answer3 && correctAnswer == 3) {
                currentQuestion++;
                if (currentQuestion < randomized.size()) {
                    Cursor cursor = handler.getQuestionData(randomized.get(currentQuestion));
                    if (cursor != null && cursor.getCount() > 0) {
                        fillTexts(cursor);
                    }
                }
            } else if (view.getId() == R.id.answer4 && correctAnswer == 4) {
                currentQuestion++;
                if (currentQuestion < randomized.size()) {
                    Cursor cursor = handler.getQuestionData(randomized.get(currentQuestion));
                    if (cursor != null && cursor.getCount() > 0) {
                        fillTexts(cursor);
                    }
                }
            } else {
                Toast.makeText(getBaseContext(), "Helytelen válasz!", Toast.LENGTH_SHORT).show();
                penalty += 10000;
            }
            if (currentQuestion >= randomized.size()) {
                timer.stop();
                if (elapsedTime == -1) {
                    elapsedTime = (int) (SystemClock.elapsedRealtime() - timer.getBase());
                }
                getNameForResults();
            }
        }
    };

    private void fillTexts(Cursor cursor) {
        cursor.moveToFirst();
        TextView textView = (TextView) findViewById(R.id.question);
        textView.setText(cursor.getString(2));
        button1.setText(cursor.getString(3));
        button2.setText(cursor.getString(4));
        button3.setText(cursor.getString(5));
        button4.setText(cursor.getString(6));
        correctAnswer = cursor.getInt(7);
    }

    private ArrayList<Integer> randomizeQuestions(int size) {
        ArrayList<Integer> randomized = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            randomized.add(i);
        }
        Collections.shuffle(randomized);
        return randomized;
    }

    private void getNameForResults() {
        LayoutInflater layoutInflater = LayoutInflater.from(QuizActivity.this);
        View promptView = layoutInflater.inflate(R.layout.result_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(QuizActivity.this);
        alertDialogBuilder.setView(promptView);

        final AppCompatEditText editText = (AppCompatEditText) promptView.findViewById(R.id.edittext);
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String name = editText.getText().toString();
                        if (name.length() > 0) {
                            int score = (int)(((double)randomized.size() / (double)(elapsedTime + penalty)) * 1000000);
                            handler.insertResult(name, score);
                            finish();
                        } else {
                            Toast.makeText(getBaseContext(), "Add meg a neved!", Toast.LENGTH_SHORT).show();
                            getNameForResults();
                        }
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                finish();
                            }
                        });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }
}

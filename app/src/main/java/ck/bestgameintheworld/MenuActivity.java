package ck.bestgameintheworld;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {

    DatabaseHandler handler;
    Button start;
    Button results;
    Button newQuestion;
    Button resetQuestions;
    Button resetResults;
    Button quit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        handler = new DatabaseHandler(this);
        start = (Button) findViewById(R.id.quizstart);
        results = (Button) findViewById(R.id.results);
        quit = (Button) findViewById(R.id.quit);
        newQuestion = (Button) findViewById(R.id.newquestion);
        resetQuestions = (Button) findViewById(R.id.resetQuestions);
        resetResults = (Button) findViewById(R.id.resetResults);
        quit.setOnClickListener(quitListener);
        start.setOnClickListener(startListener);
        results.setOnClickListener(resultsListener);
        newQuestion.setOnClickListener(newQuestionListener);
        resetQuestions.setOnClickListener(resetQuestionsListener);
        resetResults.setOnClickListener(resetResultsListener);
    }

    View.OnClickListener quitListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };

    View.OnClickListener startListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MenuActivity.this, QuizActivity.class);
            MenuActivity.this.startActivity(intent);
        }
    };

    View.OnClickListener resultsListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MenuActivity.this, ResultsActivity.class);
            MenuActivity.this.startActivity(intent);
        }
    };

    View.OnClickListener newQuestionListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MenuActivity.this, NewQuestion_Activity.class);
            MenuActivity.this.startActivity(intent);
        }
    };

    View.OnClickListener resetQuestionsListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            LayoutInflater layoutInflater = LayoutInflater.from(MenuActivity.this);
            View promptView = layoutInflater.inflate(R.layout.delete_confirm, null);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MenuActivity.this);
            alertDialogBuilder.setView(promptView);
            alertDialogBuilder.setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            handler.resetQuestions();
                            Toast.makeText(getBaseContext(), "Visszaállítva", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
            AlertDialog alert = alertDialogBuilder.create();
            alert.show();
        }
    };

    View.OnClickListener resetResultsListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            LayoutInflater layoutInflater = LayoutInflater.from(MenuActivity.this);
            View promptView = layoutInflater.inflate(R.layout.delete_confirm, null);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MenuActivity.this);
            alertDialogBuilder.setView(promptView);
            alertDialogBuilder.setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            handler.resetResults();
                            Toast.makeText(getBaseContext(), "Visszaállítva", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
            AlertDialog alert = alertDialogBuilder.create();
            alert.show();
        }
    };
}

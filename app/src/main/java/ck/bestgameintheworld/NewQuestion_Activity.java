package ck.bestgameintheworld;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

public class NewQuestion_Activity extends AppCompatActivity {

    NumberPicker picker;
    EditText question, answer1, answer2, answer3, answer4;
    Button addButton;
    DatabaseHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_question_);
        handler = new DatabaseHandler(this);
        question = (EditText) findViewById(R.id.newquestion);
        answer1 = (EditText) findViewById(R.id.newanswer1);
        answer2 = (EditText) findViewById(R.id.newanswer2);
        answer3 = (EditText) findViewById(R.id.newanswer3);
        answer4 = (EditText) findViewById(R.id.newanswer4);
        picker = (NumberPicker) findViewById(R.id.picker);
        picker.setMinValue(1);
        picker.setMaxValue(4);
        addButton = (Button) findViewById(R.id.add);
        addButton.setOnClickListener(addListener);
    }

    View.OnClickListener addListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String q = question.getText().toString();
            String a1 = answer1.getText().toString();
            String a2 = answer2.getText().toString();
            String a3 = answer3.getText().toString();
            String a4 = answer4.getText().toString();
            int c = picker.getValue();
            if (inputIsCorrect(q, a1, a2, a3, a4)) {
                if(handler.insertQuestion(q, a1, a2, a3, a4, c)){
                    Toast.makeText(getBaseContext(), "Sikeres hozzáadás!", Toast.LENGTH_SHORT).show();
                    question.setText("");
                    answer1.setText("");
                    answer2.setText("");
                    answer3.setText("");
                    answer4.setText("");
                }
            }
            else
            {
                Toast.makeText(getBaseContext(), "Helytelen adatok!", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private boolean inputIsCorrect(String question, String answer1, String answer2, String answer3, String answer4) {
        return question.length() > 0 && question.length() <= 200 &&
                answer1.length() > 0 && answer1.length() <= 200 &&
                answer2.length() > 0 && answer2.length() <= 200 &&
                answer3.length() > 0 && answer3.length() <= 200 &&
                answer4.length() > 0 && answer4.length() <= 200;
    }
}

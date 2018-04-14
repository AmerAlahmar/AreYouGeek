package com.testspace.amer.areyougeek;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class QuizActivity extends AppCompatActivity {
    LinearLayout questionsLinearLayout;//The layout that will contain the questions **DYNAMICALLY**
    Button submitButton; //The Button that will accept user answers and display results
    ArrayList<RadioGroup> optionsRadioGroups = new ArrayList<>();//ArrayList of RadioGroups That will contain options
    //Crete ArrayList of class MultipleChoiceQuestion to enter as much questions as desired.
    ArrayList<MultipleChoiceQuestion> multipleChoiceQuestions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        //Initialize and connect Layout and Button
        questionsLinearLayout = findViewById(R.id.questionsLinearLayout);
        submitButton = findViewById(R.id.submitButton);
        //Declare and initialize ALL Questions
        multipleChoiceQuestions.add(new MultipleChoiceQuestion("1) What do we call the small image icons used to express emotions or ideas in digital communication ?", new String[]{"Emotions", "Emojis", "Face Symbols"}, 1));
        multipleChoiceQuestions.add(new MultipleChoiceQuestion("2) What is company founded in 1976 by Steve Wozniak, Steve Jobs, and Ronald Wayne ?", new String[]{"Apple", "Google", "Microsoft"}, 0));
        multipleChoiceQuestions.add(new MultipleChoiceQuestion("3) In a photo editing program, what do the letters RGB stand for ?", new String[]{"Red Green Blue", "Relay Gravity Bottom", "Round Glass Border"}, 0));
        multipleChoiceQuestions.add(new MultipleChoiceQuestion("4) Nintendo is a consumer electronics and video game company founded in :", new String[]{"USA", "Germany", "Japan"}, 2));
        multipleChoiceQuestions.add(new MultipleChoiceQuestion("5) HTML and CSS are computer languages used to create :", new String[]{"Android Designs", "Websites", "Desktop Application"}, 1));
        multipleChoiceQuestions.add(new MultipleChoiceQuestion("6) 1 Megabyte is equal to :", new String[]{"1,000 Kilobytes", "0.1 Gigabyte", "1,048,576 bytes"}, 2));
        multipleChoiceQuestions.add(new MultipleChoiceQuestion("7) Created in 2009, what was the first decentralized cryptocurrency ?", new String[]{"Bitcoin Cash", "Bitcoin", "Ethereum"}, 1));
        multipleChoiceQuestions.add(new MultipleChoiceQuestion("8) Fonts that contain small decorative lines at the end of a stroke are known as :", new String[]{"Arial Fonts", "Serif Fonts", "Impact Fonts"}, 1));
        multipleChoiceQuestions.add(new MultipleChoiceQuestion("9) What year was Facebook founded ?", new String[]{"2004", "2005", "2006", "2007"}, 0));
        multipleChoiceQuestions.add(new MultipleChoiceQuestion("10) In what year was the iPhone first released ?", new String[]{"2004", "2005", "2006", "2007"}, 3));
        //After Initializing Questions Display them on screen
        displayQuestionsOnScreen();
        //change userAnswer in multipleChoiceQuestion object when user change Answer
        for (final RadioGroup radioGroup : optionsRadioGroups) {
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    RadioButton userAnswerRadioButton = findViewById(radioGroup.getCheckedRadioButtonId());
                    multipleChoiceQuestions.get(optionsRadioGroups.indexOf(radioGroup)).setUserAnswer(userAnswerRadioButton.getText().toString());
                }
            });
        }
        //when submit button clicked do:
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //If all questions answered create new Intent and pass the questions ArrayList to resultActivity
                if (isAllRadioGroupChecked(optionsRadioGroups)) {
                    Intent intent = new Intent(QuizActivity.this, SubmitActivity.class);
                    intent.putExtra("QUESTIONS", multipleChoiceQuestions);
                    startActivity(intent);
                    finish(); // finish so user can't return directly to this layout (Must go throw Welcome Layout)
                } else {
                    //If not all questions answered, display Error toast
                    Toast.makeText(QuizActivity.this, "All questions must be answered!", Toast.LENGTH_LONG).show();
                }
            }

        });
    }

    //Checks if user Answered all the questions by looking for the checkedRadioButtonId
    private boolean isAllRadioGroupChecked(ArrayList<RadioGroup> radioGroups) {
        for (RadioGroup radioGroup : radioGroups) {
            if (radioGroup.getCheckedRadioButtonId() == -1) {
                return false;
            }
        }
        return true;
    }

    //Display and assign styles for all the questions in multipleChoiceQuestions ArrayList along with the options.
    private void displayQuestionsOnScreen() {
        for (MultipleChoiceQuestion multipleChoiceQuestion : multipleChoiceQuestions) {
            TextView questionTextView = new TextView(new ContextThemeWrapper(this, R.style.question));
            optionsRadioGroups.add(new RadioGroup(new ContextThemeWrapper(this, R.style.options)));//for bottom margin between the questions
            ArrayList<RadioButton> optionsRadioButtons = new ArrayList<>();
            for (String option : multipleChoiceQuestion.getOptions()) {
                RadioButton radioButton = new RadioButton(new ContextThemeWrapper(this, R.style.option));
                radioButton.setText(option);
                optionsRadioButtons.add(radioButton);
            }
            for (RadioButton radioButton : optionsRadioButtons) {
                optionsRadioGroups.get(multipleChoiceQuestions.indexOf(multipleChoiceQuestion)).addView(radioButton);
            }
            questionTextView.setText(multipleChoiceQuestion.getQuestion());
            questionsLinearLayout.addView(questionTextView);
            questionsLinearLayout.addView(optionsRadioGroups.get(multipleChoiceQuestions.indexOf(multipleChoiceQuestion)));
        }
    }
}
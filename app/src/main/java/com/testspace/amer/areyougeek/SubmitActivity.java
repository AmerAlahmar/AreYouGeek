package com.testspace.amer.areyougeek;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class SubmitActivity extends AppCompatActivity {
    LinearLayout resultsLinearLayout;//The layout that will contain the Results **DYNAMICALLY**
    TextView userScoreTextView;//to display user scores
    TextView totalScoreTextView;//to display the total scores (depends on the number of questions)
    Button resetButton; // reset button to return to WelcomeActivity
    //Crete ArrayList of class MultipleChoiceQuestion to assign the Objects passed with the Intent to it.
    ArrayList<MultipleChoiceQuestion> multipleChoiceQuestions = new ArrayList<>();
    int userScore = 0;//to keep track of user Scores to display it later.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);
        //Initialize and connect Layout, TextViews and Button
        resultsLinearLayout = findViewById(R.id.resultsLinearLayout);
        userScoreTextView = findViewById(R.id.userScoreTextView);
        totalScoreTextView = findViewById(R.id.totalScoreTextView);
        resetButton = findViewById(R.id.restartButton);
        //Get All questions objects from intent and assign it to multipleChoiceQuestions ArrayList.
        multipleChoiceQuestions = (ArrayList<MultipleChoiceQuestion>) getIntent().getSerializableExtra("QUESTIONS");
        //calculate the total scores.
        totalScoreTextView.setText(String.valueOf(multipleChoiceQuestions.size()));
        //go throw all questions and display them with the options
        for (MultipleChoiceQuestion multipleChoiceQuestion : multipleChoiceQuestions) {
            //setText of textView to the question and add it to the Layout
            TextView questionTextView = new TextView(new ContextThemeWrapper(this, R.style.question));
            questionTextView.setText(multipleChoiceQuestion.getQuestion());
            resultsLinearLayout.addView(questionTextView);
            //go throw all the options in the current question, evaluate user answers, display them and track user scores
            for (String option : multipleChoiceQuestion.getOptions()) {
                //create and setText of textView to the option
                TextView optionTextView = new TextView(new ContextThemeWrapper(this, R.style.option));
                optionTextView.setText(option);
                //if this option is the correct answer give GREEN textColor and increase score.
                if (option.equals(multipleChoiceQuestion.getCorrectOption())) {
                    optionTextView.setTextColor(getResources().getColor(R.color.colorCorrectAnswer));
                    userScore++;
                    //if it's not correct and user choose it decrease the score and set text color to RED
                } else if (option.equals(multipleChoiceQuestion.getUserAnswer())) {
                    optionTextView.setTextColor(getResources().getColor(R.color.colorWrongAnswer));
                    userScore--;
                }
                //add option textView to Layout.
                resultsLinearLayout.addView(optionTextView);
            }
            //create Empty Text View, style it as divider to keep margin between questions and add it to Layout.
            TextView resultsDividerTextView = new TextView(new ContextThemeWrapper(this, R.style.resultsDivider));
            resultsLinearLayout.addView(resultsDividerTextView);
        }
        //display userScore.
        userScoreTextView.setText(String.valueOf(userScore));
        //when reset button clicked do:
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create new Intent to WelcomeActivity and start it.
                Intent intent = new Intent(SubmitActivity.this, WelcomeActivity.class);
                startActivity(intent);
                finish();// finish so user can't go back here unless by answering the questions again.
            }
        });
    }
}
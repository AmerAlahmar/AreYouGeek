package com.testspace.amer.areyougeek;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
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
    ArrayList<FreeWriteQuestion> freeWriteQuestions = new ArrayList<>();
    ArrayList<CheckBoxesQuestion> checkBoxesQuestions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        //Initialize and connect Layout and Button
        questionsLinearLayout = findViewById(R.id.questionsLinearLayout);
        submitButton = findViewById(R.id.submitButton);
        //Declare and initialize ALL Questions
        multipleChoiceQuestions.add(new MultipleChoiceQuestion("What do we call the small image icons used to express emotions or ideas in digital communication ?", new String[]{"Emotions", "Emojis", "Face Symbols"}, 1));
        multipleChoiceQuestions.add(new MultipleChoiceQuestion("What is company founded in 1976 by Steve Wozniak, Steve Jobs, and Ronald Wayne ?", new String[]{"Apple", "Google", "Microsoft"}, 0));
        multipleChoiceQuestions.add(new MultipleChoiceQuestion("Nintendo is a consumer electronics and video game company founded in :", new String[]{"USA", "Germany", "Japan"}, 2));
        multipleChoiceQuestions.add(new MultipleChoiceQuestion("HTML and CSS are computer languages used to create :", new String[]{"Android Designs", "Websites", "Desktop Application"}, 1));
        multipleChoiceQuestions.add(new MultipleChoiceQuestion("1 Megabyte is equal to :", new String[]{"1,000 Kilobytes", "0.1 Gigabyte", "1,048,576 bytes"}, 2));
        multipleChoiceQuestions.add(new MultipleChoiceQuestion("Created in 2009, what was the first decentralized cryptocurrency ?", new String[]{"Bitcoin Cash", "Bitcoin", "Ethereum"}, 1));
        multipleChoiceQuestions.add(new MultipleChoiceQuestion("Fonts that contain small decorative lines at the end of a stroke are known as :", new String[]{"Arial Fonts", "Serif Fonts", "Impact Fonts"}, 1));
        multipleChoiceQuestions.add(new MultipleChoiceQuestion("What year was Facebook founded ?", new String[]{"2004", "2005", "2006", "2007"}, 0));
        checkBoxesQuestions.add(new CheckBoxesQuestion("In a photo editing program, what do the letters RGB stand for ? (Chose Only 3)", new String[]{"Relay", "Red", "Green", "Gravity", "Blue", "Bottom"}, new Integer[]{1, 2, 4}));
        freeWriteQuestions.add(new FreeWriteQuestion("In what year was the iPhone first released ?", "2007"));
        //After Initializing Questions Display them on screen
        displayQuestionsOnScreen();
        //Update user inputs
        updateUserRadioButtonsClicking();
        updateUserCheckBoxesChecking();
        updateUserFreeWriteWriting();
        //when submit button clicked do:
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //If all questions answered create new Intent and pass the questions ArrayLists to resultActivity
                if (isAllQuestionsAnswered()) {
                    Intent intent = new Intent(QuizActivity.this, SubmitActivity.class);
                    intent.putExtra("MULTIPLECHOICEQUESTION", multipleChoiceQuestions);
                    intent.putExtra("CHECKBOXESQUESTION", checkBoxesQuestions);
                    intent.putExtra("FREEWRITEQUESTION", freeWriteQuestions);
                    startActivity(intent);
                    finish(); // finish so user can't return directly to this layout (Must go throw Welcome Layout)
                } else {
                    //If not all questions answered, display Error toast
                    Toast.makeText(QuizActivity.this, "All questions must be answered!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //Display and assign styles for all kinds of questions along with the options if any.
    private void displayQuestionsOnScreen() {
        //for multiple choice questions:
        for (MultipleChoiceQuestion multipleChoiceQuestion : multipleChoiceQuestions) {
            TextView questionTextView = new TextView(new ContextThemeWrapper(this, R.style.question));
            questionTextView.setId(View.generateViewId());
            multipleChoiceQuestion.setQuestionTextViewId(questionTextView.getId());
            optionsRadioGroups.add(new RadioGroup(this));
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
            TextView questionDividerTextView = new TextView(new ContextThemeWrapper(this, R.style.divider));
            questionsLinearLayout.addView(questionDividerTextView);
        }
        //for check boxes questions:
        for (CheckBoxesQuestion checkBoxesQuestion : checkBoxesQuestions) {
            TextView questionTextView = new TextView(new ContextThemeWrapper(this, R.style.question));
            questionTextView.setText(checkBoxesQuestion.getQuestion());
            questionTextView.setId(View.generateViewId());
            checkBoxesQuestion.setQuestionTextViewId(questionTextView.getId());
            questionsLinearLayout.addView(questionTextView);
            for (String option : checkBoxesQuestion.getOptions()) {
                CheckBox optionCheckBox = new CheckBox(new ContextThemeWrapper(this, R.style.option));
                optionCheckBox.setText(option);
                optionCheckBox.setId(View.generateViewId());
                checkBoxesQuestion.setOptionCheckBoxeId(optionCheckBox.getId());
                questionsLinearLayout.addView(optionCheckBox);
            }
            TextView questionDividerTextView = new TextView(new ContextThemeWrapper(this, R.style.divider));
            questionsLinearLayout.addView(questionDividerTextView);
        }
        //for free write questions:
        for (FreeWriteQuestion freeWriteQuestion : freeWriteQuestions) {
            TextView questionTextView = new TextView(new ContextThemeWrapper(this, R.style.question));
            questionTextView.setText(freeWriteQuestion.getQuestion());
            questionTextView.setId(View.generateViewId());
            freeWriteQuestion.setQuestionTextViewId(questionTextView.getId());
            questionsLinearLayout.addView(questionTextView);
            EditText freeWriteQuestionEditText = new EditText(this);
            questionsLinearLayout.addView(freeWriteQuestionEditText);
            freeWriteQuestionEditText.setId(View.generateViewId());
            freeWriteQuestion.setAnswerEditTextId(freeWriteQuestionEditText.getId());
            TextView questionDividerTextView = new TextView(new ContextThemeWrapper(this, R.style.divider));
            questionsLinearLayout.addView(questionDividerTextView);
        }
    }

    //change userAnswer in checkBoxesQuestions objects when user change Answers
    private void updateUserCheckBoxesChecking() {
        for (final CheckBoxesQuestion checkBoxesQuestion : checkBoxesQuestions) {
            for (int checkBoxId : checkBoxesQuestion.getOptionsCheckBoxesIds()) {
                final CheckBox optionCheckBox = findViewById(checkBoxId);
                optionCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (optionCheckBox.isChecked()) {
                            if (checkBoxesQuestion.getUserAnswers().size() >= checkBoxesQuestion.getIndexesOfCorrectAnswers().size()) {
                                TextView questionTextView = findViewById(checkBoxesQuestion.getQuestionTextViewId());
                                String errorMessage = "Chose ".concat(String.valueOf(checkBoxesQuestion.getCorrectAnswers().size())).concat(" options only (uncheck one to be able to check another)");
                                questionTextView.setError(errorMessage);
                                Toast.makeText(QuizActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                                optionCheckBox.setChecked(false);
                            } else {
                                checkBoxesQuestion.setUserAnswer(optionCheckBox.getText().toString());
                            }
                        } else {
                            TextView questionTextView = findViewById(checkBoxesQuestion.getQuestionTextViewId());
                            questionTextView.setError(null);
                            checkBoxesQuestion.removeUserAnswer(optionCheckBox.getText().toString());
                        }
                    }
                });
            }
        }
    }

    //change userAnswer in multipleChoiceQuestion objects when user change Answers
    private void updateUserRadioButtonsClicking() {
        for (final RadioGroup radioGroup : optionsRadioGroups) {
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    RadioButton userAnswerRadioButton = findViewById(radioGroup.getCheckedRadioButtonId());
                    multipleChoiceQuestions.get(optionsRadioGroups.indexOf(radioGroup)).setUserAnswer(userAnswerRadioButton.getText().toString());
                }
            });
        }
    }

    //change userAnswer in FreeWriteQuestion objects when user change Answers
    private void updateUserFreeWriteWriting() {
        for (final FreeWriteQuestion freeWriteQuestion : freeWriteQuestions) {
            final EditText freeWriteAnswerEditText = findViewById(freeWriteQuestion.getAnswerEditTextId());
            freeWriteAnswerEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        if (freeWriteAnswerEditText.getText().toString().isEmpty()) {
                            TextView questionTextView = findViewById(freeWriteQuestion.getQuestionTextViewId());
                            questionTextView.setError("Must be answered (If you don't know, type I don't know)");
                            freeWriteQuestion.setUserAnswer("");
                        } else {
                            freeWriteQuestion.setUserAnswer(freeWriteAnswerEditText.getText().toString());
                        }
                    } else {
                        freeWriteAnswerEditText.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                if (freeWriteAnswerEditText.getText().toString().isEmpty()) {
                                    TextView questionTextView = findViewById(freeWriteQuestion.getQuestionTextViewId());
                                    freeWriteQuestion.setUserAnswer("");
                                    questionTextView.setError("Must be answered (If you don't know, type I don't know)");
                                } else {
                                    TextView questionTextView = findViewById(freeWriteQuestion.getQuestionTextViewId());
                                    questionTextView.setError(null);
                                    freeWriteQuestion.setUserAnswer(freeWriteAnswerEditText.getText().toString());
                                }
                            }
                        });
                    }
                }
            });
        }
    }

    //callChecking Functions and if all returns true it returns true
    private boolean isAllQuestionsAnswered() {
        boolean checkMultipleChoiceQuestions = isAllRadioGroupChecked(optionsRadioGroups);
        boolean checkCheckBoxesQuestions = isAllCheckBoxesQuestionsAnswered();
        boolean checkFreeWriteQuestions = isAllFreeWriteQuestionsAnswered();
        return checkMultipleChoiceQuestions && checkCheckBoxesQuestions && checkFreeWriteQuestions;
    }

    //Checks if user Answered all RadioButtons questions *AND* set Error and remove it if answered
    private boolean isAllRadioGroupChecked(ArrayList<RadioGroup> radioGroups) {
        boolean isAllOk = true;
        for (RadioGroup radioGroup : radioGroups) {
            if (radioGroup.getCheckedRadioButtonId() == -1) {
                TextView questionTextView;
                questionTextView = findViewById(multipleChoiceQuestions.get(radioGroups.indexOf(radioGroup)).getQuestionTextViewId());
                questionTextView.setError("All questions must be Answered");
                isAllOk = false;
            } else {
                TextView questionTextView;
                questionTextView = findViewById(multipleChoiceQuestions.get(radioGroups.indexOf(radioGroup)).getQuestionTextViewId());
                questionTextView.setError(null);
            }
        }
        return isAllOk;
    }

    //Checks if user Answered all CheckBoxes questions
    private boolean isAllCheckBoxesQuestionsAnswered() {
        boolean isAllOk = true;
        for (CheckBoxesQuestion checkBoxesQuestion : checkBoxesQuestions) {
            if (checkBoxesQuestion.getUserAnswers().size() != checkBoxesQuestion.getIndexesOfCorrectAnswers().size()) {
                TextView questionTextView;
                questionTextView = findViewById(checkBoxesQuestion.getQuestionTextViewId());
                questionTextView.setError("All questions must be Answered");
                isAllOk = false;
            } else {
                TextView questionTextView;
                questionTextView = findViewById(checkBoxesQuestion.getQuestionTextViewId());
                questionTextView.setError(null);
            }
        }
        return isAllOk;
    }

    //Checks if user Answered all freeWrite questions
    private boolean isAllFreeWriteQuestionsAnswered() {
        boolean isAllOk = true;
        for (FreeWriteQuestion freeWriteQuestion : freeWriteQuestions) {
            if (freeWriteQuestion.getUserAnswer().isEmpty()) {
                TextView questionTextView;
                questionTextView = findViewById(freeWriteQuestion.getQuestionTextViewId());
                questionTextView.setError("All questions must be Answered");
                isAllOk = false;
            } else {
                TextView questionTextView;
                questionTextView = findViewById(freeWriteQuestion.getQuestionTextViewId());
                questionTextView.setError(null);
            }
        }
        return isAllOk;
    }
}
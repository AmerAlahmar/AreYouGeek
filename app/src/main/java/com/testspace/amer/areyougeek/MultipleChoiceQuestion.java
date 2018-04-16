package com.testspace.amer.areyougeek;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class MultipleChoiceQuestion implements Serializable { // Serialized to pass it with ***Intent***
    private String question; //The Question text
    private int questionTextViewId;//The id of the question TextView
    private ArrayList<String> options = new ArrayList<>(); // The Answer Options
    private int indexOfCorrectAnswer; //The Index of the correct Answer ** IN options ArrayList**
    private String userAnswer; //The text of chosen option by the user

    //Construct question **WITHOUT userAnswer**
    MultipleChoiceQuestion(String question, String[] options, int indexOfCorrectAnswer) {
        this.question = question;
        this.indexOfCorrectAnswer = indexOfCorrectAnswer;
        Collections.addAll(this.options, options);
    }

    //returns the question text as String
    public String getQuestion() {
        return question;
    }

    public int getQuestionTextViewId() {
        return questionTextViewId;
    }

    public void setQuestionTextViewId(int questionTextViewId) {
        this.questionTextViewId = questionTextViewId;
    }

    //returns all options as ArrayList
    public ArrayList<String> getOptions() {
        return options;
    }

    //returns the indexOfCorrectAnswer as Integer
    private int getIndexOfCorrectAnswer() {
        return indexOfCorrectAnswer;
    }

    //returns the text of chosen option by the user
    public String getUserAnswer() {
        return userAnswer;
    }

    //takes userAnswer as String
    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    //takes index of the option in options ArrayList and returns it as String
    public String getOption(int optionIndex) {
        return options.get(optionIndex);
    }

    //returns the correct answer from options ArrayList as String
    public String getCorrectOption() {
        return options.get(getIndexOfCorrectAnswer());
    }
}
package com.testspace.amer.areyougeek;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class CheckBoxesQuestion implements Serializable {// Serialized to pass it with ***Intent***
    private String question;
    private int questionTextViewId;
    private ArrayList<String> options = new ArrayList<>();
    private ArrayList<Integer> indexesOfCorrectAnswers = new ArrayList<>();
    private ArrayList<Integer> optionsCheckBoxesIds = new ArrayList<>();
    private ArrayList<String> userAnswers = new ArrayList<>();

    CheckBoxesQuestion(String question, String[] options, Integer[] indexesOfCorrectAnswers) {
        this.question = question;
        Collections.addAll(this.options, options);
        Collections.addAll(this.indexesOfCorrectAnswers, indexesOfCorrectAnswers);
    }

    public String getQuestion() {
        return question;
    }

    public int getQuestionTextViewId() {
        return questionTextViewId;
    }

    public void setQuestionTextViewId(int questionTextViewId) {
        this.questionTextViewId = questionTextViewId;
    }

    public ArrayList<String> getOptions() {
        return options;
    }

    public ArrayList<Integer> getIndexesOfCorrectAnswers() {
        return indexesOfCorrectAnswers;
    }

    public ArrayList<String> getUserAnswers() {
        return userAnswers;
    }

    public ArrayList<String> getCorrectAnswers() {
        ArrayList<String> correctAnswers = new ArrayList<>();
        for (int indexOfCorrectAnswer : getIndexesOfCorrectAnswers()) {
            correctAnswers.add(options.get(indexOfCorrectAnswer));
        }
        return correctAnswers;
    }

    public ArrayList<Integer> getOptionsCheckBoxesIds() {
        return optionsCheckBoxesIds;
    }

    public void setOptionCheckBoxeId(int checkBoxId) {
        optionsCheckBoxesIds.add(checkBoxId);
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswers.add(userAnswer);
    }

    public void removeUserAnswer(String userAnswer) {
        if (userAnswers.contains(userAnswer))
            userAnswers.remove(userAnswer);
    }
}
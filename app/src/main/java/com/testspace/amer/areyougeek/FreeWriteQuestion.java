package com.testspace.amer.areyougeek;

import java.io.Serializable;

public class FreeWriteQuestion implements Serializable {// Serialized to pass it with ***Intent***
    private String question; //The Question text
    private int questionTextViewId;
    private int answerEditTextId;
    private String correctAnswer; //correct Answer
    private String userAnswer = ""; //The text of chosen option by the user

    FreeWriteQuestion(String question, String correctAnswer) {
        this.question = question;
        this.correctAnswer = correctAnswer;
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

    public int getAnswerEditTextId() {
        return answerEditTextId;
    }

    public void setAnswerEditTextId(int answerEditTextId) {
        this.answerEditTextId = answerEditTextId;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }
}

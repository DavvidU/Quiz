package com.example.quiz;

public class Question {
    private int questionId;
    private boolean trueAnswer;
    private int podpowiedzId;
    public Question(int questionId, boolean trueAnswer, int podpowiedzId)
    {
        this.questionId = questionId;
        this.trueAnswer = trueAnswer;
        this.podpowiedzId = podpowiedzId;
    }
    public boolean isTrueAnswer()
    {
        return trueAnswer;
    }
    public int getQuestionId()
    {
        return questionId;
    }
    public int getPodpowiedzId() { return podpowiedzId; }
}

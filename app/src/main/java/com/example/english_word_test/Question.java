package com.example.english_word_test;

import java.io.Serializable;

public class Question implements Serializable {
    private String questionText;
    private String optionA;
    private String optionB;
    private String correctAnswer;

    public Question(String questionText, String optionA, String optionB, String correctAnswer) {
        this.questionText = questionText;
        this.optionA = optionA;
        this.optionB = optionB;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String getOptionA() {
        return optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }
}

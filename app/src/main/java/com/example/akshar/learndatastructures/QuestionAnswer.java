package com.example.akshar.learndatastructures;

import com.squareup.moshi.Json;

public class QuestionAnswer {
    @Json(name = "question")
    String question;
    @Json(name = "Answer")
    String answer;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}

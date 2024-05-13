package com.example.quizz;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Question {
    private String question;
    private List<String> answers = new ArrayList<>();

    @Override
    public String toString() {
        return "Question{" +
            "question='" + question + '\'' +
            ", answers=" + answers +
            '}';
    }
}

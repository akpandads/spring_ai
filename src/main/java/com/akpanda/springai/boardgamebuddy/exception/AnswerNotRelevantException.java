package com.akpanda.springai.boardgamebuddy.exception;

import com.akpanda.springai.boardgamebuddy.records.Question;

public class AnswerNotRelevantException extends RuntimeException {
    String questionTitle;
    public AnswerNotRelevantException(Question question, String answer) {
        super("The answer '" + answer + "' is not relevant to the question '" + question + "'.");
        questionTitle = question.gameTitle();
    }
    public String getQuestionTitle() {return questionTitle;}
}

package com.akpanda.springai.boardgamebuddy.service;

import com.akpanda.springai.boardgamebuddy.records.Answer;
import com.akpanda.springai.boardgamebuddy.records.Question;

public interface BoardGameService {

    public Answer askQestion(Question question);
}

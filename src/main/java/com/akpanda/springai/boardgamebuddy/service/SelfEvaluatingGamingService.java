package com.akpanda.springai.boardgamebuddy.service;

import com.akpanda.springai.boardgamebuddy.records.Answer;
import com.akpanda.springai.boardgamebuddy.records.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

@Service
public class SelfEvaluatingGamingService implements BoardGameService{

    private static final Logger log = LoggerFactory.getLogger(SelfEvaluatingGamingService.class);

    private final SelfEvalatingGamingServiceProxy selfEvalatingGamingServiceProxy;


    public SelfEvaluatingGamingService(SelfEvalatingGamingServiceProxy selfEvalatingGamingServiceProxy) {
        this.selfEvalatingGamingServiceProxy = selfEvalatingGamingServiceProxy;
    }



    @Override
    public Answer askQestion(Question question) {
      return selfEvalatingGamingServiceProxy.getAnswer(question);
    }
}

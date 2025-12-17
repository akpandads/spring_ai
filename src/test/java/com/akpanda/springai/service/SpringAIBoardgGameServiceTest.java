package com.akpanda.springai.service;

import com.akpanda.springai.boardgamebuddy.records.Answer;
import com.akpanda.springai.boardgamebuddy.records.Question;
import com.akpanda.springai.boardgamebuddy.service.BoardGameService;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.evaluation.EvaluationRequest;
import org.springframework.ai.evaluation.EvaluationResponse;
import org.springframework.ai.evaluation.FactCheckingEvaluator;
import org.springframework.ai.evaluation.RelevancyEvaluator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SpringAIBoardgGameServiceTest {

    private static Logger log = LoggerFactory.getLogger(SpringAIBoardgGameServiceTest.class);
    @Autowired
    private BoardGameService boardGameService;

    @Autowired
    private ChatClient.Builder chatClientBuilder;

    private RelevancyEvaluator relevancyEvaluator;

    private FactCheckingEvaluator factCheckingEvaluator;

    @BeforeEach
    public void setUp() {
        relevancyEvaluator = new RelevancyEvaluator(chatClientBuilder);
        factCheckingEvaluator = new FactCheckingEvaluator(chatClientBuilder);
    }

    @Test
    public void evauateRelevancy() {
        String userText = "Boiling point of water and calorific value";
        Question question = new Question(userText);
        Answer answer = boardGameService.askQestion(question);
        log.info(answer.toString());
        EvaluationRequest evaluationRequest = new EvaluationRequest(userText, answer.string());

        EvaluationResponse evaluationResponse = relevancyEvaluator.evaluate(evaluationRequest);
        EvaluationResponse factCheckingResponse = factCheckingEvaluator.evaluate(evaluationRequest);
        log.info(evaluationResponse.toString());
        log.info(factCheckingResponse.toString());
        Assertions.assertThat( evaluationResponse.isPass()).withFailMessage("Answer not relevant",answer.string(),userText).isTrue();
        Assertions.assertThat( factCheckingResponse.isPass()).withFailMessage("Answer not relevant",answer.string(),userText).isTrue();
    }

}

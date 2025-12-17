package com.akpanda.springai.boardgamebuddy.service;

import com.akpanda.springai.boardgamebuddy.exception.AnswerNotRelevantException;
import com.akpanda.springai.boardgamebuddy.records.Answer;
import com.akpanda.springai.boardgamebuddy.records.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.evaluation.EvaluationRequest;
import org.springframework.ai.evaluation.FactCheckingEvaluator;
import org.springframework.ai.evaluation.RelevancyEvaluator;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
public class SelfEvalatingGamingServiceProxy {

    private Logger log = LoggerFactory.getLogger(this.getClass());
    private final OllamaChatModel chatModel;
    private final ChatClient chatClient;
    private final RelevancyEvaluator relevancyEvaluator;
    private final FactCheckingEvaluator factCheckingEvaluator;

    public SelfEvalatingGamingServiceProxy(OllamaChatModel chatModel, ChatClient.Builder builder) {
        this.chatModel = chatModel;
        this.chatClient = ChatClient.builder(chatModel).build();
        this.relevancyEvaluator= new RelevancyEvaluator(builder);
        this.factCheckingEvaluator = new FactCheckingEvaluator(builder);
    }

    @Retryable(retryFor = AnswerNotRelevantException.class, maxAttempts = 2)
    public Answer getAnswer(Question question) {
        log.info("inside self evaluating gaming");
        var answerText = chatClient.prompt().user(question.question()).call().content();
        evaluateRelevancy(question,answerText);
        return new Answer(question.gameTitle(),answerText);
    }

    private void evaluateRelevancy(Question question,String answerText) {
        var evaluationRequest = new EvaluationRequest(question.question(),answerText);
        var evaluationResult = relevancyEvaluator.evaluate(evaluationRequest);
        if(!evaluationResult.isPass()){
            throw new AnswerNotRelevantException(question, answerText);
        }
    }

    @Recover
    public Answer relevantAnswerRecovery(AnswerNotRelevantException e) {
        log.info("relevant answer recovery");
        return new Answer(e.getQuestionTitle(),"Cannot answer this");
    }

}

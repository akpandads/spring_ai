package com.akpanda.springai.boardgamebuddy.service;

import com.akpanda.springai.boardgamebuddy.records.Answer;
import com.akpanda.springai.boardgamebuddy.records.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.stereotype.Service;

@Service
public class SpringAIBoardGameService implements BoardGameService {

    private static final Logger log = LoggerFactory.getLogger(SpringAIBoardGameService.class);
    private final OllamaChatModel chatModel;
    private final ChatClient chatClient;

    public SpringAIBoardGameService(OllamaChatModel chatModel) {
        this.chatModel = chatModel;
        this.chatClient = ChatClient.builder(this.chatModel).build();
    }

    @Override
    public Answer askQestion(Question question) {
        log.info("In SpringAIBoardGameService.askQestion");
        var answerText = chatClient.prompt().user(question.question())
                .call().content();
        return new Answer(question.gameTitle(),answerText);
    }
}

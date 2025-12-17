package com.akpanda.springai.boardgamebuddy.controller;

import com.akpanda.springai.boardgamebuddy.records.Answer;
import com.akpanda.springai.boardgamebuddy.records.Question;
import com.akpanda.springai.boardgamebuddy.service.BoardGameService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AskController {


    private final BoardGameService boardGameService;

    public AskController(@Qualifier("selfEvaluatingGamingService") BoardGameService boardGameService) {
        this.boardGameService = boardGameService;
    }

    @PostMapping(path = "/ask", produces = "application/json")
    public Answer ask(@RequestBody @Valid Question question) {
        return boardGameService.askQestion(question);
    }

    @GetMapping(path = "/get", produces = "application/json")
    public String get() {
        return "hello";
    }

}

package com.akpanda.springai.boardgamebuddy.records;

import jakarta.validation.constraints.NotBlank;

public record Question(
        @NotBlank(message="Game title requried") String gameTitle,
        @NotBlank(message="Question is required") String question
) {
}

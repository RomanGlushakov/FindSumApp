package com.example.findsumappfinal.domain.useCase

import com.example.findsumappfinal.domain.entity.GameSettings
import com.example.findsumappfinal.domain.entity.Question
import com.example.findsumappfinal.domain.repository.GameRepository

class GenerateQuestionUseCase(private val repository: GameRepository) {



    operator fun invoke(maxSumValue: Int,
                        ): Question {
        return repository.generateQuestion(maxSumValue, COUNT_OF_OPTIONS)
    }

    private companion object {
        const val COUNT_OF_OPTIONS = 6
    }
}
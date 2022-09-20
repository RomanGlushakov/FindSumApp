package com.example.findsumappfinal.domain.repository

import com.example.findsumappfinal.domain.entity.GameSettings
import com.example.findsumappfinal.domain.entity.Level
import com.example.findsumappfinal.domain.entity.Question

interface GameRepository {

    fun generateQuestion(
        maxSumValue: Int,
    countOfOptions: Int
    ): Question

    fun getGameSettings(level: Level): GameSettings

}
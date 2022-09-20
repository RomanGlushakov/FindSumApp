package com.example.findsumappfinal.domain.useCase

import com.example.findsumappfinal.domain.entity.GameSettings
import com.example.findsumappfinal.domain.entity.Level
import com.example.findsumappfinal.domain.repository.GameRepository

class GetGameSettingsUseCase(private val repository: GameRepository) {

    operator fun invoke(level: Level): GameSettings {
        return repository.getGameSettings(level)
    }
}
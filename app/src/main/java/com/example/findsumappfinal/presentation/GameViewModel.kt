package com.example.findsumappfinal.presentation

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.findsumappfinal.R
import com.example.findsumappfinal.data.GameRepositoryImpl
import com.example.findsumappfinal.domain.entity.GameResult
import com.example.findsumappfinal.domain.entity.GameSettings
import com.example.findsumappfinal.domain.entity.Level
import com.example.findsumappfinal.domain.entity.Question
import com.example.findsumappfinal.domain.useCase.GenerateQuestionUseCase
import com.example.findsumappfinal.domain.useCase.GetGameSettingsUseCase

class GameViewModel(
    private val application: Application,
    private val level: Level
) : ViewModel() {


    private lateinit var gameSettings: GameSettings


    private var countOfRightAnswers = 0
    private var countOfQuestions = 0

    private val repository = GameRepositoryImpl
    private val generateQuestionUseCase = GenerateQuestionUseCase(repository)
    private val getGameSettingsUseCase = GetGameSettingsUseCase(repository)

    private val _percentOfRightAnswers = MutableLiveData<Int>()
    val percentOfRightAnswers: LiveData<Int>
        get() = _percentOfRightAnswers

    private val _progressAnswers = MutableLiveData<String>()
    val progressAnswers: LiveData<String>
        get() = _progressAnswers

    private var timer: CountDownTimer? = null

    private val _formattedTime = MutableLiveData<String>()
    val formattedTime: LiveData<String>
        get() = _formattedTime

    private val _question = MutableLiveData<Question>()
    val question: LiveData<Question>
        get() = _question

    private val _gameResult = MutableLiveData<GameResult>()
    val gameResult: LiveData<GameResult>
        get() = _gameResult

    private val _enoughCount = MutableLiveData<Boolean>()
    val enoughCount: LiveData<Boolean>
        get() = _enoughCount

    private val _enoughPercent = MutableLiveData<Boolean>()
    val enoughPercent: LiveData<Boolean>
        get() = _enoughPercent

    private val _minPercent = MutableLiveData<Int>()
    val minPercent: LiveData<Int>
        get() = _minPercent

    init {
        startGame()
    }

    private fun startGame() {
        getGameSettings()
        startTimer()
        generateQuestion()
        updateProgress()
    }



    private fun getGameSettings() {

        this.gameSettings = getGameSettingsUseCase.invoke(level)
        _minPercent.value = gameSettings.minPercentOfRightAnswers

    }

    private fun startTimer() {
        timer = object : CountDownTimer(
            gameSettings.gameTimeInSeconds * MILLIS_IN_SECONDS,
            MILLIS_IN_SECONDS
        ) {
            override fun onTick(p0: Long) {
                _formattedTime.value = formatTime(p0)
            }

            override fun onFinish() {
                finishGame()
            }

        }
        timer?.start()
    }

    private fun updateProgress() {
        val percent = calculatePercentOfRightAnswers()
        _percentOfRightAnswers.value = percent
        _progressAnswers.value = String.format(
            application.resources.getString(R.string.progress_answers),
            countOfRightAnswers, gameSettings.minCountOfRightAnswers
        )

        _enoughCount.value =
            countOfRightAnswers >= gameSettings.minCountOfRightAnswers
        _enoughPercent.value = percent >= gameSettings.minPercentOfRightAnswers
    }

    private fun calculatePercentOfRightAnswers(): Int {
        if (countOfQuestions==0) {return 0}
        return ((countOfRightAnswers / countOfQuestions.toDouble()) * 100).toInt()
    }

    private fun finishGame() {
        _gameResult.value = GameResult(
            enoughCount.value == true && enoughPercent.value == true,
            countOfRightAnswers,
            countOfQuestions,
            gameSettings
        )
    }

    fun chooseAnswer(number: Int) {
        checkAnswer(number)
        updateProgress()
        generateQuestion()
    }

    private fun checkAnswer(number: Int) {
        val rightAnswer = question.value?.rightAnswer
        if (number == rightAnswer) {
            countOfRightAnswers++
        }
        countOfQuestions++
    }

    private fun generateQuestion() {
        _question.value = generateQuestionUseCase(gameSettings.maxSumValue)
    }

    private fun formatTime(millisUntilFinished: Long): String {
        val seconds = millisUntilFinished / MILLIS_IN_SECONDS
        val minutes = seconds / SECONDS_IN_MINUTES
        val leftSeconds = seconds - (minutes * SECONDS_IN_MINUTES)
        return String.format("%02d:%02d", minutes, leftSeconds)
    }


    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }


    companion object {
        private const val MILLIS_IN_SECONDS = 1000L
        private const val SECONDS_IN_MINUTES = 60
    }
}
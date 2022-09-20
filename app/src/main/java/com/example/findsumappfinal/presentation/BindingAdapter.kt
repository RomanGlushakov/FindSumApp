package com.example.findsumappfinal.presentation

import android.content.Context
import android.content.res.ColorStateList
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.findsumappfinal.R
import com.example.findsumappfinal.domain.entity.GameResult

@BindingAdapter("requireAnswers")
fun bindRequireAnswers(tv: TextView, count: Int) {
    tv.text = String.format(
        tv.context.getString(R.string.required_score),
        count
    )
}

@BindingAdapter("score")
fun bindScoreAnswers(tv: TextView, count: Int) {
    tv.text = String.format(
        tv.context.getString(R.string.score_answers), count)
}

@BindingAdapter("requirePercent")
fun bindRequirePercent(tv: TextView, count: Int) {
    tv.text = String.format(
        tv.context.getString(R.string.required_percentage), count)
}

@BindingAdapter("percent")
fun bindPercentOfAnswers(tv: TextView, gameResult: GameResult) {
    val percent = getPercentOfRightAnswers(gameResult)

    tv.text = String.format(
        tv.context.getString(R.string.score_percentage),
        percent
    )
}

@BindingAdapter("setImage")
fun bindImage(imageView: ImageView, winner: Boolean) {

    val imageResId = if (winner) {
        R.drawable.ic_smile
    } else (
            R.drawable.ic_sad
            )
    imageView.setImageResource(imageResId)

}

private fun getPercentOfRightAnswers(gameResult: GameResult) = with(gameResult) {
    if (countOfQuestions == 0) {
        0
    } else {
        ((countOfRightAnswers / countOfQuestions.toDouble()) * 100).toInt()
    }
}

@BindingAdapter("enoughCount")
fun bindEnoughCount(tv: TextView, winner: Boolean) {

    tv.setTextColor(getColorByState(tv.context, winner))

}

@BindingAdapter("enoughPercent")
fun bindEnoughPercent(progressBar: ProgressBar, winner: Boolean) {

    val color = getColorByState(progressBar.context, winner)
    progressBar.progressTintList = ColorStateList.valueOf(color)

}

@BindingAdapter("numberAsText")
fun bindBNumberAsText(tv: TextView, count: Int ) {
    tv.text = count.toString()
}

@BindingAdapter("onOptionClick")
fun bindOnOptionClick(tv: TextView, onOptionClickListener: OnOptionClickListener) {
    tv.setOnClickListener {
        onOptionClickListener.onOptionClick(tv.text.toString().toInt())
    }
}

interface OnOptionClickListener{
    fun onOptionClick(opt: Int)
}



private fun getColorByState(context: Context, goodState: Boolean): Int {
    val colorResId = if (goodState) {
        android.R.color.holo_green_light
    } else {
        android.R.color.holo_red_light
    }
    return ContextCompat.getColor(context, colorResId)
}


package com.example.findsumappfinal.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.findsumappfinal.R
import com.example.findsumappfinal.databinding.FragmentGameFinishedBinding
import com.example.findsumappfinal.domain.entity.GameResult
import com.example.findsumappfinal.domain.entity.Level


class GameFinishedFragment : Fragment() {

    private val args by navArgs<GameFinishedFragmentArgs>()


    private var _binding: FragmentGameFinishedBinding? = null
    private val binding: FragmentGameFinishedBinding
        get() = _binding ?: throw RuntimeException("FragmentGameFinishedBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameFinishedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    private fun setupResult() {
        binding.gameResult = args.gameResult

        val imageResId = if (args.gameResult.winner) {
            R.drawable.ic_smile
        } else (
                R.drawable.ic_sad
                )

        binding.emojiResult.setImageResource(imageResId)


    }





    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupResult()
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.buttonRetry.setOnClickListener {
            retryGame()
        }
    }

    private fun retryGame() {
        findNavController().popBackStack()
    }


}
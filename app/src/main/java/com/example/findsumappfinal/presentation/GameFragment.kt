package com.example.findsumappfinal.presentation

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.findsumappfinal.R
import com.example.findsumappfinal.databinding.FragmentGameBinding
import com.example.findsumappfinal.domain.entity.GameResult
import com.example.findsumappfinal.domain.entity.Level


class GameFragment : Fragment() {


    private val args by navArgs<GameFragmentArgs>()


    private val viewModel by lazy {
        ViewModelProvider(
            this,
            GameViewModelFactory(
                args.level,
                requireActivity().application
            )
        )[GameViewModel::class.java]
    }

    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("GameFragmentBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        observeViewModel()



    }


    private fun observeViewModel() {

        viewModel.gameResult.observe(viewLifecycleOwner) {
            launchGameFinishedFragment(it)
        }
    }


    private fun launchGameFinishedFragment(gameResult: GameResult) {

        findNavController().navigate(
            GameFragmentDirections.actionGameFragment2ToGameFinishedFragment2(gameResult)
        )
    }

}
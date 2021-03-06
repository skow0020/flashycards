package com.example.flashycards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class MainFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.colorsButton).setOnClickListener {
            findNavController().navigate(R.id.action_MainFragment_to_ColorsFragment)
        }

        view.findViewById<Button>(R.id.numbersButton).setOnClickListener {
            findNavController().navigate(R.id.action_MainFragment_to_NumbersFragment)
        }

        view.findViewById<Button>(R.id.wordsButton).setOnClickListener {
            findNavController().navigate(R.id.action_MainFragment_to_WordsFragment)
        }
    }
}
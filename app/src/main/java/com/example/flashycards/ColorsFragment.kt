package com.example.flashycards

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlin.random.Random

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class ColorsFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_colors, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.setOnClickListener{ _ ->
            view.setBackgroundColor(getRandomColor())
        }
    }

    private fun getRandomColor(): Int {
        val index = Random.nextInt(0, 11)
        val colors: Array<Int> = arrayOf(
            Color.BLACK,
            Color.BLUE,
            Color.CYAN,
            Color.DKGRAY,
            Color.GRAY,
            Color.GREEN,
            Color.LTGRAY,
            Color.MAGENTA,
            Color.RED,
            Color.TRANSPARENT,
            Color.WHITE,
            Color.YELLOW
        )
        return colors[index]
    }
}
package com.example.flashycards

import android.os.Bundle
import android.view.*
import android.widget.NumberPicker
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlin.random.Random

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class NumbersFragment : Fragment() {

    private lateinit var shownNumber: TextView
    private lateinit var rangeMinPicker: NumberPicker
    private lateinit var rangeMaxPicker: NumberPicker

    var sequentialOption = false

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_numbers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        setNumberPickers(view)
        setShownNumber(view)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.numbers_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.sequential -> {
                val sequentialToggle = toggleSequential()
                item.isChecked = !item.isChecked
                val message = if (sequentialToggle) "Sequential Mode" else "Random Mode"
                view?.let { Utilities.showSnackbar(it, message) }

                shownNumber.text = rangeMinPicker.value.toString()
                true
            }
            R.id.help -> {
                // TODO
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setNumberPickers(view: View) {
        rangeMinPicker = view.findViewById(R.id.rangeMin)
        rangeMaxPicker = view.findViewById(R.id.rangeMax)

        rangeMinPicker.value = 0
        rangeMaxPicker.value = 20
        rangeMinPicker.minValue = 0
        rangeMinPicker.maxValue = 90
        rangeMaxPicker.minValue = 10
        rangeMaxPicker.maxValue = 100

        rangeMaxPicker.setOnValueChangedListener { _, _, newVal ->
            rangeMinPicker.maxValue = newVal - 1
        }

        rangeMinPicker.setOnValueChangedListener { _, _, newVal ->
            rangeMaxPicker.minValue = newVal + 1
        }
    }

    private fun setShownNumber(view: View) {
        shownNumber = view.findViewById(R.id.randomNum)

        view.findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { _ ->
            when (sequentialOption) {
                true -> shownNumber.text =
                    (Integer.parseInt(shownNumber.text.toString()) + 1).toString()
                false -> shownNumber.text =
                    getRandomNumber(rangeMinPicker.value, rangeMaxPicker.value).toString()
            }
        }
    }

    private fun toggleSequential(): Boolean {
        sequentialOption = !sequentialOption
        return sequentialOption
    }

    private fun getRandomNumber(start: Int, end: Int): Int {
        return Random.nextInt(start, end)
    }
}
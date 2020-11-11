package com.example.flashycards

import android.app.AlertDialog
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.*
import kotlin.random.Random

class WordsFragment : Fragment() {
    private var words = mutableListOf<String>()
    private val wordsFile = "words.txt"
    private lateinit var shownWord: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_words, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        shownWord = view.findViewById(R.id.word)

        createWordsFile()
        loadWords()

        setOnClickAddWord(view)
        setOnClickRemoveWord(view)

        if (words.count() == 0) shownWord.text = "Add some words!"
        else shownWord.text = getRandomWord()

        view.findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { _ ->
            if (words.count() > 0) shownWord.text = getRandomWord()
        }
    }

    private fun setOnClickRemoveWord(view: View) {
        view.findViewById<Button>(R.id.removeWord).setOnClickListener { _ ->
            val alertDialog: AlertDialog.Builder = AlertDialog.Builder(view.context)
            alertDialog.setTitle("Remove a word")

            val input = EditText(view.context)
            input.inputType = InputType.TYPE_CLASS_TEXT
            alertDialog.setView(input)

            alertDialog.setPositiveButton(
                "OK"
            ) { _, _ ->
                val wordToRemove = input.text.toString()
                if (!words.contains(wordToRemove)) {
                    view.let { Utilities.showSnackbar(it, "Word not found to remove") }
                } else {
                    removeWord(wordToRemove)
                    shownWord.text = getRandomWord()
                }
            }

            alertDialog.create()
            alertDialog.show()
        }
    }

    private fun setOnClickAddWord(view: View) {
        view.findViewById<Button>(R.id.addWord).setOnClickListener { _ ->
            val alertDialog: AlertDialog.Builder = AlertDialog.Builder(view.context)
            alertDialog.setTitle("Add a word")

            val input = EditText(view.context)
            input.inputType = InputType.TYPE_CLASS_TEXT
            alertDialog.setView(input)

            alertDialog.setPositiveButton(
                "OK"
            ) { _, _ ->
                val newWord = input.text.toString()
                if (newWord.isEmpty() || newWord.length > 12) {
                    view.let {
                        Utilities.showSnackbar(
                            it,
                            "Word should be between 1 and 12 characters"
                        )
                    }
                } else {
                    addWordViaButton(newWord)
                    shownWord.text = newWord
                }
            }

            alertDialog.create()
            alertDialog.show()
        }
    }

    private fun getRandomWord(): String {
        if (words.count() == 0) return "Add some words!"
        return words[Random.nextInt(0, words.count())]
    }

    private fun addWordViaButton(word: String) {
        val file = getWordsFile()

        if (file.length() <1 ) file.appendText(word)
        else file.appendText(System.getProperty("line.separator") + word.trim())

        words.add(word)
    }

    private fun removeWord(word: String) {
        val wordsFile = getWordsFile()
        val tempFile = getTempFile()

        val reader = BufferedReader(FileReader(wordsFile))
        val writer = BufferedWriter(FileWriter(tempFile))

        var currentLine: String

        var removed = false
        try {
            while (reader.readLine().also { currentLine = it } != null) {
                if (currentLine == word) {
                    removed = true
                    continue
                }
                else if (currentLine == "") continue
                writer.write(currentLine + System.getProperty("line.separator"))
            }
        } catch (ex: NullPointerException){
            // Avoid it being null issue - probably a better way
        }
        finally {
            writer.close()
            reader.close()

            if (removed) {
                words.remove(word)
                view?.let { Utilities.showSnackbar(it, "Word removed") }
            }
            else { view?.let { Utilities.showSnackbar(it, "Word not found") } }
            tempFile.renameTo(wordsFile)
        }
    }

    private fun loadWords() {
        val file = getWordsFile()

        val savedWords = file.readLines().toMutableList()
        for (word: String in savedWords) words.add(word)
    }

    private fun createWordsFile(): Boolean {
        val file = File(activity?.filesDir, wordsFile)

        return file.createNewFile()
    }

    private fun getWordsFile(): File {
        return File(activity?.filesDir, wordsFile)
    }

    private fun getTempFile(): File {
        val file = File(activity?.filesDir, "temp.txt")

        file.createNewFile()

        return file
    }
}
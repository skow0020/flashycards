package com.example.flashycards

import android.view.View
import com.google.android.material.snackbar.Snackbar


object Utilities {
    fun showSnackbar(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
            .setAction("Action", null).show()
    }
}
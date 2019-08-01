package com.example.pruebazav.Base

import android.app.AlertDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.example.pruebazav.R
import dmax.dialog.SpotsDialog

open class BaseActivity : AppCompatActivity() {

    private var dialog: AlertDialog? = null

    fun Context.showProgress(): AlertDialog {
        dialog = SpotsDialog.Builder()
            .setContext(this)
            .setCancelable(false)
            .setTheme(R.style.Custom)
            .build()
        dialog?.show()
        return dialog!!
    }

    fun closeProgress() {
        if (dialog?.isShowing!!)
            dialog?.dismiss()
    }
}
package com.movie.app.extensions

import android.content.Context
import android.content.DialogInterface
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun Context.showDialog(
    message: String,
    positiveText: String,
    negativeText: String? = null,
    positiveListener: DialogInterface.OnClickListener? = null
) {

    val dialogBuilder = MaterialAlertDialogBuilder(this)
        .setMessage(message)
        .setPositiveButton(positiveText, positiveListener)

    if (!negativeText.isNullOrEmpty()) {
        dialogBuilder.setNegativeButton(negativeText, null)
    }

    dialogBuilder.show()
}
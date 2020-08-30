package com.sejarah.apps.utils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.util.TypedValue
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import com.github.barteksc.pdfviewer.PDFView
import com.github.barteksc.pdfviewer.util.FitPolicy
import com.google.android.material.textfield.TextInputLayout
import com.sejarah.apps.databinding.DialogNilaiBinding
import com.sejarah.apps.databinding.DialogProgressBinding
import com.sejarah.apps.databinding.DialogVideoBinding
import com.sejarah.apps.ui.MainActivity
import java.util.regex.Matcher
import java.util.regex.Pattern
import kotlin.math.roundToInt


fun Activity.dialogVideo(source: Int) {
    val videoPath: String? = "android.resource://" + this.packageName + "/" + source
    val dialog = Dialog(this, android.R.style.Theme_Material_Light)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    val binding: DialogVideoBinding = DialogVideoBinding.inflate(this.layoutInflater)
    dialog.setContentView(binding.root)
    dialog.setCanceledOnTouchOutside(true)

    val uri: Uri = Uri.parse(videoPath)
    binding.video.setVideoURI(uri)
    binding.video.start()

    binding.btnMulai.setOnClickListener {
        if (binding.video.currentPosition > 100) {
            binding.video.start()
        } else {
            binding.video.setVideoURI(uri)
            binding.video.start()
        }
    }
    binding.btnStop.setOnClickListener {
        binding.video.pause()
    }
    val lp = WindowManager.LayoutParams()
    lp.copyFrom(dialog.window!!.attributes)
    lp.width = WindowManager.LayoutParams.MATCH_PARENT
    lp.height = WindowManager.LayoutParams.WRAP_CONTENT
    dialog.window!!.attributes = lp
    dialog.show()
}

fun Activity.showScore(score: Int) {
    dialog = Dialog(this, android.R.style.Theme_Material_Light)
    dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
    val binding: DialogNilaiBinding = DialogNilaiBinding.inflate(this.layoutInflater)
    dialog!!.setContentView(binding.root)
    dialog!!.setCancelable(false)
    dialog!!.setCanceledOnTouchOutside(false)
    binding.txtScore.text = score.toString()
    binding.btnTutup.setOnClickListener {
        this.onBackPressed()
        dialog!!.dismiss()
    }
    val lp = WindowManager.LayoutParams()
    lp.copyFrom(dialog!!.window!!.attributes)
    lp.width = WindowManager.LayoutParams.WRAP_CONTENT
    lp.height = WindowManager.LayoutParams.WRAP_CONTENT
    dialog!!.window!!.attributes = lp
    dialog!!.show()
}

private var dialog: Dialog? = null
fun Activity.showDialog() {
    dialog = Dialog(this, android.R.style.Theme_Material_Light)
    dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
    val binding: DialogProgressBinding = DialogProgressBinding.inflate(this.layoutInflater)
    dialog!!.setContentView(binding.root)
    dialog!!.setCancelable(false)
    dialog!!.setCanceledOnTouchOutside(false)
    val lp = WindowManager.LayoutParams()
    lp.copyFrom(dialog!!.window!!.attributes)
    lp.width = WindowManager.LayoutParams.WRAP_CONTENT
    lp.height = WindowManager.LayoutParams.WRAP_CONTENT
    dialog!!.window!!.attributes = lp
    dialog!!.show()
}

fun hideDialog() {
    if (dialog!!.isShowing) {
        dialog!!.dismiss()
    }
}

fun Context.dpToPx(dp: Int): Int {
    val r: Resources = this.resources
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp.toFloat(),
        r.displayMetrics
    ).roundToInt()
}

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun TextInputLayout.inputError(data: String, message: String): Boolean {
    return if (data.isEmpty()) {
        this.error = message
        false
    } else {
        this.error = null
        true
    }
}

fun String.isEmailValid(): Boolean {
    val expression = "^[\\w.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
    val pattern: Pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
    val matcher: Matcher = pattern.matcher(this)
    return matcher.matches()
}

fun PDFView.load(activity: Activity, uri: String) {
    fromAsset(uri)
        .enableDoubletap(true)
        .swipeHorizontal(false)
        .defaultPage(0)
        .spacing(0)
        .onRender { hideDialog() }
        .onLoad { activity.showDialog() }
        .onError { e -> println("ERROR PDF :  ${e.printStackTrace()}") }
        .pageFitPolicy(FitPolicy.BOTH)
        .fitEachPage(true)
        .load()
}
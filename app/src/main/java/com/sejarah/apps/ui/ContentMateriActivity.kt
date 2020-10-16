package com.sejarah.apps.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.sejarah.apps.databinding.ActivityContentMateriBinding
import com.sejarah.apps.utils.dialogVideo
import com.sejarah.apps.utils.load

class ContentMateriActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_DATA = "EXTRA_DATA"
        const val EXTRA_TITLE = "EXTRA_TITLE"
        const val EXTRA_VIDEO = "EXTRA_VIDEO"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityContentMateriBinding = ActivityContentMateriBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.include.toolbar)
        supportActionBar?.title = intent?.extras?.getString(EXTRA_TITLE)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (intent?.extras?.getInt(EXTRA_VIDEO) == 0 ){
            binding.btnVideo.visibility = View.GONE
        }else{
            binding.btnVideo.visibility = View.VISIBLE
            binding.btnVideo.setOnClickListener {
                dialogVideo(intent?.extras?.getInt(EXTRA_VIDEO)!!)
            }
        }
        binding.pdfView.load(this,intent?.extras?.getString(EXTRA_DATA)!!)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
package com.sejarah.apps.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sejarah.apps.databinding.ActivityContentPendahuluanBinding
import com.sejarah.apps.utils.load

class ContentPendahuluanActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_DATA = "EXTRA_DATA"
        const val EXTRA_TITLE = "EXTRA_TITLE"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityContentPendahuluanBinding = ActivityContentPendahuluanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.include.toolbar)
        supportActionBar?.title = intent?.extras?.getString(EXTRA_TITLE)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.pdfView.load(this,intent?.extras?.getString(EXTRA_DATA)!!)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
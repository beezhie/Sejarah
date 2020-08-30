package com.sejarah.apps.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sejarah.apps.databinding.ActivityJawabanContohSoalBinding
import com.sejarah.apps.utils.load

class JawabanContohSoalActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityJawabanContohSoalBinding = ActivityJawabanContohSoalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.include.toolbar)
        supportActionBar?.title = "Jawaban Contoh Latihan Soal"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.pdfView.load(this,"Jawaban Contoh Latihan Soal.pdf")
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
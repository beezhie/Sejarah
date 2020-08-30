package com.sejarah.apps.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sejarah.apps.databinding.ActivityContohSoalBinding
import com.sejarah.apps.utils.load

class ContohSoalActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding:ActivityContohSoalBinding = ActivityContohSoalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.include.toolbar)
        supportActionBar?.title = "Contoh Latihan Soal"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.pdfView.load(this,"Contoh Latihan Soal.pdf")
        binding.btnJawaban.setOnClickListener{
            startActivity(Intent(this@ContohSoalActivity,JawabanContohSoalActivity::class.java))
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
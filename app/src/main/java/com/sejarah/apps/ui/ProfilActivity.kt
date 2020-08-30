package com.sejarah.apps.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sejarah.apps.databinding.ActivityProfilBinding
import com.sejarah.apps.utils.load

class ProfilActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding:ActivityProfilBinding = ActivityProfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.include.toolbar)
        supportActionBar?.title = "Profil"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.pdfView.load(this,"Profil.pdf")
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
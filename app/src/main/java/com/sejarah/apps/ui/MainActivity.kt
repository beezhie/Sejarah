package com.sejarah.apps.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.sejarah.apps.R
import com.sejarah.apps.adapter.MenuAdapter
import com.sejarah.apps.data.Menu
import com.sejarah.apps.databinding.ActivityMainBinding
import com.sejarah.apps.utils.SpacingItemDecoration
import com.sejarah.apps.utils.dpToPx


class MainActivity : AppCompatActivity() {
    private var doubleBackToExitPressedOnce = false
    private lateinit var adapterMenu: MenuAdapter
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        adapterMenu = MenuAdapter()
        binding.rvMenu.layoutManager = GridLayoutManager(this, 2)
        binding.rvMenu.addItemDecoration(SpacingItemDecoration(2, dpToPx(2), true))
        binding.rvMenu.setHasFixedSize(true)
        binding.rvMenu.isNestedScrollingEnabled = false
        binding.rvMenu.adapter = adapterMenu

        adapterMenu.itemClick(object :MenuAdapter.OnItemClick{
            override fun onItemClicked(item: Menu) {
                if(item.navigate != null){
                    startActivity(item.navigate)
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        fetchMenu()
    }

    private fun fetchMenu() {
        val listMenu: ArrayList<Menu> = ArrayList()
        val latihan = if(auth.currentUser == null){
            Intent(
                this@MainActivity,
                LoginActivity::class.java
            )
        }else{
            Intent(
                this@MainActivity,
                LatihanSoalActivity::class.java
            )
        }
        listMenu.add(
            Menu(
                "PENDAHULUAN", R.drawable.pendahuluan, 0, 1, Intent(
                    this@MainActivity,
                    PendahuluanActivity::class.java
                )
            )
        )
        listMenu.add(
            Menu(
                "MATERI", R.drawable.materi, 0, 1, Intent(
                    this@MainActivity,
                    MateriActivity::class.java
                )
            )
        )
        listMenu.add(
            Menu(
                "VIDEO PEMBELAJARAN", R.drawable.video, 0, 1, Intent(
                    this@MainActivity,
                    VideoActivity::class.java
                )
            )
        )
        listMenu.add(
            Menu(
                "CONTOH SOAL", R.drawable.contoh, 0, 1, Intent(
                    this@MainActivity,
                    ContohSoalActivity::class.java
                )
            )
        )
        listMenu.add(
            Menu(
                "LATIHAN SOAL", R.drawable.latihan, 0, 1, latihan
            )
        )
        listMenu.add(
            Menu(
                "PROFIL", R.drawable.profil, 0, 1, Intent(
                    this@MainActivity,
                    ProfilActivity::class.java
                )
            )
        )
        adapterMenu.replaceAll(listMenu)
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
        }
        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Tekan Lagi Untuk Keluar", Toast.LENGTH_SHORT).show()
        Handler().postDelayed({
            doubleBackToExitPressedOnce = false
        }, 2000)
    }
}
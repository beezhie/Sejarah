package com.sejarah.apps.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.sejarah.apps.R
import com.sejarah.apps.adapter.MenuAdapter
import com.sejarah.apps.data.Menu
import com.sejarah.apps.databinding.ActivityPendahuluanBinding

class PendahuluanActivity : AppCompatActivity() {

    private lateinit var adapterMenu: MenuAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityPendahuluanBinding = ActivityPendahuluanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.include.toolbar)
        supportActionBar?.title = "Pendahuluan"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        adapterMenu = MenuAdapter()
        binding.rvMenu.layoutManager = LinearLayoutManager(this)
        binding.rvMenu.setHasFixedSize(true)
        binding.rvMenu.isNestedScrollingEnabled = false
        binding.rvMenu.adapter = adapterMenu
        fetchMenu()

        adapterMenu.itemClick(object :MenuAdapter.OnItemClick{
            override fun onItemClicked(item: Menu) {
                if(item.navigate != null){
                    startActivity(item.navigate)
                }
            }
        })
    }

    private fun fetchMenu() {
        val listMenu: ArrayList<Menu> = ArrayList()
        listMenu.add(
            Menu(
                "KIKD", R.drawable.pendahuluan, 0, 0, Intent(
                    this@PendahuluanActivity,
                    ContentPendahuluanActivity::class.java
                ).putExtra(ContentPendahuluanActivity.EXTRA_DATA, "KIKD.pdf")
                    .putExtra(ContentPendahuluanActivity.EXTRA_TITLE, "KIKD")
            )
        )
        listMenu.add(
            Menu(
                "Tujuan Pembelajaran", R.drawable.pendahuluan, 0, 0, Intent(
                    this@PendahuluanActivity,
                    ContentPendahuluanActivity::class.java
                ).putExtra(ContentPendahuluanActivity.EXTRA_DATA, "Tujuan Pembelajaran.pdf")
                    .putExtra(ContentPendahuluanActivity.EXTRA_TITLE, "Tujuan Pembelajaran")
            )
        )
        listMenu.add(
            Menu(
                "Peta Konsep", R.drawable.pendahuluan, 0, 0, Intent(
                    this@PendahuluanActivity,
                    ContentPendahuluanActivity::class.java
                ).putExtra(ContentPendahuluanActivity.EXTRA_DATA, "Peta Konsep.pdf")
                    .putExtra(ContentPendahuluanActivity.EXTRA_TITLE, "Peta Konsep")
            )
        )
        listMenu.add(
            Menu(
                "Daftar Pustaka", R.drawable.pendahuluan, 0, 0, Intent(
                    this@PendahuluanActivity,
                    ContentPendahuluanActivity::class.java
                ).putExtra(ContentPendahuluanActivity.EXTRA_DATA, "Daftar Pustaka.pdf")
                    .putExtra(ContentPendahuluanActivity.EXTRA_TITLE, "Daftar Pustaka")
            )
        )
        adapterMenu.replaceAll(listMenu)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

}
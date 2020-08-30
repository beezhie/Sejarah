package com.sejarah.apps.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.sejarah.apps.R
import com.sejarah.apps.adapter.MenuAdapter
import com.sejarah.apps.data.Menu
import com.sejarah.apps.databinding.ActivityVideoBinding
import com.sejarah.apps.utils.dialogVideo

class VideoActivity : AppCompatActivity() {

    private lateinit var adapterMenu: MenuAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityVideoBinding = ActivityVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.include.toolbar)
        supportActionBar?.title = "Video"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        adapterMenu = MenuAdapter()
        binding.rvMenu.layoutManager = LinearLayoutManager(this)
        binding.rvMenu.setHasFixedSize(true)
        binding.rvMenu.isNestedScrollingEnabled = false
        binding.rvMenu.adapter = adapterMenu
        fetchMenu()

        adapterMenu.itemClick(object : MenuAdapter.OnItemClick {
            override fun onItemClicked(item: Menu) {
                if (item.source > -1) {
                    dialogVideo(item.source)
                }
            }
        })
    }

    private fun fetchMenu() {
        val listMenu: ArrayList<Menu> = ArrayList()
        listMenu.add(
            Menu(
                "Serangan Umum 1 Maret", R.drawable.video, 0, 0, null, R.raw.serangan
            )
        )
        adapterMenu.replaceAll(listMenu)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
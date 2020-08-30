package com.sejarah.apps.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.sejarah.apps.R
import com.sejarah.apps.adapter.MenuAdapter
import com.sejarah.apps.data.Menu
import com.sejarah.apps.databinding.ActivityMateriBinding

class MateriActivity : AppCompatActivity() {
    private lateinit var adapterMenu: MenuAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMateriBinding = ActivityMateriBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.include.toolbar)
        supportActionBar?.title = "Materi"
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
                "Linggarjati", R.drawable.materi, 0, 0, Intent(
                    this@MateriActivity,
                    ContentMateriActivity::class.java
                ).putExtra(ContentMateriActivity.EXTRA_DATA, "Linggarjati.pdf")
                    .putExtra(ContentMateriActivity.EXTRA_TITLE, "Linggarjati")
            )
        )
        listMenu.add(
            Menu(
                "Serangan Umum 1 Maret", R.drawable.materi, 0, 0, Intent(
                    this@MateriActivity,
                    ContentMateriActivity::class.java
                ).putExtra(ContentMateriActivity.EXTRA_DATA, "Serangan Umum 1 Maret.pdf")
                    .putExtra(ContentMateriActivity.EXTRA_TITLE, "Serangan Umum 1 Maret")
            )
        )
        listMenu.add(
            Menu(
                "Agresi Militer I", R.drawable.materi, 0, 0, Intent(
                    this@MateriActivity,
                    ContentMateriActivity::class.java
                ).putExtra(ContentMateriActivity.EXTRA_DATA, "Agresi Militer I.pdf")
                    .putExtra(ContentMateriActivity.EXTRA_TITLE, "Agresi Militer I")
            )
        )
        listMenu.add(
            Menu(
                "Agresi Militer II", R.drawable.materi, 0, 0, Intent(
                    this@MateriActivity,
                    ContentMateriActivity::class.java
                ).putExtra(ContentMateriActivity.EXTRA_DATA, "Agresi Militer II.pdf")
                    .putExtra(ContentMateriActivity.EXTRA_TITLE, "Agresi Militer II")
            )
        )
        adapterMenu.replaceAll(listMenu)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
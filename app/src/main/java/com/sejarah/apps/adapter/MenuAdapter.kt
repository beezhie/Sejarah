package com.sejarah.apps.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sejarah.apps.R
import com.sejarah.apps.data.Menu

class MenuAdapter : RecyclerView.Adapter<MenuAdapter.MenuHolder>() {
    private var listGetMenu: MutableList<Menu> = ArrayList()
    private var onItemClick: OnItemClick? = null

    fun itemClick(onItemClick: OnItemClick?) {
        this.onItemClick = onItemClick
    }

    fun replaceAll(items: MutableList<Menu>) {
        listGetMenu.clear()
        listGetMenu = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MenuHolder {
        val view: View =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.item_rv_menu, viewGroup, false)
        return MenuHolder(view)
    }

    override fun onBindViewHolder(holder: MenuHolder, position: Int) {
        holder.bind(listGetMenu[position])
    }

    override fun getItemCount(): Int {
        return listGetMenu.size
    }

    inner class MenuHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var menu: TextView = itemView.findViewById(R.id.txtMenu)
        fun bind(item: Menu) {
            menu.text = item.nama
            when (item.posisi) {
                0 -> menu.setCompoundDrawablesWithIntrinsicBounds(item.gambar, 0, 0, 0)
                1 -> menu.setCompoundDrawablesWithIntrinsicBounds(0, item.gambar, 0, 0)
                2 -> menu.setCompoundDrawablesWithIntrinsicBounds(0, 0, item.gambar, 0)
                3 -> menu.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, item.gambar)
            }

            itemView.setOnClickListener { onItemClick!!.onItemClicked(item) }

        }
    }

    interface OnItemClick {
        fun onItemClicked(item: Menu)
    }
}
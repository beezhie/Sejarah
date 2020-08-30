package com.sejarah.apps.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sejarah.apps.R
import com.sejarah.apps.data.Nilai

class NilaiAdapter : RecyclerView.Adapter<NilaiAdapter.NilaiHolder>() {
    private var listGetNilai: ArrayList<Nilai> = ArrayList()

    fun replaceAll(items: ArrayList<Nilai>) {
        println("Ukuran adapter ${items.size}")
        listGetNilai.clear()
        listGetNilai = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): NilaiHolder {
        val view: View =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.item_rv_nilai, viewGroup, false)
        return NilaiHolder(view)
    }

    override fun onBindViewHolder(holder: NilaiHolder, position: Int) {
        holder.bind(listGetNilai[position])
    }

    override fun getItemCount(): Int {
        return listGetNilai.size
    }

    @SuppressLint("SetTextI18n")
    class NilaiHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var nama: TextView = itemView.findViewById(R.id.txtNama)
        private var nilai: TextView = itemView.findViewById(R.id.txtNilai)

        fun bind(item: Nilai) {
            nama.text = ":\t${item.nama}"
            nilai.text = ":\t${item.nilai}"
        }
    }
}

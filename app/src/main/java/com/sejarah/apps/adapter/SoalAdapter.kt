package com.sejarah.apps.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sejarah.apps.R
import com.sejarah.apps.data.Soal


class SoalAdapter : RecyclerView.Adapter<SoalAdapter.SoalHolder>() {
    private var listGetSoal: MutableList<Soal> = ArrayList()
    private var onItemClick: OnItemClick? = null

    fun itemClick(onItemClick: OnItemClick?) {
        this.onItemClick = onItemClick
    }

    fun replaceAll(items: MutableList<Soal>) {
        listGetSoal.clear()
        listGetSoal = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): SoalHolder {
        val view: View =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.item_rv_soal, viewGroup, false)
        return SoalHolder(view)
    }

    override fun onBindViewHolder(holder: SoalHolder, position: Int) {
        holder.bind(listGetSoal[position], position)
    }

    override fun getItemCount(): Int {
        return listGetSoal.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    @SuppressLint("SetTextI18n")
    inner class SoalHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var nomer: TextView = itemView.findViewById(R.id.txtNomor)
        private var pertanyaan: TextView = itemView.findViewById(R.id.txtPertanyaan)
        private var pilihan: RadioGroup = itemView.findViewById(R.id.radioGroup)
        private var pilihanA: RadioButton = itemView.findViewById(R.id.pilihanA)
        private var pilihanB: RadioButton = itemView.findViewById(R.id.pilihanB)
        private var pilihanC: RadioButton = itemView.findViewById(R.id.pilihanC)
        private var pilihanD: RadioButton = itemView.findViewById(R.id.pilihanD)
        private var pilihanE: RadioButton = itemView.findViewById(R.id.pilihanE)
        fun bind(item: Soal, nomor: Int) {
            nomer.text = "${nomor + 1}. "
            pertanyaan.text = item.pertanyaan

            pilihanA.text = item.pilihan[0]
            pilihanB.text = item.pilihan[1]
            pilihanC.text = item.pilihan[2]
            pilihanD.text = item.pilihan[3]
            pilihanE.text = item.pilihan[4]

            pilihan.setOnCheckedChangeListener { group, checkedId ->
                println("Radio button id : ${checkedId} | Group : ${group.checkedRadioButtonId}")
                val checkedRadioButton = group.findViewById<View>(checkedId) as RadioButton
                val isChecked = checkedRadioButton.isChecked
                if (isChecked) {
                    if (item.jawaban == checkedRadioButton.text) {
                        onItemClick!!.onItemClicked(adapterPosition, 1)
                    } else {
                        onItemClick!!.onItemClicked(adapterPosition, 0)
                    }
                }
            }

        }
    }

    interface OnItemClick {
        fun onItemClicked(position: Int, nilai: Int)
    }
}
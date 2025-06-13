package com.example.stationary.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.stationary.R
import com.example.stationary.activities.NotaActivity
import com.example.stationary.models.Payment
import java.text.SimpleDateFormat
import java.util.*

class LaporanAdapter(private var dataList: List<Payment>) : RecyclerView.Adapter<LaporanAdapter.LaporanViewHolder>() {

    private val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaporanViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_laporan, parent, false)
        return LaporanViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: LaporanViewHolder, position: Int) {
        val currentItem = dataList[position]
        val formattedDate = dateFormat.format(currentItem.tanggal)

        holder.txtTanggalTransaksi.text = formattedDate
        holder.txtNamaPembeli.text = currentItem.nama_pembeli
        holder.txtTransaksiTotal.text = "Rp. ${currentItem.total_bayar}"

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, NotaActivity::class.java).apply {
                putExtra("PAYMENT_ID", currentItem.id_pembayaran)
                putExtra("SOURCE", "LaporanActivity")
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = dataList.size

    // ViewHolder untuk RecyclerView
    class LaporanViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtTanggalTransaksi: TextView = itemView.findViewById(R.id.txtTanggalTransaksi)
        val txtNamaPembeli: TextView = itemView.findViewById(R.id.txtNamaPembeli)
        val txtTransaksiTotal: TextView = itemView.findViewById(R.id.txtTransaksiTotal)
    }

    // Fungsi untuk update data list
    fun updateData(newList: List<Payment>) {
        dataList = newList
        notifyDataSetChanged()
    }
}
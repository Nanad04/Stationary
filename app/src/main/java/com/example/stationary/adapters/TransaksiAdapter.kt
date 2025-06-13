package com.example.stationary.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.stationary.R
import com.example.stationary.models.Product

class TransaksiAdapter(
    private val productList: List<Product>,
    private val onQuantityChange: (Product, Int) -> Unit
) : RecyclerView.Adapter<TransaksiAdapter.TransaksiViewHolder>() {

    private val productQuantities = mutableMapOf<Product, Int>()

    inner class TransaksiViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtNamaTransaksi: TextView = itemView.findViewById(R.id.txtNamaTransaksi)
        val txtHargaTransaksi: TextView = itemView.findViewById(R.id.txtHargaTransaksi)
        val txtQty: TextView = itemView.findViewById(R.id.txtQty)
        val btnPlus: ImageButton = itemView.findViewById(R.id.btnPlus)
        val btnMinus: ImageButton = itemView.findViewById(R.id.btnMinus)
        val txtStokTersisa: TextView = itemView.findViewById(R.id.txtStokTersisa)

        fun bind(product: Product) {
            txtNamaTransaksi.text = product.nama
            txtHargaTransaksi.text = "Rp. ${product.harga}"
            val quantity = productQuantities[product] ?: 0
            txtQty.text = quantity.toString()

            // Tampilkan stok tersisa
            val stokSisa = product.stok - quantity
            txtStokTersisa.text = "Stok tersisa: $stokSisa"
            txtStokTersisa.setTextColor(
                if (stokSisa <= 0) Color.RED else Color.parseColor("#2E7D32") // merah jika habis, hijau jika masih
            )

            btnPlus.setOnClickListener {
                val currentQty = productQuantities[product] ?: 0
                if (currentQty < product.stok) {
                    val newQty = currentQty + 1
                    productQuantities[product] = newQty
                    txtQty.text = newQty.toString()

                    val updatedSisa = product.stok - newQty
                    txtStokTersisa.text = "Stok tersisa: $updatedSisa"
                    txtStokTersisa.setTextColor(
                        if (updatedSisa <= 0) Color.RED else Color.parseColor("#2E7D32")
                    )

                    onQuantityChange(product, newQty)
                }
            }

            btnMinus.setOnClickListener {
                val currentQty = productQuantities[product] ?: 0
                if (currentQty > 0) {
                    val newQty = currentQty - 1
                    productQuantities[product] = newQty
                    txtQty.text = newQty.toString()

                    val updatedSisa = product.stok - newQty
                    txtStokTersisa.text = "Stok tersisa: $updatedSisa"
                    txtStokTersisa.setTextColor(
                        if (updatedSisa <= 0) Color.RED else Color.parseColor("#2E7D32")
                    )

                    onQuantityChange(product, newQty)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransaksiViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.purchase_item, parent, false)
        return TransaksiViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TransaksiViewHolder, position: Int) {
        holder.bind(productList[position])
    }

    override fun getItemCount(): Int = productList.size
}

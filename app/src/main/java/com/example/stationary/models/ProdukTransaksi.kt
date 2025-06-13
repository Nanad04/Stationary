package com.example.stationary.models

class ProdukTransaksi {
    data class ProdukTransaksi(
        val nama: String,
        val harga: Int,
        val jumlah: Int,
        val stokTersisa: Int
    )
}
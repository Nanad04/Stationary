package com.example.stationary.models

import java.io.Serializable

class Sale (
    var id_detil: Int = 0,
    var id_pembayaran: Int,
    var id_produk: Int,
    var nama_produk: String,
    var jumlah: Int,
    var harga_satuan: Double
) : Serializable

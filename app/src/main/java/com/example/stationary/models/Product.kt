package com.example.stationary.models

import java.io.Serializable

class Product (
    var id: Int = 0,
    var nama: String,
    var harga: Double,
    var stok: Int
) : Serializable
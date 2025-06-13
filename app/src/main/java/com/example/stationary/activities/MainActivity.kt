package com.example.stationary.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.stationary.R
import androidx.appcompat.app.AppCompatActivity
import android.widget.LinearLayout
import android.widget.ImageButton

class MainActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Mendapatkan referensi ke tombol dari layout
        val btnAddTransaksi = findViewById<LinearLayout>(R.id.btnAddTransaksi)
        val btnProductList = findViewById<LinearLayout>(R.id.btnProductList)
        val btnLaporan = findViewById<LinearLayout>(R.id.btnLaporan)
        val btnAboutUs = findViewById<LinearLayout>(R.id.btnAboutUs)
        val btnLogout = findViewById<ImageButton>(R.id.btnLogout)


        // Mengatur OnClickListener untuk masing-masing tombol

        // Menambah Transaksi
        btnAddTransaksi.setOnClickListener {
            val intent = Intent(this, AddTransaksiActivity::class.java)
            startActivity(intent)
        }

        // Melihat Daftar Produk
        btnProductList.setOnClickListener {
            val intent = Intent(this, ProductListActivity::class.java)
            startActivity(intent)
        }

        // Melihat Laporan
        btnLaporan.setOnClickListener {
            val intent = Intent(this, LaporanActivity::class.java)
            startActivity(intent)
        }

        // About Us
        btnAboutUs.setOnClickListener {
            val intent = Intent(this, AboutUsActivity::class.java)
            startActivity(intent)
            finish() // Optional: Close the MainActivity
        }

        // Logout
        btnLogout.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish() // Optional: Close the MainActivity
        }
    }

    fun openProductList(view: View) {}
    fun openPurchaseDetails(view: View) {}
    fun openAddPesanan(view: View) {}
}
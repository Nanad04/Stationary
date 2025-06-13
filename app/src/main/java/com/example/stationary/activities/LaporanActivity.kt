package com.example.stationary.activities

import android.content.ContentValues
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stationary.R
import com.example.stationary.adapters.LaporanAdapter
import com.example.stationary.database.DatabaseHelper
import com.example.stationary.models.Payment
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class LaporanActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var laporanAdapter: LaporanAdapter
    private lateinit var rvLaporan: RecyclerView
    private lateinit var icBack: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_laporan)

        dbHelper = DatabaseHelper(this)

        // Inisialisasi RecyclerView
        rvLaporan = findViewById(R.id.rv_pendapatan)
        rvLaporan.layoutManager = LinearLayoutManager(this)
        icBack = findViewById(R.id.ic_back)

        // Ambil data laporan dari database
        val laporanList = dbHelper.getAllLaporan()
        laporanAdapter = LaporanAdapter(laporanList)
        rvLaporan.adapter = laporanAdapter

        // Hitung dan tampilkan total pendapatan
        val totalPendapatanTextView: TextView = findViewById(R.id.totalPendapatanTextView)
        val totalPendapatan = calculateTotalTransaksi(laporanList)
        totalPendapatanTextView.text = "Rp. $totalPendapatan"

        // Tombol untuk ekspor CSV
        val btnExportCsv: Button = findViewById(R.id.btnExportCsv)
        btnExportCsv.setOnClickListener {
            val freshList = dbHelper.getAllLaporan()
            exportToCsv(freshList)
        }

        // Tombol kembali
        icBack.setOnClickListener {
            finish()
        }

        // ✅ Tombol reset laporan
        val btnResetLaporan: ImageView = findViewById(R.id.btnResetLaporan)
        btnResetLaporan.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Konfirmasi Reset")
                .setMessage("Yakin ingin menghapus semua data laporan?")
                .setPositiveButton("Ya") { _, _ ->
                    dbHelper.resetLaporan() // fungsi ini akan kamu tambahkan di DatabaseHelper.kt

                    val updatedList = dbHelper.getAllLaporan()
                    laporanAdapter.updateData(updatedList)
                    totalPendapatanTextView.text = "Rp. 0"

                    Toast.makeText(this, "Data laporan berhasil di-reset", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Batal", null)
                .show()
        }
    }

    // Fungsi ekspor ke folder publik Downloads
    private fun exportToCsv(data: List<Payment>) {
        val fileName = "Laporan penjualan.csv"
        val csvHeader = "Tanggal,Nama Pembeli,Total Pembayaran\n"
        val csvBody = data.joinToString("\n") {
            "${formatTanggal(it.tanggal)},\"${it.nama_pembeli}\",${it.total_bayar}"
        }
        val csvContent = csvHeader + csvBody

        try {
            val resolver = contentResolver
            val contentValues = ContentValues().apply {
                put(MediaStore.Downloads.DISPLAY_NAME, fileName)
                put(MediaStore.Downloads.MIME_TYPE, "text/csv")
            }
            val uri = resolver.insert(MediaStore.Downloads.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY), contentValues)
            uri?.let {
                resolver.openOutputStream(it)?.use { outputStream ->
                    outputStream.write(csvContent.toByteArray())
                    outputStream.flush()
                }
                Toast.makeText(this, "CSV berhasil disimpan di folder Downloads", Toast.LENGTH_LONG).show()
            } ?: run {
                Toast.makeText(this, "Gagal menyimpan file", Toast.LENGTH_SHORT).show()
            }
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(this, "Error saat menyimpan CSV", Toast.LENGTH_SHORT).show()
        }
    }

    // Hitung total transaksi
    private fun calculateTotalTransaksi(laporanList: List<Payment>): Double {
        var total = 0.0
        for (laporan in laporanList) {
            total += laporan.total_bayar
        }
        return total
    }

    // Format tanggal
    private fun formatTanggal(date: Date): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return sdf.format(date)
    }
}


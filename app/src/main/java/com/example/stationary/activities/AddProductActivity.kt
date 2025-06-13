package com.example.stationary.activities

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.stationary.R
import com.example.stationary.database.DatabaseHelper
import com.example.stationary.models.Product
import com.google.android.material.textfield.TextInputEditText

class AddProductActivity : AppCompatActivity() {
    private lateinit var db: DatabaseHelper
    private lateinit var txtFromNamaProduk: TextInputEditText
    private lateinit var txtFromHargaProduk: TextInputEditText
    private lateinit var txtFromStokProduk: TextInputEditText
    private lateinit var btnTambahProduk: Button
    private var imageUri: Uri? = null
    private lateinit var pickImageLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)

        db = DatabaseHelper(this)

        txtFromNamaProduk = findViewById(R.id.txtFromNamaProduk)
        txtFromHargaProduk = findViewById(R.id.txtFromHargaProduk)
        txtFromStokProduk = findViewById(R.id.txtFromStokProduk)
        btnTambahProduk = findViewById(R.id.btnTambahProduk)

        // Tombol kembali
        val backIcon: ImageView = findViewById(R.id.ic_back)
        backIcon.setOnClickListener {
            handleBackButton()
        }

        // Tambah produk
        btnTambahProduk.setOnClickListener {
            addProduct()
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        pickImageLauncher.launch(intent)
    }

    private fun addProduct() {
        val nama = txtFromNamaProduk.text.toString()
        val harga = txtFromHargaProduk.text.toString().toDoubleOrNull()
        val stok = txtFromStokProduk.text.toString().toIntOrNull()

        if (nama.isNotEmpty() && harga != null && stok != null) {
            val product = Product(nama = nama, harga = harga, stok = stok)
            val result = db.addProduct(product)

            if (result > 0) {
                Toast.makeText(this, "Produk berhasil ditambahkan", Toast.LENGTH_SHORT).show()
                setResult(RESULT_OK)
                finish()
            } else {
                Toast.makeText(this, "Gagal menambahkan produk", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Isi semua data dengan benar", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleBackButton() {
        if (isFormDirty()) {
            showUnsavedChangesDialog()
        } else {
            navigateToProductList()
        }
    }

    private fun isFormDirty(): Boolean {
        return txtFromNamaProduk.text.toString().isNotEmpty() ||
                txtFromHargaProduk.text.toString().isNotEmpty() ||
                txtFromStokProduk.text.toString().isNotEmpty()
    }

    private fun showUnsavedChangesDialog() {
        AlertDialog.Builder(this)
            .setTitle("Konfirmasi")
            .setMessage("Apakah Anda yakin kembali? Penambahan belum tersimpan.")
            .setPositiveButton("Ya, Buang Perubahan") { _, _ -> navigateToProductList() }
            .setNegativeButton("Lanjut Mengedit", null)
            .show()
    }

    private fun navigateToProductList() {
        val intent = Intent(this, ProductListActivity::class.java)
        startActivity(intent)
        finish()
    }
}

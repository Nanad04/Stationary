package com.example.stationary.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatActivity.RESULT_OK
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stationary.R
import com.example.stationary.adapters.ProductAdapter
import com.example.stationary.database.DatabaseHelper
import com.example.stationary.models.Product

class ProductListActivity : AppCompatActivity(), ProductAdapter.OnItemClickListener {

    // Deklarasi variabel untuk elemen UI dan DatabaseHelper
    private lateinit var productsRecyclerView: RecyclerView
    private lateinit var productAdapter: ProductAdapter
    private lateinit var db: DatabaseHelper
    private lateinit var icBack: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)

        // Inisialisasi elemen UI
        icBack = findViewById(R.id.ic_back)
        productsRecyclerView = findViewById(R.id.recyclerViewProducts)
        productsRecyclerView.layoutManager = LinearLayoutManager(this)

        // Inisialisasi DatabaseHelper
        db = DatabaseHelper(this)

        // Listener untuk tombol tambah produk
        val btnAddProduct: Button = findViewById(R.id.btnAddProduct)
        btnAddProduct.setOnClickListener {
            val intent = Intent(this, AddProductActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_ADD_PRODUCT)
        }

        // Memuat data produk
        loadProducts()

        // Listener untuk tombol kembali
        icBack.setOnClickListener {
            finish()
        }
    }

    // Fungsi untuk memuat data produk dari database dan mengatur adapter RecyclerView
    private fun loadProducts() {
        val productList = db.getAllProducts()
        productAdapter = ProductAdapter(productList, this)
        productsRecyclerView.adapter = productAdapter
    }

    // Fungsi yang dipanggil saat item produk diklik
    override fun onItemClick(product: Product) {
        val intent = Intent(this, EditProductActivity::class.java).apply {
            putExtra("PRODUCT_ID", product.id)
            putExtra("PRODUCT_NAME", product.nama)
            putExtra("PRODUCT_PRICE", product.harga)
            putExtra("PRODUCT_STOCK", product.stok)
        }
        startActivityForResult(intent, REQUEST_CODE_EDIT_PRODUCT)
    }

    // Fungsi yang dipanggil saat kembali dari aktivitas AddProductActivity atau EditProductActivity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                REQUEST_CODE_ADD_PRODUCT, REQUEST_CODE_EDIT_PRODUCT -> loadProducts()
            }
        }
    }

    // Objek pendamping untuk mendefinisikan konstanta kode permintaan
    companion object {
        private const val REQUEST_CODE_ADD_PRODUCT = 1
        private const val REQUEST_CODE_EDIT_PRODUCT = 2
    }
}
package com.example.stationary.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.stationary.R
import com.example.stationary.models.Admin


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Deklarasi variabel untuk elemen UI
        val txtID = findViewById<EditText>(R.id.txtID)
        val txtPassword = findViewById<EditText>(R.id.txtPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        btnLogin.setOnClickListener {
            val Id_admin = txtID.text.toString()
            val password = txtPassword.text.toString()

            if (isValidLogin(Id_admin, password)) {
                // Login berhasil, pindah ke MainActivity
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish() // Tutup LoginActivity
            } else {
                // Login gagal, tampilkan pesan kesalahan
                Toast.makeText(this, "ID atau password salah", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isValidLogin(id_admin: String, password: String): Boolean {
        // Deklarasikan pasangan id_admin dan password yang valid
        val validUsers = listOf(
            Admin("Admin", "adminbeban123", "Admin")
        )

        // Periksa apakah ID dan password cocok dengan salah satu pasangan yang valid
        return validUsers.any { it.id_admin == id_admin && it.password == password }
    }
}
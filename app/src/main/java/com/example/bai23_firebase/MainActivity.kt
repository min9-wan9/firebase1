package com.example.bai23_firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnInsertData = findViewById<Button>(R.id.btnInsertDaTa)
        val btnFetchtData = findViewById<Button>(R.id.btnFetchData)

        //1. code nút InsertionData
        btnInsertData.setOnClickListener {
            val intent = Intent(this, InsertionActivity::class.java)
            startActivity(intent)
        }
        //2. code nút FetchingData
        btnFetchtData.setOnClickListener {
            val intent = Intent(this, FetchingActivity::class.java)
            startActivity(intent)
        }
    }
}
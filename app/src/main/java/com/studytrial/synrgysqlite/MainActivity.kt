package com.studytrial.synrgysqlite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        fetchData()
    }

    fun fetchData() {
        val dbHandler = DatabaseHandler(this)
        // bisa juga dengan val listStudent = dbHandler.viewStudent() --> langsung array
        val listItem = arrayListOf<Inventory>() //--> membuat wadah baru
        listItem.addAll(dbHandler.viewInventory()) // -> menambahkan data ke wadah di liststudent
        val adapter = RecyclerAdapter(listItem)
        rv_layout.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_layout.adapter = adapter
    }
}
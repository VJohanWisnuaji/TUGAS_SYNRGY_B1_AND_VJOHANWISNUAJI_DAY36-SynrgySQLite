package com.studytrial.synrgysqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add.*

class AddActivity : AppCompatActivity() {
    private lateinit var db: DatabaseHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        db = DatabaseHandler(this)

        btn_add.setOnClickListener {
            val inventory = Inventory(null, et_nama.text.toString(), et_jumlah.text.toString().toInt())
            val rowInserted = db.insertInventory(inventory)

            if(rowInserted!! > 0){
                Toast.makeText(this, "Data sukses disimpan", Toast.LENGTH_SHORT).show()
            } else{
                Toast.makeText(this, "Data gagal disimpan", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
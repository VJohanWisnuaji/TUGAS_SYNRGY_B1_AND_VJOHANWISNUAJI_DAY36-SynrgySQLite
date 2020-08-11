package com.studytrial.synrgysqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_update.*

class UpdateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        val dbHandler = DatabaseHandler(this)

        val inventory = intent.getParcelableExtra<Inventory>("inventory")

        supportActionBar?.title = "Ubah data ${inventory?.name}"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        et_nama_update.setText(inventory?.name)
        et_jumlahl_update.setText(inventory!!.Jumlah)

        btn_save.setOnClickListener {
            inventory?.name = et_nama_update.text.toString()
            inventory?.Jumlah = et_jumlahl_update.text.toString().toInt()

            inventory?.let {
                if(dbHandler.updateInventory(it) != 0){
                    Toast.makeText(this, "Data terupdate", Toast.LENGTH_SHORT).show()
                    finish()
                } else{
                    Toast.makeText(this, "Gagal terupdate", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}
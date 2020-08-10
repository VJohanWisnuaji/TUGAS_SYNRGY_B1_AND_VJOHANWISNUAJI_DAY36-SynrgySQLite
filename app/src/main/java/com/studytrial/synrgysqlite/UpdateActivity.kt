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

        val student = intent.getParcelableExtra<Student>("student")

        supportActionBar?.title = "Ubah data ${student?.name}"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        et_nama_update.setText(student?.name)
        et_email_update.setText(student?.email)

        btn_save.setOnClickListener {
            student?.name = et_nama_update.text.toString()
            student?.email = et_email_update.text.toString()

            student?.let {
                if(dbHandler.updateStudent(it) != 0){
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
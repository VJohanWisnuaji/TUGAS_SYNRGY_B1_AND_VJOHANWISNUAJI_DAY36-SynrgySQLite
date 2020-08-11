package com.studytrial.synrgysqlite

import android.app.AlertDialog
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.inventory_data.view.*

class RecyclerAdapter(val listInventory: ArrayList<Inventory>) :
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.inventory_data, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return listInventory.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.tv_isi_id.setText(listInventory[position].id.toString())
        holder.itemView.tv_isi_nama.setText(listInventory[position].name)
        holder.itemView.tv_isi_jumlah.setText(listInventory[position].Jumlah.toString())

        holder.itemView.iv_edit.setOnClickListener {
            val intentUpdateActivity = Intent(it.context, UpdateActivity::class.java)
            intentUpdateActivity.putExtra("inventory", listInventory[position])
            it.context.startActivity(intentUpdateActivity)
        }

        holder.itemView.iv_delete.setOnClickListener {
            AlertDialog.Builder(it.context).setPositiveButton("Yes") { p0, p1 ->
                val dbHelper = DatabaseHandler(holder.itemView.context)

                if (dbHelper.deleteInventory(listInventory[position]) != 0) {
                    Toast.makeText(
                        it.context,
                        "Data ${listInventory[position].name} berhasil dihapus",
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(it.context, MainActivity::class.java)
                    startActivity(it.context, intent, null)
                } else {
                    Toast.makeText(it.context, "Data ${listInventory[position].name} tidak berhasil dihapus", Toast.LENGTH_SHORT).show()
                }
            }.setNegativeButton("No"){
                    p0,p1 -> p0.dismiss()
            }.setMessage("Apakah anda yakin ingin menghapus data inventory?").setTitle("Peringatan Hapus Data").create().show()
        }
    }

}
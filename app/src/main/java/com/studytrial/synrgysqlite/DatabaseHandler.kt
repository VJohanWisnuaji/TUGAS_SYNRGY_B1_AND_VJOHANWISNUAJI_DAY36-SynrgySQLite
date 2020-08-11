package com.studytrial.synrgysqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHandler(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "InventoryDatabase"
        private val TABLE_INVENTORY = "InventoryTable"
        private val KEY_ID = "id"
        private val KEY_NAME = "name"
        private val KEY_JUMLAH = "jumlah"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_INVENTORY_TABLE =
            ("CREATE TABLE $TABLE_INVENTORY ($KEY_ID INTEGER PRIMARY KEY, $KEY_NAME TEXT,$KEY_JUMLAH TEXT)")
        db?.execSQL(CREATE_INVENTORY_TABLE)

    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        val queryDeteleTable = "DROP TABLE IF EXISTS $TABLE_INVENTORY"
        db?.execSQL(queryDeteleTable)
        onCreate(db)
    }

    fun insertInventory(inventory: Inventory): Long? {
        val db = this.writableDatabase
        val contenValues = ContentValues()
        contenValues.put(KEY_NAME, inventory.name)
        contenValues.put(KEY_JUMLAH, inventory.Jumlah)

        //Mengembalikan jumlah baris yang berhasil diinputkan / disimpan
        val numberRecordSaved = db?.insert(TABLE_INVENTORY, null, contenValues)
        db?.close()

        return numberRecordSaved
    }


    fun viewInventory(): ArrayList<Inventory> {
        val listInventory = ArrayList<Inventory>()
        val selectAllInventoryQuery = "SELECT * FROM $TABLE_INVENTORY"
        val db = this.readableDatabase
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery(selectAllInventoryQuery, null, null)
        } catch (e: SQLException) {
            db.execSQL(selectAllInventoryQuery)
            return ArrayList()
        }

        var id: Int
        var nama: String
        var Jumlah: Int

        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                nama = cursor.getString(cursor.getColumnIndex(KEY_NAME))
                Jumlah = cursor.getInt(cursor.getColumnIndex(KEY_JUMLAH))
                val inventory = Inventory(id, nama, Jumlah)
                listInventory.add(inventory)
            } while (cursor.moveToNext())
        }

        return listInventory
    }

    fun updateInventory(inventory: Inventory): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, inventory.id)
        contentValues.put(KEY_NAME, inventory.name)
        contentValues.put(KEY_JUMLAH, inventory.Jumlah)

        val isSuccess = db.update(
            TABLE_INVENTORY,
            contentValues,
            "$KEY_ID = ${inventory.id}" ,
            null)

        db.close()
        return isSuccess
    }

    fun deleteInventory(inventory: Inventory): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, inventory.id) // Id Student
        // Menghapus Baris
        val success = db.delete(TABLE_INVENTORY, "$KEY_ID = ${inventory.id}", null)

        //Argumen kedua adalah String yang berisi nullColumnHack
        db.close() // Menutup koneksi ke database
        return success
    }
}
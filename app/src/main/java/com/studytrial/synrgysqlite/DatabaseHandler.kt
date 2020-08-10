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
        private val DATABASE_NAME = "StudentDatabase"
        private val TABLE_STUDENT = "StudentTable"
        private val KEY_ID = "id"
        private val KEY_NAME = "name"
        private val KEY_EMAIL = "email"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_STUDENT_TABLE =
            ("CREATE TABLE $TABLE_STUDENT ($KEY_ID INTEGER PRIMARY KEY, $KEY_NAME TEXT,$KEY_EMAIL TEXT)")
        db?.execSQL(CREATE_STUDENT_TABLE)

    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        val queryDeteleTable = "DROP TABLE IF EXISTS $TABLE_STUDENT"
        db?.execSQL(queryDeteleTable)
        onCreate(db)
    }

    fun insertStudent(student: Student): Long? {
        val db = this.writableDatabase
        val contenValues = ContentValues()
        contenValues.put(KEY_NAME, student.name)
        contenValues.put(KEY_EMAIL, student.email)

        //Mengembalikan jumlah baris yang berhasil diinputkan / disimpan
        val numberRecordSaved = db?.insert(TABLE_STUDENT, null, contenValues)
        db?.close()

        return numberRecordSaved
    }


    fun viewStudent(): ArrayList<Student> {
        val listStudent = ArrayList<Student>()
        val selectAllStudentQuery = "SELECT * FROM $TABLE_STUDENT"
        val db = this.readableDatabase
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery(selectAllStudentQuery, null)
        } catch (e: SQLException) {
            db.execSQL(selectAllStudentQuery)
            return ArrayList()
        }

        var id: Int
        var nama: String
        var email: String

        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                nama = cursor.getString(cursor.getColumnIndex(KEY_NAME))
                email = cursor.getString(cursor.getColumnIndex(KEY_EMAIL))
                val student = Student(id, nama, email)
                listStudent.add(student)
            } while (cursor.moveToNext())
        }

        return listStudent
    }

    fun updateStudent(student: Student): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, student.id)
        contentValues.put(KEY_NAME, student.name) // Nama Student
        contentValues.put(KEY_EMAIL, student.email) // Email Student

        val isSuccess = db.update(
            TABLE_STUDENT,
            contentValues,
            "$KEY_ID = ${student.id}" ,
            null)

        db.close()
        return isSuccess
    }

    fun deleteStudent(student: Student): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, student.id) // Id Student
        // Menghapus Baris
        val success = db.delete(TABLE_STUDENT, "$KEY_ID = ${student.id}", null)

        //Argumen kedua adalah String yang berisi nullColumnHack
        db.close() // Menutup koneksi ke database
        return success
    }
}
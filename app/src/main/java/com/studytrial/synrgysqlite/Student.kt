package com.studytrial.synrgysqlite

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Student(var id: Int?, var name: String, var email: String) : Parcelable
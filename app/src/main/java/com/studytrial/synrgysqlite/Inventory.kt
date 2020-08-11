package com.studytrial.synrgysqlite

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Inventory(var id: Int?, var name: String, var Jumlah: Int) : Parcelable
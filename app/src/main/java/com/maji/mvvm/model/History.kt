package com.maji.mvvm.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "history", foreignKeys = [ForeignKey(entity = Subject::class, parentColumns = ["id"], childColumns = ["hid"])])
class History(
        @PrimaryKey(autoGenerate = true)
        val id: Int,
        @ColumnInfo(name = "time")
        var time: String,
        @ColumnInfo(name = "hid")
        var hid: String
)
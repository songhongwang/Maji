package com.maji.mvvm.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "subject")
class Subject(
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    @ColumnInfo(name = "current_user_url")
    var current_user_url:String,
    @ColumnInfo(name = "auth_html_url")
    var current_user_authorizations_html_url:String?,
    @ColumnInfo(name = "auth_url")
    var authorizations_url:String?,
    @ColumnInfo(name = "search_url")
    var code_search_url:String?,
    @ColumnInfo(name = "create_time")
    var create_time:String?

)
package com.example.simple.database

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "url_table")
data class UrlBar(

        @PrimaryKey(autoGenerate = true)
        val urlId: Long= 0L,
        @ColumnInfo(name = "url_link")
        val url_link: String = "",
        @ColumnInfo(name = "url_name")
        val url_title: String = "",
        /*@ColumnInfo(name = "url_image_name")
        val url_image_name: Bitmap?*/

)



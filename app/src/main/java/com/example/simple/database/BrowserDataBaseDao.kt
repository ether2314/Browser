package com.example.simple.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface BrowserDataBaseDao {
    @Insert
    suspend fun insert(url: UrlBar)

    @Update
    suspend fun update(url: UrlBar)

    @Query("DELETE FROM url_table")
    suspend fun clear()

    @Query("SELECT * FROM url_table ORDER BY urlId DESC LIMIT 1")
    suspend fun geturl(): UrlBar?


    @Query("SELECT * FROM url_table ORDER BY urlId DESC")
    fun getALLUrls():LiveData<List<UrlBar>>
}
package com.maji.mvvm.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.maji.mvvm.model.Subject

@Dao
interface SubjectDao {

    @Insert
    fun insertSubject(subject: Subject?)

    @Query("select * from subject limit 20")
    fun getSubject():LiveData<List<Subject?>?>?

}
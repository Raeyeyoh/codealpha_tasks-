package com.example.flashcard_quiz

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface catagoreyDao {
    @Insert
    fun insertcategory(category:Categorytable)
    @Update
    fun updatecategory(category: Categorytable)
    @Delete
    fun deletecategory(category: Categorytable)
    @Query("SELECT * from categorytable")
    fun getallcategory(): LiveData<List<Categorytable>>
}
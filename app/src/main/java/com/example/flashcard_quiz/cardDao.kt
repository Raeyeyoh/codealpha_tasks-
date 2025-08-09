package com.example.flashcard_quiz
import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao

@Dao
interface cardDao {
    @Insert
    suspend fun insert(card:Cardtable)
    @Update
    fun update(card1: Cardtable)
    @Delete
    fun delete(card1: Cardtable)
    @Query("SELECT  * from cardtable")
    suspend fun getall():List<Cardtable>
    @Query("SELECT * FROM cardtable WHERE categoryId = :id")
    suspend  fun getCardsByCategory(id: Long): List<Cardtable>
    @Query("SELECT * FROM cardtable WHERE cardid = :id")
    suspend  fun getCardById(id: Long): Cardtable?


}

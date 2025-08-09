package com.example.flashcard_quiz

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
data class Categorytable(
    @PrimaryKey(autoGenerate = true)
    var categoryid:Long=0L,
    var name:String="",
)

package com.example.flashcard_quiz

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = Categorytable::class,
        parentColumns = ["categoryid"],
        childColumns = ["categoryId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Cardtable(
    @PrimaryKey(autoGenerate = true)
    var cardid:Long=0L,
    var question:String="",
    var answer:String="",
    @ColumnInfo(index = true)
  var categoryId: Long=0L

)

package com.example.flashcard_quiz
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(entities =  [Cardtable::class,Categorytable::class], version = 1, exportSchema = false)
abstract class flashcardDatabase:RoomDatabase(){
   abstract val carddao:cardDao
   abstract val cateDao:catagoreyDao
companion object {
   @Volatile
   private var Instance: flashcardDatabase? = null
   fun getInstance(context: Context): flashcardDatabase {
      synchronized(this) {
         var instance = Instance
         if (instance == null) {
          instance=  Room.databaseBuilder(
               context.applicationContext,
              flashcardDatabase::class.java,
               "flashcard_database"
            ).build()
            Instance = instance
         }
         return instance
      }
   }
}
}

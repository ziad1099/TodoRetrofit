package com.zezo.todoretrofit.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlin.concurrent.Volatile

@Database(entities = [Todo::class], version = 1, exportSchema = false)
abstract class todoDatabase:RoomDatabase() {
    abstract fun todoDao():TodoDao

    companion object{
        @Volatile
        private var INSTANCE : todoDatabase?=null
        fun getDataBase(context: Context):todoDatabase{
            val tempInstance=INSTANCE
            if (tempInstance!=null){
                return tempInstance
            }
             synchronized(this){
                val instance=Room.databaseBuilder(
                    context.applicationContext,
                    todoDatabase::class.java,
                    "todo_Database"
                ).build()
                 INSTANCE=instance
                return instance
             }
        }

    }
}
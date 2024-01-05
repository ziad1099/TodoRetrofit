package com.zezo.todoretrofit.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addtodo(todo: Todo)
    @Query("Select * From todos_data Order by id ASC")
    fun readAlldata():LiveData<List<Todo>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(todos:List<Todo>)
}
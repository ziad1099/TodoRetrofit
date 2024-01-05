package com.zezo.todoretrofit.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("todos_data")
data class Todo(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val userId: Int,
    val title: String,
    val completed: Boolean
)
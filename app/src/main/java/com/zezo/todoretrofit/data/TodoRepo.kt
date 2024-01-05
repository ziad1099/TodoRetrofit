package com.zezo.todoretrofit.data

import androidx.lifecycle.LiveData

class TodoRepo(private val todoDao:TodoDao) {
    val readAllData : LiveData<List<Todo>> = todoDao.readAlldata()
    suspend fun addTodo(todo:Todo){
        todoDao.addtodo(todo)
    }
    suspend fun insertAll(todos:List<Todo>){
        todoDao.insertAll(todos)
    }

}
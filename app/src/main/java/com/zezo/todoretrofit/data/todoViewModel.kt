package com.zezo.todoretrofit.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class todoViewModel(application: Application):AndroidViewModel(application) {
    private val readAllData:LiveData<List<Todo>>
    private val repo:TodoRepo
    init {
        val todoDao=todoDatabase.getDataBase(application).todoDao()
        repo= TodoRepo(todoDao)
        readAllData=repo.readAllData
    }
    fun addTodo(todo: Todo){
        viewModelScope.launch(Dispatchers.IO) {
            repo.addTodo(todo)
        }
    }fun insertAll(todos: List<Todo>){
        viewModelScope.launch(Dispatchers.IO) {
            repo.insertAll(todos)
        }
    }

}
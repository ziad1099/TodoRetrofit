package com.zezo.todoretrofit

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.zezo.todoretrofit.data.Todo
import com.zezo.todoretrofit.data.TodoRepo
import com.zezo.todoretrofit.data.todoDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okio.IOException


class MainActivityViewModel(application: Application) : AndroidViewModel(application) {
    val readAllData: LiveData<List<Todo>>
    private val repo: TodoRepo

    init {
        val todoDao = todoDatabase.getDataBase(application).todoDao()
        repo = TodoRepo(todoDao)
        readAllData = repo.readAllData
    }

//    fun fetchDatafromApi() {
//        viewModelScope.launch {
//            val response = try {
//                RetrofitInstance.api.getTodo()
//            } catch (e: IOException) {
//                return@launch
//            }
//
//        }
//    }

    fun addTodo(todo: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.addTodo(todo)
        }
    }

    fun insertAll(todos: List<Todo>) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.insertAll(todos)
        }
    }

}
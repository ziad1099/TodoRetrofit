package com.zezo.todoretrofit

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zezo.todoretrofit.data.Todo
import com.zezo.todoretrofit.data.TodoRepo
import com.zezo.todoretrofit.data.todoDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okio.IOException
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    application: Application,
    name:String
) : ViewModel() {
    val readAllData: LiveData<List<Todo>>
    private val repo: TodoRepo

    init {
        val todoDao = todoDatabase.getDataBase(application).todoDao()
        repo = TodoRepo(todoDao)
        readAllData = repo.readAllData
        Log.d("ViewModelHilt","hello $name")
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
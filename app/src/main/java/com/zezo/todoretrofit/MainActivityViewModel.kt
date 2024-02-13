package com.zezo.todoretrofit

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zezo.todoretrofit.data.Todo
import com.zezo.todoretrofit.data.TodoRepo
import com.zezo.todoretrofit.data.todoDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    @ApplicationContext applicationContext: Context
) : ViewModel() {
    val readAllData: LiveData<List<Todo>>
    private val repo: TodoRepo
    val TAG: String = "ViewModelTAG"
    @Inject lateinit var name:String
    init {
        val todoDao = todoDatabase.getDataBase(applicationContext).todoDao()
        repo = TodoRepo(todoDao)
        readAllData = repo.readAllData
//        Log.d(TAG, name)
    }


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
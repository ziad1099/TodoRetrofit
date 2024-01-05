package com.zezo.todoretrofit

import com.zezo.todoretrofit.data.Todo
import retrofit2.Response
import retrofit2.http.GET

interface TodoApi {
    @GET("/todos")
    suspend fun getTodo():Response<List<Todo>>
}
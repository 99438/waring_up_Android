package com.example.todolist


import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.Path
import com.example.todolist.TodoItem
import retrofit2.Call
import retrofit2.http.GET

interface TodoApi {
    @POST("todos") // POST 请求用于增加待办事项
    fun addTodoItem(@Body todoItem: TodoItem): Call<TodoItem>

    @GET("todos") // GET 请求用于获取待办事项列表
    fun getTodoItems(): Call<List<TodoItem>>

    @DELETE("todos/{id}") // DELETE 请求用于删除待办事项
    fun deleteTodoItem(@Path("id") id: String?):Call<Void>
}
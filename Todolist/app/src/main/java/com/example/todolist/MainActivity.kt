package com.example.todolist

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call

import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : Activity() {

    private lateinit var todoAdapter: TodoAdapter
    private val todoList = mutableListOf<TodoItem>()
    private lateinit var todoApi: TodoApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView) // 找到对应的 RecyclerView
        val addButton: Button = findViewById(R.id.addButton) // 找到添加按钮
        val editText: EditText = findViewById(R.id.editText) // 找到输入框

        val retrofit = Retrofit.Builder()
            .baseUrl("https://t.com/todos")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        todoApi = retrofit.create(TodoApi::class.java)

        todoAdapter = TodoAdapter(todoList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = todoAdapter

        addButton.setOnClickListener {
            val todoText = editText.text.toString()
            val todoItem = TodoItem(todoText)
            val call = todoApi.addTodoItem(todoItem)
            call.enqueue(object : Callback<TodoItem> {
                override fun onResponse(call: Call<TodoItem>, response: Response<TodoItem>) {
                    if (response.isSuccessful) {
                        if (todoText.isNotEmpty()) {
                            todoList.add(TodoItem(todoText))
                            todoAdapter.notifyItemInserted(todoList.size - 1)
                            editText.text.clear()
                        }
                        val addedTodoItem = response.body()
                    } else {
                        // 处理错误情况
                    }
                }

                override fun onFailure(call: Call<TodoItem>, t: Throwable) {
                    // 处理失败情况
                }
            })

        }
    }
}
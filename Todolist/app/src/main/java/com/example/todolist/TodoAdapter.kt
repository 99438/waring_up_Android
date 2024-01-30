package com.example.todolist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TodoAdapter(private val items: MutableList<TodoItem>) :
    RecyclerView.Adapter<TodoAdapter.ViewHolder>() {
    private lateinit var todoApi: TodoApi

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.textView)
        val deleteButton: Button = view.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.todo_item_delete, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val item = items[position]
        holder.textView.text = item.text
        holder.deleteButton.setOnClickListener {
            val call = todoApi.deleteTodoItem(item.text)
            call.enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        items.removeAt(position)
                        notifyItemRemoved(position)
                    } else {
                        // 处理错误情况
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    // 处理失败情况
                }
            })
        }
    }

    override fun getItemCount() = items.size
}
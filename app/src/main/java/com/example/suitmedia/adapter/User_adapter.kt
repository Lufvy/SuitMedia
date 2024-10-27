package com.example.suitmedia.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.ImageLoader
import com.bumptech.glide.Glide
import com.example.suitmedia.R
import com.example.suitmedia.model.User_model


class User_adapter(private var userList: ArrayList<User_model>, private val listener: (String, String) -> Unit): RecyclerView.Adapter<User_adapter.Showlist>() {


    class Showlist(userview: View): RecyclerView.ViewHolder(userview){
        val avatar: ImageView = userview.findViewById(R.id.imageView_rv)
        val username: TextView= userview.findViewById(R.id.username_rv)
        val email: TextView= userview.findViewById(R.id.email_rv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Showlist {
        val itemView= LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_layout, parent,false)
        return Showlist(itemView)
    }

    override fun getItemCount(): Int {
        return userList.count()
    }

    override fun onBindViewHolder(holder: Showlist, position: Int) {
        val curr_user = userList[position]
        Glide.with(holder.itemView.context).load(curr_user.avatar).into(holder.avatar)
        holder.email.text=curr_user.email
        holder.username.text= "${curr_user.firstname} ${curr_user.lastname}"

        holder.itemView.setOnClickListener{
            listener(curr_user.firstname, curr_user.lastname)
        }
    }
}
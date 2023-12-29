package com.example.electrigo.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.electrigo.Model.Comment
import com.example.electrigo.Model.Get.Poost
import com.example.electrigo.Model.Get.Postt
import com.example.electrigo.R
import com.example.electrigo.Model.Get.Root

class MyCommentAdapter(private val onItemClick: (Comment) -> Unit = {}) :
    RecyclerView.Adapter<MyCommentAdapter.MyViewHolder>() {

    private var myList = emptyList<Comment>()

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val c_comment_author: TextView = itemView.findViewById(R.id.c_comment_author)
        val c_comment_message: TextView = itemView.findViewById(R.id.c_comment_message)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {

                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.single_item_comment, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return myList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val post = myList[position]

        holder.c_comment_author.text = post.commenter
        holder.c_comment_message.text = post.text





    }

    fun setData(newList: List<Comment>) {
        myList = newList
        notifyDataSetChanged()
    }
}

package com.example.electrigo.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.electrigo.Model.Get.Poost
import com.example.electrigo.Model.Get.Postt
import com.example.electrigo.R
import com.example.electrigo.Model.Get.Root

class MyPostAdapter(private val onItemClick: (Postt) -> Unit = {},
                    private val onLikeClick: (Postt) -> Unit = {},
                    private val onDislikeClick: (Postt) -> Unit = {},
                    private val onCommentClick: (Postt) -> Unit = {}) :
    RecyclerView.Adapter<MyPostAdapter.MyViewHolder>() {

    private var myList = emptyList<Postt>()

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val blog_content: TextView = itemView.findViewById(R.id.blog_content)
        val blog_user_name: TextView = itemView.findViewById(R.id.blog_user_name)
        val blog_username: TextView = itemView.findViewById(R.id.blog_username)
        //like
        val blog_like_btn: ImageView = itemView.findViewById(R.id.blog_like_btn)
        val blog_like_count: TextView = itemView.findViewById(R.id.blog_like_count)
        //dislike
        val blog_dislike_btn: ImageView = itemView.findViewById(R.id.blog_dislike_btn)
        val blog_dislike_count: TextView = itemView.findViewById(R.id.blog_dislike_count)

        val blog_comment_icon: ImageView = itemView.findViewById(R.id.blog_comment_icon)


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
            LayoutInflater.from(parent.context).inflate(R.layout.single_item_post, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return myList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val post = myList[position]

        holder.blog_user_name.text = post.author
        holder.blog_content.text = post.content
        holder.blog_username.text = post.title
        holder.blog_like_count.text=post.like.toString()
        holder.blog_dislike_count.text=post.dislike.toString()

        holder.blog_like_btn.setOnClickListener {
            onLikeClick(post)
        }

        holder.blog_dislike_btn.setOnClickListener {
            onDislikeClick(post)
        }

        holder.blog_comment_icon.setOnClickListener {
            onCommentClick(post)
        }


    }

    fun setData(newList: List<Postt>) {
        myList = newList
        notifyDataSetChanged()
    }
}

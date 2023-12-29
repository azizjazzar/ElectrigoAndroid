package com.example.electrigo.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.electrigo.databinding.FragmentCommentBinding
import com.example.electrigo.repository.Repository
import com.example.retrofitdemo.MainViewModel
import com.example.retrofittest.MainViewModelFactory
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.electrigo.Adapter.MyCommentAdapter
import com.example.electrigo.Model.CommentBody
import com.example.electrigo.Service.RetrofitInstancePost

class CommentFragment : Fragment() {
    private lateinit var binding: FragmentCommentBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var commentAdapter: MyCommentAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCommentBinding.inflate(inflater, container, false)

        val postId = arguments?.getString("postId")
        if (postId != null) {
            val apiService = RetrofitInstancePost.api
            val repository = Repository(apiService)
            val viewModelFactory = MainViewModelFactory(repository)
            viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)


            commentAdapter = MyCommentAdapter { comment ->

            }


            binding.commentRecyclerView.layoutManager = LinearLayoutManager(context)
            binding.commentRecyclerView.adapter = commentAdapter

            viewModel.comments.observe(viewLifecycleOwner, Observer { comments ->

                commentAdapter.setData(comments)
            })

            viewModel.fetchCommentsForPost(postId)


            binding.commentButton.setOnClickListener {
                val commentText = binding.commentEditText.text.toString().trim()


                if (commentText.isNotEmpty()) {
                    val postId = arguments?.getString("postId")
                    if (postId != null) {
                        val comment = CommentBody(postId, "1", commentText)
                        viewModel.addComment(comment)

                        binding.commentEditText.text.clear()
                        val apiService = RetrofitInstancePost.api
                        val repository = Repository(apiService)
                        val viewModelFactory = MainViewModelFactory(repository)
                        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

                        commentAdapter = MyCommentAdapter { comment ->
                        }

                        binding.commentRecyclerView.layoutManager = LinearLayoutManager(context)
                        binding.commentRecyclerView.adapter = commentAdapter

                        viewModel.comments.observe(viewLifecycleOwner, Observer { comments ->

                            commentAdapter.setData(comments)
                        })

                        viewModel.fetchCommentsForPost(postId)

                    }
                } else {

                }
            }
        }



        return binding.root
    }
}

package com.example.electrigo.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.electrigo.Adapter.MyPostAdapter
import com.example.electrigo.R
import com.example.electrigo.Service.RetrofitInstancePost
import com.example.electrigo.databinding.FragementPostBinding
import com.example.electrigo.repository.Repository
import com.example.retrofitdemo.MainViewModel
import com.example.retrofitdemo.api.SimpleApi
import com.example.retrofittest.MainViewModelFactory

class PostFragment : Fragment(R.layout.fragement_post){
    private lateinit var binding: FragementPostBinding
    private lateinit var viewModel: MainViewModel
    private val apiService: SimpleApi = RetrofitInstancePost.api
    private val postAdapter: MyPostAdapter by lazy {
        MyPostAdapter(
            onLikeClick = { post ->
                Log.d("PostFragment", "Liked post with ID: ${post._id}")
                viewModel.likePost(post._id)
                Toast.makeText(requireContext(), "Post liked!", Toast.LENGTH_SHORT).show()
                refreshPosts()
            },
            onDislikeClick = { post ->
                Log.d("PostFragment", "Disliked post with ID: ${post._id}")
                viewModel.dislikePost(post._id)
                Toast.makeText(requireContext(), "Post disliked!", Toast.LENGTH_SHORT).show()
                refreshPosts()
            },
            onCommentClick = { post ->

                val bundle = Bundle()
                bundle.putString("postId", post._id)

                val fragmentmanager = requireFragmentManager()
                val currentFragment = this

                val commentFragment = CommentFragment()
                commentFragment.arguments = bundle

                fragmentmanager.beginTransaction()
                    .remove(currentFragment)
                    .replace(R.id.fragmentContainer, commentFragment)
                    .addToBackStack(null)
                    .commit()

            }
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = FragementPostBinding.inflate(layoutInflater)
        val fragmentmanager = requireFragmentManager()

        binding.rvPost.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding.rvPost.adapter = postAdapter

        val repository = Repository(apiService)
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        viewModel.posts.observe(viewLifecycleOwner, Observer {
            postAdapter.setData(it)
        })

        binding.fab.setOnClickListener {
            val currentFragment = this
            fragmentmanager.beginTransaction()
                .remove(currentFragment)
                .replace(R.id.fragmentContainer, AddPostFragement())
                .addToBackStack(null)
                .commit()        }

        return binding.root
    }

    private fun observePosts() {
        viewModel.posts.observe(viewLifecycleOwner, Observer {
            postAdapter.setData(it)
        })
    }

    private fun refreshPosts() {
        val fragmentmanager = requireFragmentManager()
        val currentFragment = this
        fragmentmanager.beginTransaction()
            .remove(currentFragment)
            .replace(R.id.fragmentContainer, PostFragment())
            .addToBackStack(null)
            .commit()
    }
}

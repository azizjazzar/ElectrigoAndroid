package com.example.electrigo.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.electrigo.databinding.FragementAddPostBinding
import com.example.electrigo.Model.Post
import com.example.electrigo.R
import com.example.electrigo.Service.RetrofitInstancePost
import com.example.electrigo.repository.Repository
import com.example.retrofitdemo.MainViewModel
import com.example.retrofitdemo.api.SimpleApi
import com.example.retrofittest.MainViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class AddPostFragement : Fragment() {
    private lateinit var binding: FragementAddPostBinding
    private lateinit var viewModel: MainViewModel
    private val apiService: SimpleApi = RetrofitInstancePost.api



    companion object {
        const val iduserconnect = "6553fe22539c1e3985881aa2"
        const val REQUEST_CODE_IMAGE = 101
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragementAddPostBinding.inflate(layoutInflater)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.AddPost.setOnClickListener {
            val api = RetrofitInstancePost.api
            val repository = Repository(api)
            viewModel = ViewModelProvider(this, MainViewModelFactory(repository)).get(MainViewModel::class.java)

            val content = binding.addContent.text.toString()
           val message = binding.addMessage.text.toString()
            val author = binding.addAuthor.text.toString()


            val post = Post("111111111111", message, content, author)
            AddPostAPI(post)

            refreshPosts()

        }
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

    private fun AddPostAPI(post: Post) {

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = Repository(apiService).addPost(
                   post
                )

                launch(Dispatchers.Main) {
                    if (response.isSuccessful) {

                    } else {

                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }
}

package com.zezo.todoretrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.zezo.todoretrofit.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import okio.IOException


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var todoAdapter: TodoAdapter
    private val viewModel:MainActivityViewModel by viewModels()
//    private lateinit var viewModel: MainActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        Thread.sleep(3000)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // factory and view model
//        val viewModelFactory = ViewModelFactory(application)
//        viewModel = ViewModelProvider(this, viewModelFactory)[MainActivityViewModel::class.java]
        viewModel
        setupRecyclerView()
        viewModel.readAllData.observe(this,Observer{todo->
            todoAdapter.setData(todo)

        })
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                binding.PB.isVisible = true
                val response = try {
                    RetrofitInstance.api.getTodo()
                } catch (e: IOException) {
                    Toast.makeText(
                        this@MainActivity, "Make sure you access the internet", Toast.LENGTH_LONG
                    ).show()
                    binding.PB.isVisible = false
                    return@repeatOnLifecycle
                }
                /* TODO("why http exception doesn't work ???") */
                if (response.isSuccessful && response.body() != null) {
                    todoAdapter.todos = response.body()!!
                    viewModel.insertAll(response.body()!!)

                } else {
                    Toast.makeText(
                        this@MainActivity, "Response is not successful", Toast.LENGTH_LONG
                    ).show()
                    Log.e("MainActivity", "Response is not successful")
                }
                binding.PB.isVisible = false
            }
        }

    }

    fun setupRecyclerView() = binding.rvTodos.apply {
        todoAdapter = TodoAdapter()
        adapter = todoAdapter
        layoutManager = LinearLayoutManager(this@MainActivity)
    }
}
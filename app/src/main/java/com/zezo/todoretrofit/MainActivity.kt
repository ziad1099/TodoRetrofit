package com.zezo.todoretrofit

import android.app.Application
import android.net.http.HttpException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.zezo.todoretrofit.data.todoViewModel
import com.zezo.todoretrofit.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import okio.IOException
class MyViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
     override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(todoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return todoViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var todoAdapter: TodoAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        Thread.sleep(3000)
        installSplashScreen()
//        val viewModel= ViewModelProvider(this)[todoViewModel::class.java]
          val viewModel: todoViewModel by viewModels {
            MyViewModelFactory(application) // Pass the application instance to the factory
        }

        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                binding.PB.isVisible=true
                val response= try{
                    RetrofitInstance.api.getTodo()
                }catch (e: IOException){
                    Toast.makeText(this@MainActivity
                        ,"MAke sure you access the internet"
                        ,Toast.LENGTH_LONG).show()
                    binding.PB.isVisible=false
                    return@repeatOnLifecycle
                }
                /* TODO("why http exception doesn't work ???") */
                if (response.isSuccessful && response.body()!=null){
                    todoAdapter.todos=response.body()!!
//                    viewModel.insertAll(response.body()!!)

                }else{
                    Toast.makeText(this@MainActivity
                        ,"Response is not successful"
                        ,Toast.LENGTH_LONG).show()
                    Log.e("MainActivity", "Response is not successful" )
                }
                binding.PB.isVisible=false
            }
        }
//                    viewModel.addTodo(todoAdapter.todos[1])
    }

    fun setupRecyclerView()=binding.rvTodos.apply {
        todoAdapter= TodoAdapter()
        adapter=todoAdapter
        layoutManager=LinearLayoutManager(this@MainActivity)
    }
}
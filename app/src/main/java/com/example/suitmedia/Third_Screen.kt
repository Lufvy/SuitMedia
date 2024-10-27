package com.example.suitmedia

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.suitmedia.adapter.User_adapter
import com.example.suitmedia.model.User_model


class Third_Screen : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var back_button: ImageButton
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var fullName: String
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third_screen)
        supportActionBar?.hide()

        back_button = findViewById(R.id.back_button_tc)
        recyclerView = findViewById(R.id.recycler_view)
        swipeRefreshLayout = findViewById(R.id.swipe)
        fullName=""

        back_button.setOnClickListener {
            val intent = Intent()
            if(!fullName.isEmpty()){
                intent.putExtra("SELECTED_USER", fullName)
                setResult(Activity.RESULT_OK, intent)
            }

            finish()
        }

        sharedViewModel = ViewModelProvider(this).get(SharedViewModel::class.java)

        setupRecyclerView()
        setupSwipeRefresh()
        loadUsers()

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1) && !isLoading) {
                    if (sharedViewModel.hasMorePages()) {
                        loadUsers(sharedViewModel.currentPage + 1)
                    }
                }
            }
        })
    }

    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        sharedViewModel.UserList.observe(this, Observer { userList ->
            if (userList != null && userList.isNotEmpty()) {
                recyclerView.visibility = View.VISIBLE
                recyclerView.adapter = User_adapter(userList as ArrayList<User_model>) { firstname, lastname ->
                    fullName = "$firstname $lastname"
                    sharedViewModel.selectUser(fullName)
                }
            } else {
                recyclerView.visibility = View.GONE
            }
        })
    }

    private fun setupSwipeRefresh() {
        swipeRefreshLayout.setOnRefreshListener {
            sharedViewModel.resetPaging()
            loadUsers()
        }
    }

    private fun loadUsers(page: Int = 1) {
        isLoading = true
        sharedViewModel.fetchUsersWithPage(this, page).addOnSuccessListener {
            isLoading = false
            swipeRefreshLayout.isRefreshing = false
        }.addOnFailureListener {
            isLoading = false
            swipeRefreshLayout.isRefreshing = false
        }
    }
}




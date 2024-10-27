package com.example.suitmedia

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.suitmedia.model.User_model
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.TaskCompletionSource
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class SharedViewModel : ViewModel() {
    private var _currentPage = 1
    val currentPage: Int get() = _currentPage

    private val selectedUser = MutableLiveData<String?>()
    val selectedUserName: LiveData<String?> get() = selectedUser

    private val _userList = MutableLiveData<List<User_model>>()
    val UserList: LiveData<List<User_model>> get() = _userList

    fun selectUser(fullName: String) {
        selectedUser.value = fullName
    }

    private var _totalPages = 1
    val totalPages: Int get() = _totalPages

    fun fetchUsersWithPage(context: Context, page: Int) :Task<Boolean>{
        val isdone =TaskCompletionSource<Boolean>()
        val url = "https://reqres.in/api/users?page=$page&per_page=10"

        val stringRequest = object : StringRequest(
            Request.Method.GET, url,
            Response.Listener { response ->
                // Parse the response
                val jsonObject = JSONObject(response)
                _totalPages = jsonObject.getInt("total_pages")
                val userList = mutableListOf<User_model>()

                val dataArray = jsonObject.getJSONArray("data")
                for (i in 0 until dataArray.length()) {
                    val userObject = dataArray.getJSONObject(i)
                    val user = User_model(
                        id = userObject.getInt("id"),
                        email = userObject.getString("email"),
                        firstname = userObject.getString("first_name"),
                        lastname = userObject.getString("last_name"),
                        avatar = userObject.getString("avatar")
                    )
                    userList.add(user)
                }

                _currentPage = page
                _userList.value = userList
                isdone.setResult(true)
            },
            Response.ErrorListener { error ->
            }) {
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Content-Type"] = "application/json"
                return headers
            }
        }

        Volley.newRequestQueue(context).add(stringRequest)
        return isdone.task
    }

    fun resetPaging() {
        _currentPage = 1
    }

    fun hasMorePages(): Boolean {
        return _currentPage < _totalPages
    }
}

package com.example.parse_retrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private val users = mutableListOf<UserModel.UserData>()
    private lateinit var adapter: RecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getUsers()
    }

    private fun getUsers() {
        HttpReq.getRequest(HttpReq.USERS, object : Callback<Any?> {
            override fun onFailure(body: String) {
            }

            override fun onResponse(body: String) {
                parse()

            }
        })
    }

    private fun init() {
        adapter = RecyclerViewAdapter(users)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = true
            refresh()

            Handler().postDelayed({
                swipeRefreshLayout.isRefreshing = false
                parse()
                adapter.notifyDataSetChanged()
            }, 500)
        }

    }

    private fun refresh() {
        users.clear()
        adapter.notifyDataSetChanged()
    }



    private fun parse() {

        val userModel = UserModel()
        val json = JSONObject(JSON)

        if (json.has("page")) {
            userModel.page = json.getInt("page")
        }
        if (json.has("per_page")) {
            userModel.perPage = json.getInt("per_page")
        }
        if (json.has("total")) {
            userModel.total = json.getInt("total")
        }
        if (json.has("total_pages")) {
            userModel.totalPages = json.getInt("total_pages")
        }

        for (i in 0 until json.getJSONArray("data").length()) {
            val jsonObject = json.getJSONArray("data")[i] as JSONObject
            val data = UserModel.UserData()
            data.id = jsonObject.getInt("id")
            data.email = jsonObject.getString("email")
            data.firstName = jsonObject.getString("first_name")
            data.lastName = jsonObject.getString("last_name")
            data.avatar = jsonObject.getString("avatar")
            userModel.data.add(data)
            users.add(data)
        }


    }

}

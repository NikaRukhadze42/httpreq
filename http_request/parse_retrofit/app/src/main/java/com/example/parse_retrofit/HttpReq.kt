package com.example.parse_retrofit

import android.util.Log.d
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

object HttpReq {

    const val USERS = "users"

    private var retrofit = Retrofit.Builder()
        .baseUrl("https://reqrses.isn/api/")
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()

    private var service = retrofit.create(ApiService::class.java)

    interface ApiService {
        @GET("{path}")
        fun getRequest(@Path("path") path: String): retrofit2.Call<String>
    }

    fun getRequest(path: String, callback: com.example.parse_retrofit.Callback<Any?>){
        val call = service.getRequest(path)
        call.enqueue(onCallback(callback))
    }

    private fun onCallback(callback: com.example.parse_retrofit.Callback<Any?>): Callback<String> {
        val value = object : Callback<String> {
            override fun onFailure(call: retrofit2.Call<String>, t: Throwable) {
                d("onFailure", "${t.message}")
                callback.onFailure(t.message.toString())
            }

            override fun onResponse(call: retrofit2.Call<String>, response: Response<String>) {
                d("onFailure", "${response.body()}")
                callback.onFailure(response.body().toString())

            }

        }
        return value
    }
}



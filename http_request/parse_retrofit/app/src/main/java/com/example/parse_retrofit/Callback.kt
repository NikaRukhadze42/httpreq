package com.example.parse_retrofit


interface Callback<T> {
    fun onFailure(body: String)
    fun onResponse(body: String)
}
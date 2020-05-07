package com.example.parse_retrofit

class UserModel(var page: Int =0,
                var perPage: Int = 0,
                var total: Int = 0,
                var totalPages: Int =0){
    var data : MutableList<UserData> = mutableListOf()

    class UserData(
        var id: Int = 0,
        var email: String ="",
        var firstName: String = "",
        var lastName: String = "",
        var avatar: String ="")
}




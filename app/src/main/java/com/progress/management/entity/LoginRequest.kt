package com.progress.management.entity

import com.google.gson.annotations.SerializedName

class LoginRequest {
    @SerializedName("username")
    var username:String? = null
    @SerializedName("password")
    var password:String? = null
}
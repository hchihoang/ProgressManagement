package com.progress.management.entity

import com.google.gson.annotations.SerializedName

data class User(
    var id: Int = 0,
    @SerializedName("name")
    var name: String? = null
)
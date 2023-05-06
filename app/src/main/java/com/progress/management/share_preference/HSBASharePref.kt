package com.progress.management.share_preference

import com.progress.management.entity.response.LoginResponse
import javax.inject.Singleton

@Singleton
interface HSBASharePref {
    var savedUser: LoginResponse?

    fun logout()

}
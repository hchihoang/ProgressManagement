package com.progress.management.base.entity

data class BaseError(var error: String, var code: Int) : Exception(error)
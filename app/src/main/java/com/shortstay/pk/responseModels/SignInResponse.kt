package com.shortstay.pk.responseModels

import com.google.gson.Gson

data class SignInResponse(
    val code: Int,
    val `data`: Data,
    val message: String,
    val status: Boolean
){
    fun toJson(): String {
        val gson = Gson()
        return gson.toJson(this)
    }

}
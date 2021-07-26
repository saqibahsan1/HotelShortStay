package com.shortstay.pk.httpclient

import com.shortstay.pk.responseModels.SignInResponse
import retrofit2.Call
import retrofit2.http.*

interface HttpEndPoints {

    @FormUrlEncoded
    @POST("login")
    fun loginUser(@FieldMap parameters: HashMap<String, String>): Call<SignInResponse>

    @FormUrlEncoded
    @POST("sign-up")
    fun signUp(@FieldMap parameters: HashMap<String, String>):  Call<SignInResponse>
}
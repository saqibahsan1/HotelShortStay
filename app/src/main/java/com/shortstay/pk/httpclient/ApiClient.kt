package com.shortstay.pk.httpclient


import com.shortstay.pk.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient {

    companion object{
        private var instance: HttpEndPoints? = null

        fun getInstance(): HttpEndPoints? {
            if (instance == null) {
                lateinit var retrofit: Retrofit
                if (BuildConfig.DEBUG) {
                    val logging = HttpLoggingInterceptor()
                    logging.level = HttpLoggingInterceptor.Level.BODY
                    val httpClient = OkHttpClient.Builder()
                    httpClient.addInterceptor(logging)
                            .connectTimeout(30, TimeUnit.SECONDS)
                            .readTimeout(30, TimeUnit.SECONDS)
                            .writeTimeout(15, TimeUnit.SECONDS)
                    retrofit = Retrofit.Builder()
                        .baseUrl(Constants.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(httpClient.build())
                        .build()
                } else
                    retrofit = Retrofit.Builder()
                        .baseUrl(Constants.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                instance = retrofit.create(HttpEndPoints::class.java)
            }
            return instance
        }
    }
}
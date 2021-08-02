package com.shortstay.pk.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shortstay.pk.httpclient.ApiClient
import com.shortstay.pk.httpclient.PARAMETERS
import com.shortstay.pk.responseModels.nearbyHotels.AllHotels
import com.shortstay.pk.responseModels.nearbyHotels.Data
import com.shortstay.pk.utils.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    val hotels = MutableLiveData<List<Data>>()

    fun callApi() {
        val parameters = HashMap<String, String>()
        parameters[PARAMETERS.LATITUDE] = ""
        parameters[PARAMETERS.LONGITUDE] = ""
        val builderApiClient = ApiClient.getInstance()
        builderApiClient?.getAllHotels(parameters)?.enqueue(object : Callback<AllHotels?> {
            override fun onResponse(
                call: Call<AllHotels?>,
                response: Response<AllHotels?>
            ) {
                Utils.hideProgress()
                if (response.isSuccessful && response.body() != null) {
                        hotels.postValue(response.body()?.data)
                }
            }

            override fun onFailure(call: Call<AllHotels?>, t: Throwable) {
            }
        })
    }

}
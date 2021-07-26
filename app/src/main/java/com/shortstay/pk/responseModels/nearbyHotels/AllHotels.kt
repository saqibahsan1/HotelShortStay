package com.shortstay.pk.responseModels.nearbyHotels

data class AllHotels(
    val code: Int,
    val `data`: List<Data>,
    val message: String,
    val status: Boolean
)
package com.shortstay.pk.responseModels.nearbyHotels

data class Data(
    val address: String,
    val amenities: List<String>,
    val available_rooms: Int,
    val created_at: String,
    val hotel_descriptions: String,
    val hotel_images: List<String>,
    val hotel_name: String,
    val latitude: String,
    val longitude: String,
    val rating: String,
    val total_rooms: Int,
    val unique_prefix: String
)
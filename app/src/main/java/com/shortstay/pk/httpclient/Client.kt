    package com.shortstay.pk.httpclient

object Client {
    val client = ApiClient.getInstance()

//    suspend fun addDevice(token: String,  deviceTokenRaw: DeviceTokenRaw): Any? {
//        return withContext(Dispatchers.IO) {
//            async {
//                client?.addDevice(token,  deviceTokenRaw)
//            }
//        }.await()
//    }
//
//    suspend fun deleteDevice(token: String,  deviceTokenRaw: DeviceTokenRaw):  Any? {
//        return withContext(Dispatchers.IO) {
//            async {
//                client?.deleteDevice(token,  deviceTokenRaw)
//            }
//        }.await()
//    }
//
//    suspend fun rateRide(token: String,rateRideRaw: RateRideRaw):  Any? {
//        return withContext(Dispatchers.IO) {
//            async {
//                client?.rateRide(token,rateRideRaw)
//            }
//        }.await()
//    }
}
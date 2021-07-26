package com.shortstay.pk.ui

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsStatusCodes
import com.shortstay.pk.MainActivity
import com.shortstay.pk.R
import kotlinx.android.synthetic.main.activity_location_error.*

class LocationErrorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_error)


        close.setOnClickListener {
            finish()
        }
        get_location.setOnClickListener {
            if (checkPermission())
                getLocationPermission()
            else if (gpsCheck())
                displayLocationSettingsRequest()
        }
    }

    private fun checkPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
              android.Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    }

    private fun getLocationPermission() {
        ActivityCompat.requestPermissions(
            this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
        )
    }

    fun gpsCheck(): Boolean {
        val manager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return !manager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (requestCode == LOCATION_SETTINGS_REQUEST)
            if (resultCode == Activity.RESULT_OK) {
                startActivity(Intent(this@LocationErrorActivity, MainActivity::class.java))
                finish()
            }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    if (gpsCheck()) {
                        displayLocationSettingsRequest()
                    } else {
                        startActivity(Intent(this@LocationErrorActivity, MainActivity::class.java))
                        finish()
                    }
                }
            }
        }

    }

    private fun displayLocationSettingsRequest() {
        val locationRequest = LocationRequest.create()
            .setInterval(10 * 1000)
            .setFastestInterval(1 * 1000)
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)

        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
        builder.setAlwaysShow(true);
        val result =
            LocationServices.getSettingsClient(this)
                .checkLocationSettings(builder.build())

        result.addOnCompleteListener { task ->
            try {
                task.getResult(ApiException::class.java)
            } catch (ex: ApiException) {
                when (ex.statusCode) {
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> try {
                        val resolvableApiException =
                            ex as ResolvableApiException
                        resolvableApiException
                            .startResolutionForResult(
                                this@LocationErrorActivity,
                                LOCATION_SETTINGS_REQUEST
                            )
                    } catch (e: IntentSender.SendIntentException) {
                    }
                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                    }
                }
            }
        }
    }

    companion object {
        private const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1
        private const val LOCATION_SETTINGS_REQUEST = 2
    }
}
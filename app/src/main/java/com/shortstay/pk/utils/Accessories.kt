@file:Suppress("DEPRECATION", "DEPRECATION")

package com.shortstay.pk.utils


import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.andrognito.flashbar.Flashbar
import com.andrognito.flashbar.anim.FlashAnim
import com.google.gson.Gson
import com.shortstay.pk.R
import com.shortstay.pk.responseModels.SignInResponse


object Utils {

    private var progressDialog: ProgressDialog? = null

    private fun showDialogY(context: Context) {
        progressDialog = ProgressDialog(context, R.style.MyGravity)
        progressDialog?.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        progressDialog?.show()
        progressDialog?.setContentView(R.layout.custom_progress_dialog)
        progressDialog?.setCancelable(true)
    }

    fun showProgress(context: Context) {
        if (progressDialog != null && progressDialog?.isShowing!!) {
            //is running
            hideProgress()
        } else {
            showDialogY(context)
        }
    }

    fun hideProgress() {
        if (progressDialog != null && progressDialog?.isShowing!!)
            progressDialog?.dismiss()
    }

    fun hideStatusBar(context: Activity) {
        context.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }



    fun showToast(context: Context, message: String) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    fun showSuccessSnackBar(context: Activity, string: String) {
        val flashBar = Flashbar.Builder(context)
            .gravity(Flashbar.Gravity.TOP)
            .title("Success")
            .message(string)
            .backgroundColorRes(android.R.color.holo_green_light)
            .showIcon()
            .icon(R.drawable.ic_tick)
            .duration(3000)
            .enterAnimation(
                FlashAnim.with(context)
                    .animateBar()
                    .duration(750)
                    .alpha()
                    .overshoot()
            )
            .exitAnimation(
                FlashAnim.with(context)
                    .animateBar()
                    .duration(400)
                    .accelerateDecelerate()
            )
            .iconAnimation(
                FlashAnim.with(context)
                    .animateIcon()
                    .pulse()
                    .alpha()
                    .duration(750)
                    .accelerate()
            )
            .build()

        flashBar.show()
    }

    fun checkPermission(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    }

    fun verifyAvailableNetwork(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    fun isNetworkAvailable(context: Context?): Boolean {
        if (context == null) return false
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            try {
                return if (verifyAvailableNetwork(context)) {
                    Log.i("update_status", "Network is available : true")
                    true
                } else
                    false
            } catch (e: Exception) {
                Log.i("update_status", "" + e.message)
            }
        }
        Log.i("update_status", "Network is available : FALSE ")
        return false
    }

    fun gpsCheck(context: Context): Boolean {
        val manager =
            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return !manager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }


    fun getSignInObject(context: Context): SignInResponse? {
        var preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val gson = Gson()
        val userJson = preferences.getString("user", "")
        return gson.fromJson<Any>((userJson), SignInResponse::class.java) as SignInResponse?
    }

    fun setSignInObject(context: Context, user: SignInResponse?) {
        var preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        if (user != null) {
            preferences.edit().putString("user",(user.toJson())).apply()
        } else {
            preferences.edit().putString("user", null).apply()
        }
    }

    fun hideKeyboard(activity: Activity) {
        val view: View? = activity.currentFocus
        view?.let { v ->
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(v.windowToken, 0)
        }
    }

    fun showErrorSnackBar(context: Activity , string: String) {
        val flashBar = Flashbar.Builder(context)
            .gravity(Flashbar.Gravity.TOP)
            .title("Error")
            .message(string)
            .backgroundColorRes(android.R.color.holo_red_light)
            .showIcon()
            .duration(3000)
            .icon(R.drawable.ic_warning)
            .enterAnimation(
                FlashAnim.with(context)
                    .animateBar()
                    .duration(750)
                    .alpha()
                    .overshoot()
            )
            .exitAnimation(
                FlashAnim.with(context)
                    .animateBar()
                    .duration(400)
                    .accelerateDecelerate()
            )
            .iconAnimation(
                FlashAnim.with(context)
                    .animateIcon()
                    .pulse()
                    .alpha()
                    .duration(750)
                    .accelerate()
            )
            .build()

        flashBar.show()
    }


}

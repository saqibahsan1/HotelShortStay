package com.shortstay.pk.ui.login

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.shortstay.pk.MainActivity
import com.shortstay.pk.R
import com.shortstay.pk.databinding.LoginActivityBinding
import com.shortstay.pk.httpclient.ApiClient
import com.shortstay.pk.httpclient.PARAMETERS
import com.shortstay.pk.responseModels.SignInResponse
import com.shortstay.pk.ui.LocationErrorActivity
import com.shortstay.pk.ui.register.Registration
import com.shortstay.pk.utils.SharedPreference
import com.shortstay.pk.utils.Utils.checkPermission
import com.shortstay.pk.utils.Utils.gpsCheck
import com.shortstay.pk.utils.Utils.hideProgress
import com.shortstay.pk.utils.Utils.showErrorSnackBar
import com.shortstay.pk.utils.Utils.showProgress
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: LoginActivityBinding
    lateinit var sharedPreference: SharedPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        binding = LoginActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreference = SharedPreference(this)
        supportActionBar?.hide()
        binding.fab.setOnClickListener {
            window.exitTransition = null
            window.enterTransition = null
            val options: ActivityOptions = ActivityOptions.makeSceneTransitionAnimation(
                this,
                binding.fab,
                binding.fab.transitionName
            )
            startActivity(Intent(this, Registration::class.java), options.toBundle())
        }
    }

    private fun callApi() {
        showProgress(this)
        val parameters = HashMap<String, String>()
        parameters[PARAMETERS.USERNAME] = binding.etUsername.text.toString()
        parameters[PARAMETERS.PASSWORD] = binding.etPassword.text.toString()
        val builderApiClient = ApiClient.getInstance()
        builderApiClient?.loginUser(parameters)?.enqueue(object : Callback<SignInResponse?> {
            override fun onResponse(
                call: Call<SignInResponse?>,
                response: Response<SignInResponse?>
            ) {
                hideProgress()
                if (response.isSuccessful && response.body() != null) {
                    sharedPreference.setSignInObject(response.body())

                    if (gpsCheck(this@LoginActivity) || checkPermission(this@LoginActivity))
                        startActivity(
                            Intent(
                                this@LoginActivity,
                                LocationErrorActivity::class.java
                            )
                        )
                    else
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    finish()

                }
//                else {
//                    parseError(response.errorBody())
//                }
            }

            override fun onFailure(call: Call<SignInResponse?>, t: Throwable) {
                hideProgress()
                showErrorSnackBar(this@LoginActivity, t.message.toString())
            }
        })
    }


    override fun onRestart() {
        super.onRestart()
        binding.fab.visibility = View.GONE
    }

    override fun onResume() {
        super.onResume()
        binding.fab.visibility = View.VISIBLE
    }

    fun onLoginClick(view: View) {
        if (binding.etUsername.text.toString().isEmpty())
            binding.etUsername.error = getString(R.string.field_empty_error)
        if (binding.etPassword.text.toString().isEmpty())
            binding.etPassword.error = getString(R.string.field_empty_error)

        if (binding.etUsername.text.toString()
                .isNotEmpty() && binding.etPassword.text.toString().isNotEmpty()
        )
            callApi()
    }
}
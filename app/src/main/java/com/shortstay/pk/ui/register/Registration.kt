@file:Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package com.shortstay.pk.ui.register

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.transition.Transition
import android.transition.TransitionInflater
import android.util.Patterns
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.AccelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import com.shortstay.pk.R
import com.shortstay.pk.databinding.RegisterActivityBinding
import com.shortstay.pk.httpclient.ApiClient
import com.shortstay.pk.httpclient.PARAMETERS
import com.shortstay.pk.responseModels.SignInResponse
import com.shortstay.pk.utils.Utils.hideProgress
import com.shortstay.pk.utils.Utils.hideStatusBar
import com.shortstay.pk.utils.Utils.showErrorSnackBar 
import com.shortstay.pk.utils.Utils.showProgress
import com.shortstay.pk.utils.Utils.showSuccessSnackBar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Registration : AppCompatActivity() {

    private lateinit var binding: RegisterActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideStatusBar(this)
        binding = RegisterActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        showEnterAnimation()
        binding.fab.setOnClickListener {
            animateRevealClose()
        }
        binding.btSignup.setOnClickListener {
            validate()
        }
    }

    private fun validate() {
        if (binding.etUsername.text.toString().isEmpty())
            binding.etUsername.error = getString(R.string.field_empty_error)
        if (binding.etMobile.text.toString().isEmpty())
            binding.etMobile.error = getString(R.string.field_empty_error)
        if (binding.etEmail.text.toString().isNotEmpty() && !isValidEmail(binding.etEmail.text.toString())) {
            binding.etEmail.error = getString(R.string.validEmail)
            return
        }
        if (binding.etPassword.text.toString().isEmpty())
            binding.etPassword.error = getString(R.string.field_empty_error)
        if (binding.etRepeatpassword.text.toString().isEmpty())
            binding.etRepeatpassword.error = getString(R.string.field_empty_error)
        if (binding.etPassword.text.toString() != binding.etRepeatpassword.text.toString()) {
            binding.etRepeatpassword.error = getString(R.string.passwordNotMatched)
            return
        }

        if (binding.etUsername.text.toString().isNotEmpty() && binding.etMobile.text.toString()
                .isNotEmpty() && binding.etEmail.text.toString()
                .isNotEmpty() && binding.etPassword.text.toString()
                .isNotEmpty() && binding.etRepeatpassword.text.toString().isNotEmpty()
        )
            callApi()
    }

    private fun callApi() {
        showProgress(this)
        val parameters = HashMap<String, String>()
        parameters[PARAMETERS.PHONE] = binding.etMobile.text.toString()
        parameters[PARAMETERS.NAME] = binding.etUsername.text.toString()
        parameters[PARAMETERS.EMAIL] = binding.etEmail.text.toString()
        parameters[PARAMETERS.PASSWORD] = binding.etPassword.text.toString()
        val builderApiClient = ApiClient.getInstance()
        builderApiClient?.signUp(parameters)?.enqueue(object : Callback<SignInResponse?> {
            override fun onResponse(
                call: Call<SignInResponse?>,
                response: Response<SignInResponse?>
            ) {
                hideProgress()
                if (response.isSuccessful && response.body() != null) {
                    showSuccessSnackBar(
                        this@Registration,
                        getString(R.string.registered_successfully)
                    )
                    Handler(Looper.getMainLooper()).postDelayed({
                        animateRevealClose()
                    }, 3500)
                }
            }

            override fun onFailure(call: Call<SignInResponse?>, t: Throwable) {
                showErrorSnackBar(this@Registration, getString(R.string.signup_failed))
                hideProgress()
            }
        })
    }


    private fun isValidEmail(target: CharSequence?): Boolean {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches())
    }

    private fun animateRevealClose() {
        val mAnimator = ViewAnimationUtils.createCircularReveal(
            binding.cvAdd,
            binding.cvAdd.width / 2,
            0,
            binding.cvAdd.height.toFloat(),
            (binding.fab.width / 2).toFloat()
        )
        mAnimator.duration = 500
        mAnimator.interpolator = AccelerateInterpolator()
        mAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                binding.cvAdd.visibility = View.INVISIBLE
                super.onAnimationEnd(animation)
                binding.fab.setImageResource(R.drawable.plus)
                super@Registration.onBackPressed()
            }

            override fun onAnimationStart(animation: Animator) {
                super.onAnimationStart(animation)
            }
        })
        mAnimator.start()
    }

    fun animateRevealShow() {
        val mAnimator = ViewAnimationUtils.createCircularReveal(
            binding.cvAdd,
            binding.cvAdd.width / 2,
            0,
            (binding.fab.width / 2).toFloat(),
            binding.cvAdd.height.toFloat()
        )
        mAnimator.duration = 500
        mAnimator.interpolator = AccelerateInterpolator()
        mAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
            }

            override fun onAnimationStart(animation: Animator) {
                binding.cvAdd.visibility = View.VISIBLE
                super.onAnimationStart(animation)
            }
        })
        mAnimator.start()
    }


    private fun showEnterAnimation() {
        val transition = TransitionInflater.from(this).inflateTransition(R.transition.fabtransition)
        window.sharedElementEnterTransition = transition
        transition.addListener(object : Transition.TransitionListener {
            override fun onTransitionStart(transition: Transition) {
                binding.cvAdd.visibility = View.GONE
            }

            override fun onTransitionEnd(transition: Transition) {
                transition.removeListener(this)
                animateRevealShow()
            }

            override fun onTransitionCancel(transition: Transition) {}
            override fun onTransitionPause(transition: Transition) {}
            override fun onTransitionResume(transition: Transition) {}
        })
    }


    override fun onBackPressed() {
        animateRevealClose()
    }

}
package com.shortstay.pk

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.shortstay.pk.databinding.SplashScreenBinding
import com.shortstay.pk.httpclient.Constants.Companion.signInResponse
import com.shortstay.pk.ui.LocationErrorActivity
import com.shortstay.pk.ui.login.LoginActivity
import com.shortstay.pk.utils.SharedPreference
import com.shortstay.pk.utils.SplashView
import com.shortstay.pk.utils.Utils.checkPermission
import com.shortstay.pk.utils.Utils.gpsCheck

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: SplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private lateinit var sharedPreference: SharedPreference
    private fun init() {
        // Enable transparent status and navigation bar
        goEdgeToEdge()
        sharedPreference = SharedPreference(this)
        // Animate splash screen logo
        findViewById<SplashView>(R.id.splash_view)
            .animateLogo()

        // Animate content
        findViewById<View>(R.id.welcome_title)
            .animate()
            .scaleX(TARGET_SCAlE)
            .scaleY(TARGET_SCAlE)
            .setStartDelay(SCALE_ANIMATION_DELAY)
            .setDuration(SCALE_ANIMATION_DURATION)
            .start()

        Handler(Looper.getMainLooper()).postDelayed({
            if (sharedPreference.getSignInObject() != null) {
                signInResponse = sharedPreference.getSignInObject()

                if (gpsCheck(this) || checkPermission(this@SplashActivity))
                    startActivity(Intent(this@SplashActivity, LocationErrorActivity::class.java))
                else
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            } else
                startActivity(Intent(this@SplashActivity, LoginActivity::class.java))

            finish()
        }, SWITCH_SCREEN_TIME)

    }


    @Suppress("DEPRECATION")
    private fun goEdgeToEdge() {
        this.window?.apply {
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            statusBarColor = Color.TRANSPARENT
        }
    }

    companion object {
        private const val TARGET_SCAlE = 1f
        private const val SCALE_ANIMATION_DELAY = 800L
        private const val SCALE_ANIMATION_DURATION = 800L
        private const val SWITCH_SCREEN_TIME = 4000L
    }
}
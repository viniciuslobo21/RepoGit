package com.lobo.repogit.presentation

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.databinding.DataBindingUtil
import com.lobo.repogit.R
import com.lobo.repogit.core.platform.BaseActivity
import com.lobo.repogit.databinding.ActivitySplashBinding

class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        Handler().postDelayed(
            {
                startActivity(Intent(this, MainActivity::class.java))
            }, 3000
        )
    }

    override fun getContentLayoutId(): Int = R.layout.activity_splash

    override fun init() {
    }
}
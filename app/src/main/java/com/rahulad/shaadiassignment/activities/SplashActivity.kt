package com.rahulad.shaadiassignment.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.rahulad.shaadiassignment.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        initUI()

        // open home activity after splash
        GlobalScope.launch {
            delay(1500)
            val intent = Intent(this@SplashActivity, MyMatchesActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun initUI() {
        supportActionBar?.hide()
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    }
}
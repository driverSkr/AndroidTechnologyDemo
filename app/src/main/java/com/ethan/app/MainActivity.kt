package com.ethan.app

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ethan.app.databinding.ActivityMainBinding
import com.ethan.app.ui.AndroidStudioDevelopmentPracticeActivity
import com.ethan.app.ui.LoginActivity
import com.ethan.app.ui.ShareActivity
import com.example.banner.BannerActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.banner.setOnClickListener { startActivity(Intent(this, BannerActivity::class.java)) }
        binding.share.setOnClickListener { startActivity(Intent(this, ShareActivity::class.java)) }
        binding.login.setOnClickListener { startActivity(Intent(this, LoginActivity::class.java)) }
        binding.developPractice.setOnClickListener { startActivity(Intent(this, AndroidStudioDevelopmentPracticeActivity::class.java)) }
    }
}
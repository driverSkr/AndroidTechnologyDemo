package com.ethan.app

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ethan.app.databinding.ActivityMainBinding
import com.ethan.app.ui.AndroidStudioDevelopmentPracticeActivity
import com.ethan.app.ui.LoginActivity
import com.ethan.app.ui.ShareActivity
import com.ethan.util.Global
import com.example.banner.BannerActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Global.setOnClickListener(binding.banner, binding.share, binding.login, binding.developPractice) {
            when (this) {
                binding.banner -> { startActivity(Intent(this@MainActivity, BannerActivity::class.java)) }
                binding.share -> { startActivity(Intent(this@MainActivity, ShareActivity::class.java)) }
                binding.login -> { startActivity(Intent(this@MainActivity, LoginActivity::class.java)) }
                binding.developPractice -> { startActivity(Intent(this@MainActivity, AndroidStudioDevelopmentPracticeActivity::class.java)) }
            }
        }
    }
}
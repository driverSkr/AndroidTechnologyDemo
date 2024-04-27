package com.ethan.app.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ethan.app.databinding.ActivityAndroidStudioDevelopmentPracticeBinding
import com.ethan.customControls.CustomControlMainActivity
import com.ethan.util.Global

class AndroidStudioDevelopmentPracticeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAndroidStudioDevelopmentPracticeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAndroidStudioDevelopmentPracticeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Global.setOnClickListener(binding.btnCustomControls, binding.btnGroupControls, binding.btnAnimationEffects) {
            when (this) {
                binding.btnCustomControls -> startActivity(Intent(this@AndroidStudioDevelopmentPracticeActivity, CustomControlMainActivity::class.java))
                binding.btnGroupControls -> {}
                binding.btnAnimationEffects -> {}
            }
        }
    }
}
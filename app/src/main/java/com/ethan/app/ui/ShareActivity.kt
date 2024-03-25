package com.ethan.app.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.GridLayout
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.ethan.app.databinding.ActivityShareBinding
import com.ethan.app.databinding.ItemShareBinding
import com.ethan.app.vm.ShareViewModel

class ShareActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShareBinding
    private lateinit var videoUri: Uri
    // 注册一个善后工作的活动结果启动器，获取指定类型的内容
    /*Activity Result API 方式*/
    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let { videoUri = it }
    }
    // 初始化 launcher
    private val shareLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // 分享成功，可以根据需求在这里添加处理逻辑
        } else {
            // 分享失败或被用户取消，同样可以添加相应的处理逻辑
        }
    }

    private val shareViewModel by lazy { ViewModelProvider(this)[ShareViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShareBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.shareMethodList.alignmentMode = GridLayout.ALIGN_BOUNDS
        shareViewModel.shareIconList.forEachIndexed { index, it ->
            val itemBinding = ItemShareBinding.inflate(LayoutInflater.from(this))
            itemBinding.ivIcon.setImageResource(it.icon)
            itemBinding.tvAppName.setText(it.text)
            setShareItemClickEvent(itemBinding, it)

            val row = index / binding.shareMethodList.columnCount
            val col = index % binding.shareMethodList.columnCount
            val layoutParams = GridLayout
                .LayoutParams(GridLayout.spec(row, 1f), GridLayout.spec(col, 1f)).apply {
                    this.setGravity(Gravity.FILL) // 这个设置填充item区域的
                }
            binding.shareMethodList.addView(itemBinding.root, layoutParams)
        }

        binding.share.setOnClickListener {
            selectVideo()
        }
        binding.getVideo.setOnClickListener {
            getVideo()
        }
    }

    private fun setShareItemClickEvent(itemBinding: ItemShareBinding, icon: ShareViewModel.ShareIcon) {
        itemBinding.root.setOnClickListener {
            selectVideo()
            shareViewModel.doShare(this, icon, videoUri, shareLauncher)
        }
    }

    private fun getVideo() {
        //准备跳转到系统视频库
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "video/*"     // 类型为视频
        startActivityForResult(intent, 3)     //打开系统视频库
    }

    private fun selectVideo() {
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            launcher.launch("video/*")
        }else {
            // 请求读取外部存储权限
            requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 100)
        }
    }

    // 在 onRequestPermissionsResult 方法中处理权限请求结果
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100 && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
            // 权限已授予，此时可以再次调用 selectVideo 函数来打开选择视频的界面
            selectVideo()
        } else {
            // 用户未授权或者取消了权限申请，需进行相应提示或处理
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        if (resultCode== Activity.RESULT_OK && requestCode== 3) {
            intent?.data?.let {
                videoUri = it
            }
        }
    }
}
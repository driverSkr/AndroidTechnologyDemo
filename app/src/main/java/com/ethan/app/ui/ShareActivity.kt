package com.ethan.app.ui

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.GridLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.ethan.app.databinding.ActivityShareBinding
import com.ethan.app.databinding.ItemShareBinding
import com.ethan.app.vm.ShareViewModel

class ShareActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShareBinding

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
    }

    private fun setShareItemClickEvent(itemBinding: ItemShareBinding, icon: ShareViewModel.ShareIcon) {
        /*itemBinding.root.antiShakeClick {
            if (shareViewModel?.shareVideoPath?.value?.loadState != ShareViewModel.ShareVideoState.EncodeVideoState.SUCCESS) return@antiShakeClick
            shareViewModel?.doShare(this, icon, launcher, "")
        }*/
    }
}
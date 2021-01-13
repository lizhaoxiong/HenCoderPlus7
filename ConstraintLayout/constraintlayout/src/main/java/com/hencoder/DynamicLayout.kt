package com.hencoder

import android.app.Activity
import android.os.Bundle
import android.text.TextUtils.TruncateAt
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.hencoder.constraintlayout.R
import com.hencoder.constraintlayout.R.layout

/**
 * 创建时间: 2021/01/13 16:17 <br></br>
 * 作者: lizhaoxiong <br></br>
 * 描述:
 */
class DynamicLayout : Activity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(layout.activity_dynamic_layout)

    val constraintLayout = findViewById<ConstraintLayout>(R.id.root)
    listOf<String>("吃好喝好","wwwwwwww","我耳机覅额减肥IE减肥IE").forEachIndexed { index, s ->
      val iv = ImageView(this).apply {
        id = R.id.iv + index
        setImageResource(R.mipmap.ic_launcher_round)
        layoutParams = ConstraintLayout.LayoutParams(75, 75).apply {
          leftToLeft = R.id.root
          marginStart = 100 + index * (75 + 64 + 200)
          topToBottom = R.id.imageView4
          topMargin = 40
        }
      }

      val tv = TextView(this).apply {
        text = s
        textSize = 14f
        maxEms = 7
        isSingleLine = true
        ellipsize = TruncateAt.END

        layoutParams = ConstraintLayout.LayoutParams(
          ConstraintLayout.LayoutParams.WRAP_CONTENT,
          ConstraintLayout.LayoutParams.WRAP_CONTENT
        ).apply {
          leftToLeft = iv.id
          rightToRight = iv.id
          topToBottom = iv.id
          topMargin = 20
        }
      }
      constraintLayout.addView(iv)
      constraintLayout.addView(tv)
    }
  }
}
package com.example.mocklocation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val textView = TextView(this).apply {
            text = """
                MockLocation App 已启动
                
                请在开发者选项中：
                1. 开启 USB 调试
                2. 选择本应用为"模拟位置应用"
                
                然后使用 Python 脚本控制位置
            """.trimIndent()
            textSize = 16f
            setPadding(50, 100, 50, 50)
        }
        
        setContentView(textView)
    }
}

package com.github.heesung6701.quokkaui.catalog.picker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.heesung6701.quokkaui.catalog.databinding.ActivityComponentPickerBinding

class ComponentPickerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityComponentPickerBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
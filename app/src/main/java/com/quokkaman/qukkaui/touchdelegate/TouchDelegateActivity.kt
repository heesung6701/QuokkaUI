package com.quokkaman.qukkaui.touchdelegate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.quokkaman.qukkaui.databinding.ActivityTouchDelegateBinding
import com.github.heesung6701.quokkaui.touchdelegate.TouchDelegateSetBuilder

class TouchDelegateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityTouchDelegateBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        TouchDelegateSetBuilder(binding.containerSingle)
            .add(binding.btnSingle, TouchDelegateSetBuilder.Insets(100, 10, 100, 10))
            .done()

        TouchDelegateSetBuilder(binding.containerMulti)
            .add(binding.btnMultiple1, TouchDelegateSetBuilder.Insets(100, 0, 5, 0))
            .add(binding.btnMultiple2, TouchDelegateSetBuilder.Insets(5, 0, 100, 0))
            .done()
    }
}
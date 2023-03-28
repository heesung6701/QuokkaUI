package com.github.heesung6701.quokkaui.catalog.touchdelegate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.github.heesung6701.quokkaui.catalog.databinding.ActivityTouchDelegateBinding
import com.github.heesung6701.quokkaui.touchdelegate.TouchDelegateSet
import com.github.heesung6701.quokkaui.touchdelegate.TouchDelegateSet.Builder.Insets

class TouchDelegateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityTouchDelegateBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        TouchDelegateSet.Builder(binding.containerSingle)
            .add(binding.btnSingle, Insets(100, 10, 100, 10))
            .done()

        TouchDelegateSet.Builder(binding.containerMulti)
            .add(binding.btnMultiple1, Insets(100, 0, 5, 0))
            .add(binding.btnMultiple2, Insets(5, 0, 100, 0))
            .done()
    }
}
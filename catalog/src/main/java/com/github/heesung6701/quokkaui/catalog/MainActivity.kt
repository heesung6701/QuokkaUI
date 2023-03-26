package com.github.heesung6701.quokkaui.catalog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.github.heesung6701.quokkaui.catalog.touchdelegate.TouchDelegateActivity;
import com.github.heesung6701.quokkaui.catalog.anchor.window.DialogAnchorActivity;
import com.github.heesung6701.quokkaui.catalog.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnTouchDelegate.setOnClickListener((btn) -> startActivity(new Intent(this, TouchDelegateActivity.class)));
        binding.btnDialogAnchor.setOnClickListener((btn) -> startActivity(new Intent(this, DialogAnchorActivity.class)));
    }
}
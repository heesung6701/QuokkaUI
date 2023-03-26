package com.quokkaman.qukkaui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.quokkaman.qukkaui.anchor.window.DialogAnchorActivity;
import com.quokkaman.qukkaui.databinding.ActivityMainBinding;
import com.quokkaman.qukkaui.touchdelegate.TouchDelegateActivity;

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
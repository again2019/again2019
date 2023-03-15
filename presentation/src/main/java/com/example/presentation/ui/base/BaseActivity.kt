package com.example.presentation.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<B : ViewBinding> (
    val bindingFactory : (LayoutInflater) -> B
    ) : AppCompatActivity() {
    // 익명함수 : 함수에 이름이 없는 형식
    // (매개변수) -> 결과

    private var _binding :B? = null
        val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = bindingFactory(layoutInflater)
        setContentView(binding.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    }
package com.mumayank.airwebview.example.app.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.viewModelScope
import com.mumayank.airwebview.example.app.AppConstants
import com.mumayank.airwebview.example.app.R
import com.mumayank.airwebview.example.app.databinding.ActivityViewBinding

class ViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewBinding
    private val viewModel by viewModels<ViewViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        with(binding) {
            progressLayout.visibility = View.VISIBLE
            airWebView.load(
                this@ViewActivity,
                viewModel.viewModelScope,
                intent.getStringExtra(AppConstants.URL_DATA).toString(),
                onLoaded = {
                    progressLayout.visibility = View.GONE
                },
                onError = {
                    Toast.makeText(
                        this@ViewActivity,
                        "Something went wrong",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )
        }
    }
}
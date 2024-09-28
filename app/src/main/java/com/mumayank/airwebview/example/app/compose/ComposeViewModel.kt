package com.mumayank.airwebview.example.app.compose

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mumayank.airwebview.helpers.AirWebViewHelper

class ComposeViewModel : ViewModel() {

    private val _composeStatus = MutableLiveData<ComposeStatus>()
    val composeStatus: LiveData<ComposeStatus> = _composeStatus

    fun loadUrl(
        context: Context,
        url: String
    ) {
        AirWebViewHelper.load(
            context,
            viewModelScope,
            url,
            onWebsiteUrl = {
                _composeStatus.postValue(ComposeStatus.WebsiteUrl(it))
            },
            onPdfFile = {
                _composeStatus.postValue(ComposeStatus.PdfFile(it))
            },
            onError = {
                _composeStatus.postValue(ComposeStatus.Error)
            }
        )
    }

}
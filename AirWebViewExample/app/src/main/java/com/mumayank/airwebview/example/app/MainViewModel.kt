package com.mumayank.airwebview.example.app

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mumayank.airwebview.helpers.AirWebViewHelper

class MainViewModel : ViewModel() {

    private val _mainStatus = MutableLiveData<MainStatus>()
    val mainStatus: LiveData<MainStatus> = _mainStatus

    fun loadUrl(
        context: Context,
        url: String
    ) {
        AirWebViewHelper.load(
            context,
            viewModelScope,
            url,
            onWebsiteUrl = {
                _mainStatus.postValue(MainStatus.WebsiteUrl(it))
            },
            onPdfFile = {
                _mainStatus.postValue(MainStatus.PdfFile(it))
            },
            onError = {
                _mainStatus.postValue(MainStatus.Error)
            }
        )
    }

}
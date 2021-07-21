package com.example.simple.mainp

import android.annotation.SuppressLint
import android.app.Application
import android.os.Build
import android.util.Log
import android.util.Patterns
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.simple.database.BrowserDataBaseDao
import com.example.simple.database.UrlBar
import kotlinx.coroutines.launch

class MainFragmentViewModel (
        val database: BrowserDataBaseDao,
        application: Application): AndroidViewModel(application)
{

        private var url_link = MutableLiveData<UrlBar?>()

        val link = database.getALLUrls()



    var _url = MutableLiveData<String>()
    val url: LiveData<String>
    get() = _url

    var _webView = MutableLiveData<WebView>()
    val webView: LiveData<WebView>
    get() = _webView

     var _twitlink = MutableLiveData<String>()
    val twitLink: LiveData<String>
    get() = _twitlink

    var _fblink = MutableLiveData<String>()
    val fbLink: LiveData<String>
    get() = _fblink

    var _nairalink = MutableLiveData<String>()
    val nairaLink: LiveData<String>
    get() = _nairalink



    init {
        _url.value = ""
        _nairalink.value= "https://www.nairaland.com"
        _twitlink.value = "https://www.twitter.com"
        _fblink.value = "https://www.facebook.com"
        initialzize_url()
    }

    val url_text = MutableLiveData<String>()

    val layoutOff = MutableLiveData<Boolean>()

    fun on(){
        layoutOff.value = true
    }

    fun off(){
        layoutOff.value= false

    }

    fun goto(){
        val isAddress = Patterns.WEB_URL.matcher(_url.value.toString()).matches()
        _webView.value?.webViewClient = WebViewClient()
        if (isAddress){
            _webView.value?.loadUrl(_url.value)
        }else{
            webView.value?.loadUrl("https://www.google.com/search?q=${_url.value}")
        }
    }




    @RequiresApi(Build.VERSION_CODES.Q)
    @SuppressLint("setJavaScriptEnabled")
    fun gotwitter(){

        _webView.value?.webViewClient = WebViewClient()

        _webView.value?.apply {
            loadUrl("https://www.twitter.com/")
            settings.javaScriptEnabled = true
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                settings.safeBrowsingEnabled
            }
            Log.i("webi","webview loaded")



        }

        fun twitlink(){
            _twitlink.value = "https://www.twitter.com"
        }



    }


    private fun initialzize_url(){
        viewModelScope.launch {
            url_link.value = getUrlFromDatabase()
        }
    }

    private suspend fun getUrlFromDatabase(): UrlBar? {
        var url = database.geturl()
        return url
    }

     fun collectUrl(){
        viewModelScope.launch {
            val newUrl = UrlBar()
            if (newUrl != null) {
                insert(newUrl)
            }
            url_link.value = getUrlFromDatabase()
        }

    }


     suspend fun insert(url: UrlBar){
        database.insert(url)
    }

    /*suspend fun urls(newLink: UrlBar) {
        _webView.value?.let { UrlBar(url_title = it.title, /*url_image_name = _webView.value!!.favicon,*/ url_link = _webView.value!!.url) }?.let {
            database.insert(
                    it
        )
        }
    }*/


}
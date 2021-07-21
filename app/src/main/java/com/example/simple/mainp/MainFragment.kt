package com.example.simple.mainp

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebHistoryItem
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.room.Database
import com.example.simple.R
import com.example.simple.database.BrowserDatabase
import com.example.simple.database.UrlBar
import com.example.simple.databinding.FragmentMainBinding
import java.util.*
import kotlin.collections.ArrayList


class MainFragment : Fragment() {


    var share_url:String? = null

    private lateinit var mwebView: WebView


    var textView: String = ""




    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("RestrictedApi")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment
        var binding : FragmentMainBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_main,container, false
        )
        val application = requireNotNull(this.activity).application

        val dataSource = BrowserDatabase.getInstance(application).browserDataBaseDao


        val viewModelFactory = MainFragmentViewModelFactory(dataSource,application)

        val mainTrackerViewModel = ViewModelProvider(this,viewModelFactory).get(MainFragmentViewModel::class.java)

        binding.lifecycleOwner = this

        binding.lifecycleOwner = viewLifecycleOwner


        binding.mainfragmentviewmodel = mainTrackerViewModel

        mainTrackerViewModel.url.observe(viewLifecycleOwner, Observer {text ->
            binding.editedText.text = text
        })

        val webView = binding.webView

        val webview= binding.webView

        mwebView = binding.webView

        mainTrackerViewModel.webView.observe(viewLifecycleOwner, Observer {url ->
            url.url
        })






       /// mainTrackerViewModel._webView.observe(viewLifecycleOwner, Observer { web -> webView = web })


        textView = binding.edit.toString()
        @RequiresApi(Build.VERSION_CODES.Q)
        @SuppressLint("setJavaScriptEnabled")
         fun go_fb(){

            var webview = binding.webView

            webview.webViewClient = WebViewClient()

            webview.apply {
                loadUrl(mainTrackerViewModel.fbLink.value)
                settings.javaScriptEnabled
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    settings.safeBrowsingEnabled
                }


            }
            webview.isVisible=true
            binding.mai1.isVisible= false

        }

        fun go_twit(){
            webview.webViewClient = WebViewClient()

            webview.apply {
                loadUrl(mainTrackerViewModel.twitLink.value)
                settings.javaScriptEnabled = true
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    settings.safeBrowsingEnabled
                }
            }
            mainTrackerViewModel.collectUrl()
        }
            fun go_naira(){
            webview.webViewClient = WebViewClient()

            webview.apply {
                loadUrl(mainTrackerViewModel.nairaLink.value)
                settings.javaScriptEnabled = true
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    settings.safeBrowsingEnabled
                }
            }
            webview.isVisible=true

            binding.mai1.isVisible= false
        }

        binding.fbBtn.setOnClickListener { go_fb() }



        fun getBackHistory() : ArrayList<String> {
            val webBackHistory = webView.copyBackForwardList()
            val historyList =  ArrayList<String>()

            for (i in 0 until webBackHistory.currentIndex)
                historyList.add(webBackHistory.getItemAtIndex(i).title)

            historyList.reverse()
            return historyList
        }
        fun getForwardHistory() : ArrayList<String>{
            val webForwardHistory = webView.copyBackForwardList()
            val historyList =  ArrayList<String>()

            for (i in 0 until webForwardHistory.size - webForwardHistory.currentIndex -1)
                historyList.add(webForwardHistory.getItemAtIndex((
                        webForwardHistory.currentIndex + i + 1
                        )).title)
            return historyList
        }


        binding.bckBtn.setOnLongClickListener {
            getBackHistory()
            true
        }
        binding.forBtn.setOnLongClickListener {
            getForwardHistory()
            true
        }

        @SuppressLint("SetJavaScriptEnabled")
        @RequiresApi(Build.VERSION_CODES.O)

        fun goto(){

            val edit = binding.edit
            var webview = binding.webView



            webview.webViewClient = WebViewClient()

            val addr: String = edit.text.toString()



            webview.apply {

                if(Patterns.WEB_URL.matcher(addr.toLowerCase(Locale.ROOT)).matches())
                {
                    if (addr.contains("http://") || addr.contains("https://"))
                    {
                        webview.loadUrl(addr)

                    }
                    else
                    {
                        webview.loadUrl("https://$addr")

                    }
                }
                else
                {
                    //google search url
                    webview.loadUrl("https://www.google.com/search?q=$addr")


                }





            }

            binding.editedText.text = binding.edit.text
            textView = binding.editedText.text.toString()
            webview.settings.javaScriptEnabled = true
            webview.canGoBack()
            webview.canGoForward()
            webview.isVisible = true
            binding.mai1.isVisible= false
            binding.editedText.isVisible= true
            binding.edit.isVisible = false
            webview.onPause()
            webview.onResume()
            webView.copyBackForwardList().apply { mainTrackerViewModel.collectUrl() }
            mainTrackerViewModel.collectUrl()



            Log.i("web","webview gone to")






        }





        fun edi(){
            binding.edit.isVisible = true
            binding.editedText.isVisible = false
        }

        fun goHome(){
            binding.webView.findNavController().navigate(MainFragmentDirections.actionMainFragmentSelf())
        }

        fun refresh(){
            binding.webView.reload()
        }


        binding.editedText.setOnClickListener {
            edi()
        }

        binding.nairaBtn.setOnClickListener { go_naira() }

        binding.twiBtn.setOnClickListener { go_twit() }


        binding.reloadBtn.setOnClickListener { refresh() }

        binding.homeBtn.setOnClickListener { goHome() }

        binding.btn.setOnClickListener { goto() }

        binding.forBtn.setOnClickListener{
            binding.webView.goForward()
        }
       binding.bckBtn.setOnClickListener{
            binding.webView.goBack()
        }
/*
        binding.menuBtn.setOnClickListener {view:View->
            view.findNavController().navigate(MainFragmentDirections.actionMainFragmentToSettingsFragment22())
        }*/
        binding.toolbar2.inflateMenu(R.menu.menu)

        binding.toolbar2.setOnMenuItemClickListener{
            if (it.itemId == R.id.version_detailsFragment)  {
                view?.findNavController()?.navigate(MainFragmentDirections.actionMainFragmentToVersionDetailsFragment())
            }
            if (it.itemId == R.id.settingsFragment2)  {
                view?.findNavController()?.navigate(MainFragmentDirections.actionMainFragmentToSettingsFragment22())

            }
            true
        }

   /*if (savedInstanceState != null ) {


       textView = savedInstanceState.getString(KEY_URL).toString()
       if (mwebView.isNotEmpty()){
           mwebView.reload()
       }*/


  return binding.root
}

override fun onPause() {
  super.onPause()
  mwebView.onPause()
     mwebView.pauseTimers()
}

override fun onResume() {
  super.onResume()
    mwebView.onResume()
    mwebView.resumeTimers()
}

/*
  override fun onSaveInstanceState(outState: Bundle) {
  super.onSaveInstanceState(outState)
  outState.putString(KEY_URL,textView)
  mwebView.saveState(outState)
  Log.i("last","lastsaved")

}*/


abstract class WebHistoryItem : Cloneable {

}

}







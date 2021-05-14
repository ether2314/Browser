package com.example.simple

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.activity.addCallback
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.simple.databinding.FragmentMainBinding
import java.util.*

class MainFragment : Fragment() {

    var share_url:String? = null





    @SuppressLint("RestrictedApi")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding : FragmentMainBinding = DataBindingUtil.inflate(
            inflater,R.layout.fragment_main,container, false
        )












        @RequiresApi(Build.VERSION_CODES.Q)
        @SuppressLint("setJavaScriptEnabled")
         fun webViewSetup(){


            binding.webView.webViewClient = WebViewClient()

            binding.webView.apply {
                loadUrl("https://www.google.com/")
                settings.javaScriptEnabled
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    settings.safeBrowsingEnabled
                }

            }

        }



        @SuppressLint("SetJavaScriptEnabled")
        @RequiresApi(Build.VERSION_CODES.O)

        fun goto(){
            val edit = binding.edit
            val webview = binding.webView

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



            webview.settings.javaScriptEnabled = true
            webview.canGoBack()
            webview.canGoForward()
            webview.isVisible = true
            binding.mai1.isVisible= false





        }

        fun goHome(){
            binding.webView.findNavController().navigate(MainFragmentDirections.actionMainFragmentSelf())
        }

        fun refresh(){
            binding.webView.reload()
        }



        binding.reloadBtn.setOnClickListener { refresh() }

        binding.homeBtn.setOnClickListener { goHome() }

        binding.btn.setOnClickListener { goto()

        }

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





        return binding.root
    }









}
package com.example.nfc.mynfcreader

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_qr.*
import kotlinx.android.synthetic.main.activity_webview.*


class WebviewActivity : AppCompatActivity() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)

        if(webView!=null){

            val webSettings = webView!!.settings
            webSettings.javaScriptEnabled = true

            webView!!.webViewClient = WebViewClient()
            webView!!.webChromeClient = WebChromeClient()
            //URL
            webView.loadUrl("https://google.com")

            webView!!.webViewClient = object:WebViewClient(){
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    progressBar.visibility = View.VISIBLE
                    super.onPageStarted(view, url, favicon)
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    progressBar.visibility = View.GONE
                    super.onPageFinished(view, url)
                }
            }

        }
    }

    override fun onBackPressed() {
        if(webView!!.canGoBack()){
            webView!!.goBack()
        }
        else{
            super.onBackPressed()
        }
    }
}

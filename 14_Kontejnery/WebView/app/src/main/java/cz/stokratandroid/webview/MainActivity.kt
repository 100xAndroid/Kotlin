package cz.stokratandroid.webview

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        spustitBrowser()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun spustitBrowser() {

        // val url = "https://cs.wikipedia.org/wiki/Android_(opera%C4%8Dn%C3%AD%20syst%C3%A9m)"
        val url = "file:///android_asset/verzeAndroidu.html"

        // najdit komponentu WebView
        val browser = findViewById<View>(R.id.webView) as WebView

        // nastavit parametry browseru
        browser.settings.javaScriptEnabled = true
        browser.webViewClient = WebViewClient()

        // zobrazit webovou stranku
        browser.loadUrl(url)
    }
}

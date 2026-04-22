package nl.erasmusmagazine.newsapp.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import nl.erasmusmagazine.newsapp.databinding.ActivityTipEditorBinding

class TipEditorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTipEditorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTipEditorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.topAppBar.setNavigationOnClickListener { finish() }

        val url = intent.getStringExtra(EXTRA_URL) ?: return
        configureWebView(binding.webView)
        binding.webView.loadUrl(url)
    }

    private fun configureWebView(webView: WebView) {
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true

        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                binding.progressBar.visibility = android.view.View.GONE
            }
        }

        webView.webChromeClient = WebChromeClient()
    }

    companion object {
        private const val EXTRA_URL = "extra_url"

        fun open(context: Context, url: String) {
            context.startActivity(Intent(context, TipEditorActivity::class.java).putExtra(EXTRA_URL, url))
        }
    }
}

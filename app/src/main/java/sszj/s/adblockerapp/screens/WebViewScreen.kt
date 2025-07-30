package sszj.s.adblockerapp.screens

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.net.Uri
import android.webkit.*
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

val adHosts = listOf(
    "doubleclick.net",
    "googlesyndication.com",
    "adservice.google.com",
    "googletagservices.com",
    "googleadservices.com",
    "google-analytics.com",
    "adsafeprotected.com",
    "adnxs.com",
    "ads.yahoo.com",
    "adform.net",
    "adbrite.com",
    "advertising.com",
    "quantserve.com",
    "scorecardresearch.com",
    "zedo.com",
    "media.net",
    "yieldmo.com",
    "openx.net",
    "pubmatic.com",
    "moatads.com",
    "rubiconproject.com",
    "facebook.net",
    "fbcdn.net",
    "criteo.com",
    "ads.twitter.com",
    "taboola.com",
    "outbrain.com",
    "popads.net",
    "propellerads.com",
    "revcontent.com",
    "mgid.com",
    "onclickads.net",
    "traffichaus.com",
    "exoclick.com",
    "inmobi.com",
    "chartboost.com",
    "unityads.unity3d.com",
    "vungle.com",
    "ironsrc.com"
)

@SuppressLint("SetJavaScriptEnabled")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WebViewScreen(url: String?, navController: NavController) {
    var progress by remember { mutableStateOf(0) }
    var webView: WebView? by remember { mutableStateOf(null) }

    BackHandler(enabled = true) {
        if (webView?.canGoBack() == true) {
            webView?.goBack()
        } else {
            navController.popBackStack()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Web Page") },
                navigationIcon = {
                    IconButton(onClick = {
                        if (webView?.canGoBack() == true) {
                            webView?.goBack()
                        } else {
                            navController.popBackStack()
                        }
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {

            if (progress in 1..99) {
                LinearProgressIndicator(
                    progress = progress / 100f,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                )
            }

            AndroidView(
                factory = { context ->
                    WebView(context).apply {
                        settings.javaScriptEnabled = true
                        settings.domStorageEnabled = true
                        settings.builtInZoomControls = true

                        webViewClient = object : WebViewClient() {
                            override fun shouldOverrideUrlLoading(
                                view: WebView?,
                                request: WebResourceRequest?
                            ): Boolean {
                                val urlToLoad = request?.url.toString()
                                return if (isAdUrl(urlToLoad)) {
                                    true // Block ad URL
                                } else {
                                    false
                                }
                            }

                            override fun onPageStarted(
                                view: WebView?,
                                url: String?,
                                favicon: Bitmap?
                            ) {
                                progress = 1
                            }

                            override fun onPageFinished(view: WebView?, url: String?) {
                                progress = 100
                            }
                        }

                        webChromeClient = object : WebChromeClient() {
                            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                                progress = newProgress
                            }
                        }

                        loadUrl(url ?: "")
                        webView = this
                    }
                },
                update = { it.loadUrl(url ?: "") },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}


fun isAdUrl(url: String): Boolean {
    val uri = Uri.parse(url)
    val host = uri.host ?: return false
    return adHosts.any { adHost -> host.contains(adHost, ignoreCase = true) }
}


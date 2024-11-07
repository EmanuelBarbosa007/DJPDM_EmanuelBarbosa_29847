package ipca.examples.dailynews.ui

import android.content.Context
import android.content.Intent
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun ArticleDetail(
    modifier: Modifier,
    url: String,
    title: String?,
    navController: NavController // Agora você está recebendo o navController aqui
) {
    val context = LocalContext.current

    Column(modifier = modifier.fillMaxSize()) {
        Text(
            text = title ?: "Título não disponível",
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.titleLarge
        )

        AndroidView(
            factory = { context ->
                WebView(context).apply {
                    settings.javaScriptEnabled = true
                    webViewClient = WebViewClient()
                    settings.loadWithOverviewMode = true
                    settings.useWideViewPort = true
                    settings.setSupportZoom(true)
                }
            },
            modifier = Modifier.weight(1f),
            update = { webView ->
                webView.loadUrl(url)
            }
        )

        // Aqui vai a barra de navegação com os ícones
        BottomAppBar {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Voltar"
                )
            }

            IconButton(onClick = { shareArticle(context, url) }) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "Compartilhar"
                )
            }

            IconButton(onClick = { /* Definições */ }) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Configurações"
                )
            }
        }
    }
}



fun shareArticle(context: Context, url: String) {
    val shareIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, "Veja este artigo: $url")
        type = "text/plain"
    }
    context.startActivity(Intent.createChooser(shareIntent, "Compartilhar via"))
}

@Preview
@Composable
fun ArticleDetailPreview() {
    ArticleDetail(
        modifier = Modifier.fillMaxSize(),
        navController = rememberNavController(),
        url = "https://www.google.com",
        title = "Título de Exemplo"
    )
}

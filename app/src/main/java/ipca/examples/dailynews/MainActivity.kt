package ipca.examples.dailynews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ipca.examples.dailynews.ui.ArticleDetail
import ipca.examples.dailynews.ui.HomeView
import ipca.examples.dailynews.ui.theme.DailyNewsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DailyNewsTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(navController = navController,
                        startDestination = Screen.Home.route) {

                        composable(route = Screen.Home.route) {
                            HomeView(
                                navController = navController,
                                modifier = Modifier.padding(innerPadding)
                            )
                        }

                        composable(route = Screen.ArticleDetail.route) { backStackEntry ->
                            val url = backStackEntry.arguments?.getString("articleUrl")
                            val title = backStackEntry.arguments?.getString("articleTitle")

                            // Passando o navController para ArticleDetail
                            ArticleDetail(
                                modifier = Modifier.padding(innerPadding),
                                url = url ?: "",
                                title = title ?: "",
                                navController = navController // Passando o navController
                            )
                        }


                    }
                }
            }
        }
    }
}

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object ArticleDetail : Screen("article_detail/{articleUrl}/{articleTitle}")
}


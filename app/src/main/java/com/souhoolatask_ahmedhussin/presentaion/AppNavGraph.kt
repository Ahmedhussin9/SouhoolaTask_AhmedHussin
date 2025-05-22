package com.souhoolatask_ahmedhussin.presentaion

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.souhoolatask_ahmedhussin.domain.dto.Article
import com.souhoolatask_ahmedhussin.presentaion.news.NewsScreenSetup
import com.souhoolatask_ahmedhussin.presentaion.news_details.ArticleDetailsScreen


@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "newsList"
    ) {
        composable("newsList") {
            NewsScreenSetup(
                onArticleClick = { article ->
                    navController.currentBackStackEntry?.savedStateHandle?.set("article", article)
                    navController.navigate("details")
                }
            )
        }

        composable("details") {
            val article = navController.previousBackStackEntry
                ?.savedStateHandle?.get<Article>("article")

            if (article != null) {
                ArticleDetailsScreen(
                    article = article,
                    onBackClick = { navController.popBackStack() }
                )
            }
        }
    }
}

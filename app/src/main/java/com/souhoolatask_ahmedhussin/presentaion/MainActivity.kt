package com.souhoolatask_ahmedhussin.presentaion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.souhoolatask_ahmedhussin.presentaion.news.NewsScreenSetup
import com.souhoolatask_ahmedhussin.ui.theme.SouhoolaTask_AhmedHussinTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SouhoolaTask_AhmedHussinTheme {
                Column(modifier = Modifier.padding(10.dp).fillMaxSize()) {
                    NewsScreenSetup()
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SouhoolaTask_AhmedHussinTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            NewsScreenSetup()

        }
    }
}
package com.example.superjet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import coil.annotation.ExperimentalCoilApi
import com.example.superjet.navigation.NavigationGraph
import com.example.superjet.ui.theme.SuperJetTheme
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint


@ExperimentalCoilApi
@ExperimentalComposeUiApi
@ExperimentalMaterial3Api
@ExperimentalAnimationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SuperJetTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberAnimatedNavController()
                    NavigationGraph(navHostController = navController)
                }
            }
        }
    }
}


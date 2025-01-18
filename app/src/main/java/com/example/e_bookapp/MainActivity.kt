package com.example.e_bookapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.e_bookapp.ui.theme.EBookAppTheme
import com.example.e_bookapp.ui_layer.navigation.AppNavigation
import com.example.e_bookapp.ui_layer.screens.HomeScreenUI
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EBookAppTheme {
                    Box(modifier = Modifier.fillMaxSize()){
                        AppNavigation()
                    }
            }
        }
    }
}


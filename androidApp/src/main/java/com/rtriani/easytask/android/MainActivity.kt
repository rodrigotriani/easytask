package com.rtriani.easytask.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.rtriani.easytask.android.navigation.TodoNavHost
import com.rtriani.easytask.android.ui.theme.EasyTaskTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        installSplashScreen()

        setTheme(R.style.Theme_Splash)

        setContent {
            Box(
                modifier = Modifier
                    .safeDrawingPadding()
            ) {
                EasyTaskTheme {
                    TodoNavHost()
                }
            }
        }
    }
}
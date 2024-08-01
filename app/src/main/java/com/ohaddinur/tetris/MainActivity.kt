package com.ohaddinur.tetris

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.addCallback
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ohaddinur.tetris.ui.theme.STheme
import androidx.compose.runtime.Composable

import com.ohaddinur.tetris.view.Game
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.onBackPressedDispatcher.addCallback(this) {
            // Do nothing
        }
        setContent {
            STheme {
                MyApp(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
fun MyApp(modifier: Modifier = Modifier.padding(0.dp)) {
            Game()
}

package com.masliaiev.papuga

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.masliaiev.core.models.player.Player
import com.masliaiev.core.ui.theme.PapugaTheme
import com.masliaiev.papuga.navigation.AppRootNavigation
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var player: Player

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            PapugaTheme {
                AppRootNavigation()
            }
        }
        player.initialize(this, lifecycle)
    }
}
package com.masliaiev.papuga

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.masliaiev.core.models.player.Player
import com.masliaiev.core.theme.PapugaTheme
import com.masliaiev.papuga.navigation.AppRootNavigation
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var player: Player

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            PapugaTheme {
                AppRootNavigation()
            }
        }
        player.initialize(this, lifecycle)
    }
}
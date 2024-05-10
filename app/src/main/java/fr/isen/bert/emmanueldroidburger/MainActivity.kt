package fr.isen.bert.emmanueldroidburger

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import fr.isen.bert.emmanueldroidburger.command.CommandeBurger
import fr.isen.bert.emmanueldroidburger.ui.theme.EmmanuelDroidBurgerTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            EmmanuelDroidBurgerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    CommandeBurger()
                }
            }
        }
    }
}
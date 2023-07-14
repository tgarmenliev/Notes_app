package com.example.to_do_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.to_do_app.ui.add.AddNotes
import com.example.to_do_app.ui.homescreen.NavigationView
import com.example.to_do_app.ui.list.Notes
import com.example.to_do_app.ui.theme.To_do_appTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            To_do_appTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //MyApp()

                    //Home()

                    NavigationView()

                    //AddNotes(add = { /*TODO*/ }, cancel = { /*TODO*/ })
                    
                    //Registration(button = { /*TODO*/ })

                    //Login()

                    //PasswordScreen()
                }
            }
        }
    }
}

@Composable
fun MyApp(modifier: Modifier = Modifier) {
    var showOnboarding by rememberSaveable {
        mutableStateOf(true)
    }

    var showOnboardingLogin by rememberSaveable {
        mutableStateOf(true)
    }

    if (showOnboarding && showOnboardingLogin) {
        OnboardingScreen(onContinueClicked = { showOnboarding = false })
    } else {
        Notes({})
    }
}

@Composable
fun OnboardingScreen(
    onContinueClicked: () -> Unit,
    modifier: Modifier = Modifier
) {


    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome to the Basics Codelab!")
        Button(
            modifier = Modifier
                .padding(vertical = 24.dp),
            onClick = onContinueClicked,
        ) {
            Text("Continue", style = MaterialTheme.typography.labelMedium)
        }
    }

}
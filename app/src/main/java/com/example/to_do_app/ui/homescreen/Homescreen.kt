package com.example.to_do_app.ui.homescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.to_do_app.LoginViewModel
import com.example.to_do_app.R
import com.example.to_do_app.ui.add.AddNotes
import com.example.to_do_app.ui.list.Notes
import com.example.to_do_app.ui.login.Login
import com.example.to_do_app.ui.registration.Registration
import com.example.to_do_app.ui.theme.buttonEnabled

@Composable
fun NavigationView() {
    val navController = rememberNavController()
    var LoginViewModel = LoginViewModel()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            Home(navController)
        }
        composable("login") {
            Login(
                viewModel = LoginViewModel,
                button = { navController.navigate("notes") }
            )
        }
        composable("registration") {
            Registration(
                button = { navController.navigate("notes") }
            )
        }
        composable("add") {
            AddNotes(
                add = { navController.navigate("notes") },
                cancel = { navController.navigate("notes") }
            )
        }
        composable("notes") {
            Notes(
                addButton = { navController.navigate("add") }
            )
        }
    }
}

@Composable
fun Home(navController: NavController, modifier: Modifier = Modifier) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.homescreen),
            contentDescription = stringResource(id = R.string.homescreen),
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Notey",
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(top = 80.dp)
            )

            ConstraintLayout(modifier = modifier.fillMaxSize()) {
                val (button1, button2) = createRefs()
                Button(
                    modifier = Modifier
                        .constrainAs(button1) {
                            bottom.linkTo(parent.bottom)
                        }
                        .padding(start = 30.dp, end = 30.dp, bottom = 90.dp)
                        .fillMaxWidth(),
                    onClick = { navController.navigate("login") },
                    colors = ButtonDefaults.buttonColors(
                        buttonEnabled,
                        contentColor = Color.White
                    )
                ) {
                    Text("Login", style = MaterialTheme.typography.headlineMedium)
                }

                Button(
                    modifier = Modifier
                        .constrainAs(button2) {
                            bottom.linkTo(button1.top)
                        }
                        .padding(start = 30.dp, end = 30.dp, bottom = 30.dp)
                        .fillMaxWidth(),
                    onClick = { navController.navigate("registration") },
                    colors = ButtonDefaults.buttonColors(
                        buttonEnabled,
                        contentColor = Color.White
                    )
                ) {
                    Text("Register", style = MaterialTheme.typography.headlineMedium)
                }
            }
        }
    }
}


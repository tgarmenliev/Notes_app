package com.example.to_do_app.ui.login

import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.to_do_app.LoginViewModel
import com.example.to_do_app.R
import com.example.to_do_app.rememberImeState
import com.example.to_do_app.ui.theme.buttonEnabled
import com.example.to_do_app.ui.theme.greenCorrect
import com.example.to_do_app.ui.theme.helper
import com.example.to_do_app.ui.theme.textG
import com.example.to_do_app.ui.theme.themeBackground


@Composable
fun Login(viewModel: LoginViewModel, button: () -> Unit, modifier: Modifier = Modifier) {

    var emailText by rememberSaveable { mutableStateOf("") }
    var showEmailError by remember { mutableStateOf(false) }
    showEmailError = false

    var passwordText by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }
    showError = false

    var enabledMail by remember { mutableStateOf(false) }
    enabledMail = false

    var enabledPass by remember { mutableStateOf(false) }
    enabledPass = false

    val imeState = rememberImeState()
    val scrollState = rememberScrollState()

    LaunchedEffect(key1 = imeState.value) {
        if (imeState.value){
            scrollState.animateScrollTo(scrollState.maxValue, tween(300))
        }
    }

    ConstraintLayout(modifier.verticalScroll(scrollState)) {
        val (image, text1, text2, email, password, login) = createRefs()

        Image(painter = painterResource(id = R.drawable.note_login), contentDescription = "Note login",
            modifier = modifier
                .constrainAs(image) {
                    top.linkTo(parent.top, margin = 40.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .size(width = 160.dp, height = 160.dp),
            alignment = Alignment.Center
        )

        Text(text = "Welcome to Notey!", modifier = modifier.constrainAs(text1) {
            top.linkTo(image.bottom, margin = 50.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        },
            style = MaterialTheme.typography.headlineLarge,
            color = textG
        )

        Text(text = "Login", modifier = modifier.constrainAs(text2) {
            top.linkTo(text1.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        },
            style = MaterialTheme.typography.headlineSmall,
            color = textG
        )

        var mailColor by remember { mutableStateOf(buttonEnabled) }

        MakeEmailField(modifier = Modifier
            .padding(horizontal = 40.dp)
            .constrainAs(email) {
                top.linkTo(text2.bottom, margin = 30.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            emailText = emailText,
            onEmailChange = {
                    newEmail ->
                emailText = newEmail
                showEmailError = !isValidEmail(newEmail)
            },
            showEmailError = showEmailError,
            mailColor,
            changeColor = {
                mailColor = getEmailColor(emailText)
            },
            changeColorEnable = {
                mailColor = buttonEnabled
            },
            enableButton = { isValidEmail ->
                if(isValidEmail) {
                    enabledMail = true
                }
            }
        )


        var passwordColor by remember { mutableStateOf(buttonEnabled) }

        MakePasswordField(
            modifier = Modifier
                .padding(horizontal = 40.dp)
            .constrainAs(password) {
                top.linkTo(email.bottom, margin = 16.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            passwordText = passwordText,
            onPasswordChange = { newPassword ->
                passwordText = newPassword
                showError = !isValidPassword(newPassword)
            },
            showPasswordError = showError,
            passwordColor = passwordColor,
            onPasswordVisibilityToggle = { passwordVisible = !passwordVisible },
            passwordVisible = passwordVisible,
            changeColor = { passwordColor = getColor(passwordText) },
            changeColorEnable = { passwordColor = buttonEnabled },
            enableButton = { isValidPassword ->
                if(isValidPassword) {
                    enabledPass = true
                }
            }
        )

        viewModel.setEmail(emailText)
        viewModel.setPassword(passwordText)

        Button(
            onClick = {
                button()
                viewModel.login()
            },
            modifier = Modifier
                .padding(horizontal = 40.dp)
                .constrainAs(login) {
                    top.linkTo(password.bottom, margin = 40.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(32.dp),
            colors = ButtonDefaults.buttonColors(
                buttonEnabled,
                contentColor = Color.White
            ),
            enabled = enabledMail && enabledPass
        ) {
            Text(
                text = "Log in",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )
        }

    }
}


@Composable

fun MakeEmailField( modifier: Modifier = Modifier,
                    emailText: String,
                    onEmailChange: (String) -> Unit,
                    showEmailError: Boolean,
                    mailColor: Color,
                    changeColor: () -> Unit,
                    changeColorEnable: () -> Unit,
                    enableButton: (Boolean) -> Unit
) {

    Column(
        modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(themeBackground, RoundedCornerShape(32.dp))
                .border(
                    width = 2.dp,
                    color = mailColor,
                    shape = RoundedCornerShape(32.dp)
                )
                .padding(horizontal = 16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 1.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "Mail icon",
                    tint = Color.DarkGray,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                BasicTextField(
                    value = emailText,
                    onValueChange = onEmailChange,
                    modifier = Modifier
                        .weight(1f)
                        .padding(top = 16.dp, bottom = 16.dp),
                    textStyle = MaterialTheme.typography.headlineMedium,
                    singleLine = true,
                    cursorBrush = SolidColor(Color.Black),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    )
                )
            }
        }

        if (showEmailError) {
            Text(
                text = getEmailMessage(emailText) ?: "Valid email",
                color = getEmailColor(emailText),
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
            changeColor()
            enableButton(mailColor == greenCorrect)
        } else {
            Text(
                text = "Enter your email",
                color = buttonEnabled,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
            changeColorEnable()
        }
    }
}



@Composable
fun MakePasswordField(
    modifier: Modifier = Modifier,
    passwordText: String,
    onPasswordChange: (String) -> Unit,
    showPasswordError: Boolean,
    passwordColor: Color,
    onPasswordVisibilityToggle: () -> Unit,
    passwordVisible: Boolean,
    changeColor: () -> Unit,
    changeColorEnable: () -> Unit,
    enableButton: (Boolean) -> Unit
) {
    Column(
        modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(themeBackground, RoundedCornerShape(32.dp))
                .border(
                    width = 2.dp,
                    color = passwordColor,
                    shape = RoundedCornerShape(32.dp)
                )
                .padding(horizontal = 16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 1.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.padlock),
                    contentDescription = "Password icon",
                    tint = Color.DarkGray,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                BasicTextField(
                    value = passwordText,
                    onValueChange = onPasswordChange,
                    modifier = Modifier
                        .weight(1f)
                        .padding(top = 16.dp, bottom = 16.dp), // Adjust the padding values as needed
                    textStyle = MaterialTheme.typography.headlineMedium,
                    singleLine = true,
                    cursorBrush = SolidColor(Color.Black),
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(mask = '*'),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    )
                )
                IconButton(
                    onClick = onPasswordVisibilityToggle,
                    modifier = Modifier.size(24.dp)
                ) {
                    val icon = if (passwordVisible) painterResource(id = R.drawable.unvisible) else painterResource(id = R.drawable.visible)
                    Icon(
                        painter = icon,
                        contentDescription = "Toggle password visibility",
                        tint = Color.DarkGray
                    )
                }
            }
        }

        if (showPasswordError) {
            Text(

                text = getPasswordErrorMessage(passwordText) ?: "Valid password",
                color = getColor(passwordText),
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
            changeColor()
            enableButton(passwordColor == greenCorrect)
        }
        else {
            Text(
                text = "Enter your password",
                color = buttonEnabled,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
            changeColorEnable()
        }
    }
}

fun isValidPassword(password: String): Boolean {
    // Implement your password validation logic here
    // Return true if the password is valid, false otherwise
    if (!password.matches(Regex("[!@#\$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]")))
        return false
    if (!password.matches(Regex(".*[A-Z].*")))
        return false
    if (!password.matches(Regex(".*[a-z].*")))
        return false
    if (!password.matches(Regex(".*\\d.*")))
        return false
    if (!password.matches(Regex(".{6,}")))
        return false
    return true
}

fun getPasswordErrorMessage(password: String): String? {
    if (password.isEmpty()) {
        return "Password cannot be empty"
    }

    if (!password.matches(Regex(".*[A-Z].*"))) {
        return "Password must contain at least one uppercase letter"
    }

    if (!password.matches(Regex(".*[a-z].*"))) {
        return "Password must contain at least one lowercase letter"
    }

    if (!password.matches(Regex(".*\\d.*"))) {
        return "Password must contain at least one digit"
    }

    if (!password.matches(Regex(".*[!@#\$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*"))) {
        return "Password must contain at least one special character"
    }

    if (!password.matches(Regex(".{6,}"))) {
        return "Password must be at least 6 characters long"
    }

    return null
}

fun getColor(password: String): Color {
    if (getPasswordErrorMessage(password) == null) {
        return greenCorrect
    }
    return helper
}

fun isValidEmail(email: String): Boolean {
    // Implement your email validation logic here
    // Return true if the email is valid, false otherwise
    if (email.isEmpty()) {
        return false
    }
    return false
}

fun getEmailMessage(email: String): String? {
    if (email.isEmpty()) {
        return "Email cannot be empty"
    }

    return null
}

fun getEmailColor(email: String): Color {
    if (getEmailMessage(email) == null) {
        println("yibrewrrrrrrrrrrrrrrr")
        return greenCorrect
    }
    println("NOOOOOOOOOOOOOOOOOOOOOOOOO")
    return helper
}
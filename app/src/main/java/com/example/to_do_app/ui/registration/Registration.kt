package com.example.to_do_app.ui.registration

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
import com.example.to_do_app.R
import com.example.to_do_app.rememberImeState
import com.example.to_do_app.ui.theme.buttonEnabled
import com.example.to_do_app.ui.theme.greenCorrect
import com.example.to_do_app.ui.theme.helper
import com.example.to_do_app.ui.theme.textG
import com.example.to_do_app.ui.theme.themeBackground


@Composable
fun Registration(button: () -> Unit, modifier: Modifier = Modifier) {

    var nameText by rememberSaveable { mutableStateOf("") }
    var showNameError by remember { mutableStateOf(false) }
    showNameError = false

    var emailText by rememberSaveable { mutableStateOf("") }
    var showEmailError by remember { mutableStateOf(false) }
    showEmailError = false

    var passwordText by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }
    showError = false

    var repasswordText by rememberSaveable { mutableStateOf("") }
    var repasswordVisible by rememberSaveable { mutableStateOf(false) }
    var showRepasswordError by remember { mutableStateOf(false) }
    showRepasswordError = false

    var enabledMail by remember { mutableStateOf(false) }
    enabledMail = false

    var enabledPass by remember { mutableStateOf(false) }
    enabledPass = false

    var enabledName by remember { mutableStateOf(false) }
    enabledName = false

    var enabledRepass by remember { mutableStateOf(false) }
    enabledRepass = false

    val imeState = rememberImeState()
    val scrollState = rememberScrollState()

    LaunchedEffect(key1 = imeState.value) {
        if (imeState.value){
            scrollState.animateScrollTo(scrollState.maxValue, tween(300))
        }
    }


    ConstraintLayout(modifier.verticalScroll(scrollState)) {
        val (image, text1, text2, name, email, password, repassword, registration) = createRefs()

        Image(painter = painterResource(id = R.drawable.key), contentDescription = "Note login",
            modifier = modifier
                .constrainAs(image) {
                    top.linkTo(parent.top, margin = 20.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .size(width = 120.dp, height = 120.dp),
            alignment = Alignment.Center
        )

        Text(text = "Welcome to Notey!", modifier = modifier.constrainAs(text1) {
            top.linkTo(image.bottom, margin = 5.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        },
            style = MaterialTheme.typography.headlineLarge,
            color = textG
        )

        Text(text = "Registration", modifier = modifier.constrainAs(text2) {
            top.linkTo(text1.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        },
            style = MaterialTheme.typography.headlineSmall,
            color = textG
        )


        var nameColor by remember { mutableStateOf(buttonEnabled) }

        MakeUserField(modifier = Modifier
            .padding(horizontal = 40.dp)
            .constrainAs(name) {
                top.linkTo(text2.bottom, margin = 30.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            nameText = nameText,
            onNameChange = { newName ->
                nameText = newName
                showNameError = !isValidEmail(newName)
            },
            showNameError = showNameError,
            userColor = nameColor,
            changeColor = {
                nameColor = getEmailColor(nameText)
            },
            changeColorEnable = {
                nameColor = buttonEnabled
            },
            enableButton = { isValidName ->
                if(isValidName) {
                    enabledName = true
                }
            }
        )


        var mailColor by remember { mutableStateOf(buttonEnabled) }

        MakeEmailFieldRegistration(modifier = Modifier
            .padding(horizontal = 40.dp)
            .constrainAs(email) {
                top.linkTo(name.bottom, margin = 4.dp)
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

        MakePasswordFieldRegistration(
            modifier = Modifier
                .padding(horizontal = 40.dp)
                .constrainAs(password) {
                    top.linkTo(email.bottom, margin = 4.dp)
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


        var repasswordColor by remember { mutableStateOf(buttonEnabled) }

        MakeRepasswordField(
                modifier = Modifier
                .padding(horizontal = 40.dp)
            .constrainAs(repassword) {
                top.linkTo(password.bottom, margin = 4.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            passwordText = passwordText,
            repasswordText = repasswordText,
            onRepasswordChange = { newRepassword ->
                repasswordText = newRepassword
                showRepasswordError = !isValidPassword(newRepassword)
            },
            showRepasswordError = showRepasswordError,
            repasswordColor = repasswordColor,
            onRepasswordVisibilityToggle = { repasswordVisible = !repasswordVisible },
            repasswordVisible = repasswordVisible,
            changeColor = { repasswordColor = getReColor(passwordText, repasswordText) },
            changeColorEnable = { repasswordColor = buttonEnabled },
            enableButton = {isValidRepassword ->
                if(isValidRepassword) {
                    enabledRepass = true
                }
            }
        )


        Button(
            onClick = button,
            modifier = Modifier
                .padding(horizontal = 40.dp)
                .constrainAs(registration) {
                    top.linkTo(repassword.bottom, margin = 40.dp)
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
            enabled = enabledMail && enabledPass && enabledName && enabledRepass
        ) {
            Text(
                text = "Register",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )
        }

    }
}


@Composable
fun MakeUserField(
    modifier: Modifier = Modifier,
    nameText: String,
    onNameChange: (String) -> Unit,
    showNameError: Boolean,
    userColor: Color,
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
                    color = userColor,
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
                    painter = painterResource(id = R.drawable.user),
                    contentDescription = "User icon",
                    tint = Color.DarkGray,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                BasicTextField(
                    value = nameText,
                    onValueChange = onNameChange,
                    modifier = Modifier
                        .weight(1f)
                        .padding(top = 16.dp, bottom = 16.dp), // Adjust the padding values as needed
                    textStyle = MaterialTheme.typography.headlineMedium,
                    singleLine = true,
                    cursorBrush = SolidColor(Color.Black),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    )
                )
            }
        }

        if (showNameError) {
            Text(
                text = getNameMessage(nameText) ?: "Valid name",
                color = getEmailColor(nameText),
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
            changeColor()
            enableButton(userColor == greenCorrect)
        }
        else {
            Text(
                text = "Enter your name",
                color = buttonEnabled,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
            changeColorEnable()
        }
    }
}



@Composable
fun MakeEmailFieldRegistration(
        modifier: Modifier = Modifier,
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
                        keyboardType = KeyboardType.Email,
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
fun MakePasswordFieldRegistration(
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


@Composable
fun MakeRepasswordField(
    modifier: Modifier = Modifier,
    passwordText: String,
    repasswordText: String,
    onRepasswordChange: (String) -> Unit,
    showRepasswordError: Boolean,
    repasswordColor: Color,
    onRepasswordVisibilityToggle: () -> Unit,
    repasswordVisible: Boolean,
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
                    color = repasswordColor,
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
                    contentDescription = "Repassword icon",
                    tint = Color.DarkGray,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                BasicTextField(
                    value = repasswordText,
                    onValueChange = onRepasswordChange,
                    modifier = Modifier
                        .weight(1f)
                        .padding(top = 16.dp, bottom = 16.dp), // Adjust the padding values as needed
                    textStyle = MaterialTheme.typography.headlineMedium,
                    singleLine = true,
                    cursorBrush = SolidColor(Color.Black),
                    visualTransformation = if (repasswordVisible) VisualTransformation.None else PasswordVisualTransformation(mask = '*'),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    )
                )
                IconButton(
                    onClick = onRepasswordVisibilityToggle,
                    modifier = Modifier.size(24.dp)
                ) {
                    val icon = if (repasswordVisible) painterResource(id = R.drawable.unvisible) else painterResource(id = R.drawable.visible)
                    Icon(
                        painter = icon,
                        contentDescription = "Toggle repassword visibility",
                        tint = Color.DarkGray
                    )
                }
            }
        }

        if (showRepasswordError) {
            Text(

                text = if (passwordText == repasswordText) "Match" else "Passwords doesn't match",
                color = getReColor(passwordText, repasswordText),
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
            changeColor()
            enableButton(repasswordColor == greenCorrect)
        }
        else {
            Text(
                text = "Re-enter your password",
                color = buttonEnabled,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
            changeColorEnable()
        }
    }
}


fun isValidPassword(password: String): Boolean {
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

fun getReColor(password: String, repassword: String): Color {
    if (password == repassword) {
        return greenCorrect
    }
    return helper
}

fun isValidEmail(email: String): Boolean {
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

fun getNameMessage(name: String): String? {
    if (name.isEmpty()) {
        return "Name cannot be empty"
    }

    return null
}

fun getEmailColor(email: String): Color {
    if (getEmailMessage(email) == null) {
        return greenCorrect
    }
    return helper
}
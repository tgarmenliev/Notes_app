package com.example.to_do_app.ui.add

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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.to_do_app.R
import com.example.to_do_app.rememberImeState
import com.example.to_do_app.ui.registration.MakeUserField
import com.example.to_do_app.ui.registration.getEmailColor
import com.example.to_do_app.ui.registration.isValidEmail
import com.example.to_do_app.ui.theme.buttonCancel
import com.example.to_do_app.ui.theme.buttonEnabled
import com.example.to_do_app.ui.theme.greenCorrect
import com.example.to_do_app.ui.theme.helper
import com.example.to_do_app.ui.theme.textG
import com.example.to_do_app.ui.theme.themeBackground

@Composable
fun AddNotes(add: () -> Unit, cancel: () -> Unit, modifier: Modifier = Modifier) {
    var titleText by rememberSaveable { mutableStateOf("") }
    var showTitleError by rememberSaveable { mutableStateOf(false) }
    showTitleError = false

    var enabledTitle by rememberSaveable { mutableStateOf(false) }
    enabledTitle = false

    var descriptionText by rememberSaveable { mutableStateOf("") }
    var showDescriptionError by rememberSaveable { mutableStateOf(false) }
    showDescriptionError = false

    var enabledDescription by rememberSaveable { mutableStateOf(false) }
    enabledDescription = false

    val imeState = rememberImeState()
    val scrollState = rememberScrollState()

    LaunchedEffect(key1 = imeState.value) {
        if (imeState.value){
            scrollState.animateScrollTo(scrollState.maxValue, tween(300))
        }
    }


    ConstraintLayout(modifier.verticalScroll(scrollState)) {
        val (image, text, title, description, addButton, cancelButton) = createRefs()

        Image(painter = painterResource(id = R.drawable.add_note_final), contentDescription = "Add note",
            modifier = modifier
                .constrainAs(image) {
                    top.linkTo(parent.top, margin = 30.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .size(width = 140.dp, height = 140.dp),
            alignment = Alignment.Center
        )

        Text (text = "Add new note", modifier = modifier.constrainAs(text) {
            top.linkTo(image.bottom, margin = 10.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            },
            style = MaterialTheme.typography.headlineLarge,
            color = textG
        )


        var titleColor by remember { mutableStateOf(buttonEnabled) }

        MakeTitleField(modifier = Modifier
            .padding(horizontal = 40.dp)
            .constrainAs(title) {
                top.linkTo(text.bottom, margin = 80.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            titleText = titleText,
            onTitleChange = { newTitle ->
                titleText = newTitle
                showTitleError = !isValidTitle(newTitle)
            },
            showTitleError = showTitleError,
            titleColor = titleColor,
            changeColor = {
                titleColor = getTitleColor(titleText)
            },
            changeColorEnable = {
                titleColor = buttonEnabled
            },
            enableButton = { isValidTitle ->
                if(isValidTitle) {
                    enabledTitle = true
                }
            },
            stringMessageOne = "Valid title",
            stringMessageTwo = "Enter the title of your new note :)",
            painter = painterResource(id = R.drawable.button),
        )


        var descriptionColor by remember { mutableStateOf(buttonEnabled) }

        MakeTitleField(modifier = Modifier
            .padding(horizontal = 40.dp)
            .constrainAs(description) {
                top.linkTo(title.bottom, margin = 10.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            titleText = descriptionText,
            onTitleChange = { newDescription ->
                descriptionText = newDescription
                showDescriptionError = !isValidTitle(newDescription)
            },
            showTitleError = showDescriptionError,
            titleColor = descriptionColor,
            changeColor = {
                descriptionColor = getTitleColor(descriptionText)
            },
            changeColorEnable = {
                descriptionColor = buttonEnabled
            },
            enableButton = { isValidDescription ->
                if(isValidDescription) {
                    enabledDescription = true
                }
            },
            stringMessageOne = "Valid description",
            stringMessageTwo = "Enter the description of your new note :)",
            painter = painterResource(id = R.drawable.product_description),
        )


        Button(
            onClick = add,
            modifier = Modifier
                .padding(horizontal = 40.dp)
                .constrainAs(addButton) {
                    top.linkTo(description.bottom, margin = 30.dp)
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
            enabled = enabledTitle && enabledDescription
        ) {
            Text(
                text = "Add note",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )
        }

        Button(
            onClick = cancel,
            modifier = Modifier
                .padding(horizontal = 40.dp)
                .constrainAs(cancelButton) {
                    top.linkTo(addButton.bottom, margin = 50.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth(),
            shape = RoundedCornerShape(32.dp),
            colors = ButtonDefaults.buttonColors(
                buttonCancel,
                contentColor = Color.White
            ),
            enabled = true
        ) {
            Text(
                text = "Cancel",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )
        }
    }
}


@Composable
fun MakeTitleField(
    modifier: Modifier = Modifier,
    titleText: String,
    onTitleChange: (String) -> Unit,
    showTitleError: Boolean,
    titleColor: Color,
    changeColor: () -> Unit,
    changeColorEnable: () -> Unit,
    enableButton: (Boolean) -> Unit,
    stringMessageOne: String,
    stringMessageTwo: String,
    painter: Painter
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
                    color = titleColor,
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
                    painter = painter,
                    contentDescription = "Title icon",
                    tint = Color.DarkGray,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                BasicTextField(
                    value = titleText,
                    onValueChange = onTitleChange,
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

        if (showTitleError) {
            Text(
                text = getTitleMessage(titleText) ?: stringMessageOne,
                color = getTitleColor(titleText),
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
            changeColor()
            enableButton(titleColor == greenCorrect)
        }
        else {
            Text(
                text = stringMessageTwo,
                color = buttonEnabled,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
            changeColorEnable()
        }
    }
}


fun isValidTitle(title: String): Boolean {
    if (title.isEmpty()) {
        return false
    }
    return false
}

fun getTitleMessage(title: String): String? {
    if (title.isEmpty()) {
        return "Title cannot be empty"
    }

    return null
}

fun getTitleColor(title: String): Color {
    if (getTitleMessage(title) == null) {
        return greenCorrect
    }
    return helper
}

@Preview
@Composable
fun AddNotePreview() {
    AddNotes(
        add = {},
        cancel = {}
    )
}
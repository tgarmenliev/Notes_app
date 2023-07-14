package com.example.to_do_app.ui.list

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.to_do_app.R
import com.example.to_do_app.ui.theme.textG
import com.example.to_do_app.ui.theme.themeBackground

@Composable
fun Notes(addButton: () -> Unit, modifier: Modifier = Modifier, names: List<String> = List(1000) { "$it" } ) {

    ConstraintLayout {
        val (title, button, list) = createRefs()

        Text(
            text = "Add",
            style = MaterialTheme.typography.headlineMedium,
            color = textG,
            modifier = Modifier.constrainAs(title) {
                top.linkTo(parent.top, margin = 15.dp)
                start.linkTo(parent.start, margin = 20.dp)
            }
        )

        IconButton(
            onClick = addButton,
            modifier = Modifier.size(35.dp)
                .constrainAs(button) {
                    top.linkTo(parent.top, margin = 15.dp)
                    end.linkTo(parent.end, margin = 15.dp)
                }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.button),
                contentDescription = "Add note button",
                tint = Color.DarkGray
            )
        }

        LazyColumn(modifier = modifier.padding(vertical = 4.dp)
            .constrainAs(list) {
                top.linkTo(title.bottom, margin = 50.dp)
                start.linkTo(parent.start, margin = 5.dp)
                end.linkTo(parent.end, margin = 5.dp)
                bottom.linkTo(parent.bottom, margin = 5.dp)
            }){
            items(items = names) { name ->
                MakeNote(name = name)
            }
        }

    }


}

@Composable
fun MakeNote(name: String, modifier: Modifier = Modifier) {
    val expandedSection = remember {
        mutableStateOf(false)
    }

    val extraPadding by animateDpAsState(
        if (expandedSection.value) 48.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    Surface(color = MaterialTheme.colorScheme.surface, modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)) {
        Row (modifier = Modifier.padding(24.dp)) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = extraPadding.coerceAtLeast(0.dp))
            ) {
                Text(text = "Note", style = MaterialTheme.typography.labelSmall)
                Text(text = name, style = MaterialTheme.typography.titleLarge)
            }
            ElevatedButton(onClick = { expandedSection.value = !expandedSection.value }, colors = ButtonDefaults.elevatedButtonColors(
                themeBackground, contentColor = textG
            )) {
                Text(if (expandedSection.value) "Hide" else "Show description", style = MaterialTheme.typography.labelMedium)
            }
        }
    }
}
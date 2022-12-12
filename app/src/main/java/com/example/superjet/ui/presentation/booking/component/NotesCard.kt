package com.example.superjet.ui.presentation.booking.component

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.superjet.util.Constants

@SuppressLint("UnusedTransitionTargetStateParameter")
@ExperimentalAnimationApi
@ExperimentalMaterial3Api
@Composable
fun NotesCard(
    onValueChange: (String) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }



    val transitionState = remember {
        MutableTransitionState(expanded).apply {
            targetState = !expanded
        }
    }
    val transition = updateTransition(transitionState, label = "")

    val cardPaddingHorizontal by transition.animateDp(
        { tween(durationMillis = 500) },
        label = "", targetValueByState = { if (expanded) 20.dp else 15.dp }
    )
    val cardElevation by transition.animateDp(
        { tween(durationMillis = 500) },
        label = ""
    ) { if (expanded) 10.dp else 5.dp }

    val cardRoundedCorners by transition.animateDp(
        { tween(durationMillis = 500, easing = FastOutSlowInEasing) },
        label = ""
    ) { if (expanded) 0.dp else 16.dp }

    val arrowRotationDegree by transition.animateFloat(
        { tween(durationMillis = 500) },
        label = ""
    )
    { if (expanded) 0f else 180f }

    Column(modifier = Modifier.fillMaxWidth()) {

        Text(text = "Add any additional information",
            style = TextStyle(
                color = MaterialTheme.colorScheme.secondary,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontFamily =  FontFamily(Constants.MEDIUM_FONT)
            ),
            modifier = Modifier.padding(horizontal = 5.dp)
        )

        Card(
            shape = RoundedCornerShape(cardRoundedCorners),
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 8.dp,
                    horizontal = cardPaddingHorizontal
                ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = cardElevation
            ),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.onPrimary
            )
            ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = MaterialTheme.colorScheme.secondary),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "like somewhere,You'll get off the bus ",
                        modifier = Modifier
                            .padding(start = 10.dp)
                            .weight(3f),
                        style = TextStyle(
                            color = MaterialTheme.colorScheme.onSecondary,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp
                        )
                    )
                    IconButton(
                        onClick = {
                            expanded = !expanded
                        },
                        content = {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowDown,
                                contentDescription = "Expandable Arrow",
                                modifier = Modifier
                                    .rotate(arrowRotationDegree)
                                    .padding(end = 5.dp)
                                    .weight(1f),
                                tint = MaterialTheme.colorScheme.onSecondary
                            )
                        }
                    )
                }
                ExpandableContent(visible = expanded, initialVisibility = expanded,
                    value = "",onValueChange = {
                        onValueChange(it)
                    }
                )
            }
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun ExpandableContent(
    visible: Boolean = true,
    initialVisibility: Boolean = false,
    value:String,
    onValueChange: (String) -> Unit,
) {

    val enterTransition = remember {
        expandVertically(
            expandFrom = Alignment.Top,
            animationSpec = tween(1000)
        ) + fadeIn(
            initialAlpha = 0.3f,
            animationSpec = tween(1000)
        )
    }
    val exitTransition = remember {
        shrinkVertically(
            shrinkTowards = Alignment.Top,
            animationSpec = tween(1000)
        ) + fadeOut(
            animationSpec = tween(1000)
        )
    }
    AnimatedVisibility(
        visible = visible,
        initiallyVisible = initialVisibility,
        enter = enterTransition,
        exit = exitTransition
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            TextField(value = value,
                onValueChange = { onValueChange(it) },
            placeholder = {
                Text(text = "Write Some notes here")
            },
            colors = TextFieldDefaults.textFieldColors(
                textColor = MaterialTheme.colorScheme.secondary,
                containerColor = MaterialTheme.colorScheme.onSecondary,

            ))
        }
    }
}


package com.example.superjet.ui.presentation.regestration.component

import androidx.annotation.StringRes
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.stringResource
import com.example.superjet.R

@Composable
fun SimpleProgressDialog(
    showDialog: MutableState<Boolean>
) {
    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = {},
            text = { CircularProgressIndicator() },
            buttons = {}
        )
    }
}

@Composable
fun SimpleDialog(
    showDialog: MutableState<Boolean>,
    @StringRes titleResId: Int,
    @StringRes messageResId: Int,
    onClickButtonOk: () -> Unit,
    onClickButtonCancel: () -> Unit
) {
    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = {
                Text(
                    text = stringResource(id = titleResId),
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.secondary
                )
            },
            text = {
                Text(
                    text = stringResource(id = messageResId),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary,
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDialog.value = false
                        onClickButtonOk.invoke()
                    }
                ) {
                    Text(
                        text = stringResource(id = R.string.button_ok),
                        color =  MaterialTheme.colorScheme.secondary
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDialog.value = false
                        onClickButtonCancel.invoke()
                    }
                ) {
                    Text(
                        text = stringResource(id = R.string.button_cancel).uppercase(),
                        color =  MaterialTheme.colorScheme.secondary
                    )
                }
            }
        )
    }
}

@Composable
fun ResetPasswordDialog(
    showDialog: MutableState<Boolean>,
    @StringRes titleResId: Int,
    email: String,
    onClickButtonOk: () -> Unit,
    onClickButtonCancel: () -> Unit
) {
    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = {
                Text(
                    text = stringResource(id = titleResId),
                    style = MaterialTheme.typography.headlineMedium,
                    color =  MaterialTheme.colorScheme.secondary,
                )
            },
            text = {
                Text(
                    text = email,
                    style = MaterialTheme.typography.bodyMedium,
                    color =  MaterialTheme.colorScheme.secondary,
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDialog.value = false
                        onClickButtonOk.invoke()
                    }
                ) {
                    Text(
                        text = stringResource(id = R.string.button_send),
                        color =  MaterialTheme.colorScheme.secondary
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDialog.value = false
                        onClickButtonCancel.invoke()
                    }
                ) {
                    Text(
                        text = stringResource(id = R.string.button_cancel).uppercase(),
                        color =  MaterialTheme.colorScheme.secondary
                    )
                }
            }
        )
    }
}
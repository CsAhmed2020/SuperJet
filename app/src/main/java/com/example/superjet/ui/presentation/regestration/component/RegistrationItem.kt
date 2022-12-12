package com.example.superjet.ui.presentation.regestration.component


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.superjet.R
import com.example.superjet.ui.theme.Purple500


@ExperimentalMaterial3Api
@Composable
fun RegistrationItem(
    value:String,
    label: String= "",
    hint:String="",
    icon: ImageVector,
    onValueChange: (String) -> Unit,
    isInPasswordField: Boolean =  false,
    isPasswordVisible: Boolean = false,
    onVisibilityChange: (Boolean) -> Unit = {},
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
){

    Card(modifier = Modifier.fillMaxWidth()
        .padding(horizontal = 10.dp,vertical = 5.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onPrimary,
        ),
        shape = MaterialTheme.shapes.medium,
        )
    {
        Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = value,
                    onValueChange = { onValueChange(it) },
                    label = { Text(label) },
                    placeholder = { Text(hint) },
                    keyboardOptions = keyboardOptions,
                    keyboardActions = keyboardActions,
                    singleLine = true,
                    leadingIcon = {
                        Icon(imageVector = icon, contentDescription = "icon")
                    },
                    trailingIcon = {
                        if (isInPasswordField) {
                            IconButton(onClick = { onVisibilityChange(!isPasswordVisible) }) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_baseline_remove_red_eye_24),
                                    contentDescription = "Password Eye",
                                    tint = if (isPasswordVisible) MaterialTheme.colorScheme.primary else Color.Gray
                                )
                            }
                        }
                    },
                    visualTransformation = when {
                        isInPasswordField && isPasswordVisible -> VisualTransformation.None
                        isInPasswordField -> PasswordVisualTransformation()
                        else -> VisualTransformation.None
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = MaterialTheme.colorScheme.secondary,
                        focusedLeadingIconColor = MaterialTheme.colorScheme.primary,
                        focusedLabelColor = MaterialTheme.colorScheme.secondary
                    )
                )
        }
    }
}
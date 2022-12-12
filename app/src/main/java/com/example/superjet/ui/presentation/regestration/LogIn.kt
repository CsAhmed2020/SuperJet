package com.example.superjet.ui.presentation.regestration

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ashy2e.data.DataStateResult
import com.example.superjet.R
import com.example.superjet.navigation.Screens
import com.example.superjet.ui.presentation.regestration.component.RegistrationItem
import com.example.superjet.ui.presentation.regestration.component.SimpleProgressDialog
import com.example.superjet.util.AppUtils
import com.example.superjet.util.Constants

@ExperimentalMaterial3Api
@Composable
fun LogIn(
    navController: NavController,
    registrationViewModel: RegistrationViewModel = hiltViewModel()
)
{

    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    var isPasswordVisible by rememberSaveable { mutableStateOf(false) }
    val showProgressDialog = rememberSaveable { mutableStateOf(false) }

    val context = LocalContext.current

    val dataStateResult by registrationViewModel.dataStateResult.observeAsState()
    val errorMessageResId by registrationViewModel.errorMessageResId.observeAsState()

    val focusManager = LocalFocusManager.current
    var isRequestFinished by rememberSaveable { mutableStateOf(false) }

    Scaffold(modifier = Modifier.fillMaxSize()) {

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(top = it.calculateTopPadding())) {

            Column(modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(painter = painterResource(id = R.drawable.login_image),contentDescription = null)
                Text(text = "SuperJet",
                style = TextStyle(
                    fontFamily = FontFamily(Constants.RUBIK_REGULAR),
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 40.sp,
                    color = MaterialTheme.colorScheme.primary
                )
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            RegistrationItem(
                value = email,
                icon = Icons.Default.Email,
                onValueChange = { userEmail ->
                    email = userEmail
                },
                label = "Email",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {focusManager.moveFocus(FocusDirection.Next)}
                )
            )

            RegistrationItem(
                value = password,
                icon = Icons.Default.Lock,
                onValueChange = { userPassword ->
                    password = userPassword
                },
                label = "Password",
                isInPasswordField = true,
                isPasswordVisible = isPasswordVisible,
                onVisibilityChange = { isPassVisibility ->
                    isPasswordVisible = isPassVisibility },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {focusManager.clearFocus()}
                )
            )

            Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center) {
                Button(
                    onClick = {
                        showProgressDialog.value = true
                        isRequestFinished = false
                        if (AppUtils.Utils.validateLogIn(
                                email = email,
                                password = password
                            ) { errorMessageResId ->
                                AppUtils.Utils.showToast(context, errorMessageResId)
                            }
                        ) {
                            registrationViewModel.logIn(
                                email = email,
                                password = password
                            )
                        }
                    },
                    modifier = Modifier
                        .padding(vertical = 10.dp, horizontal = 10.dp)
                        .fillMaxWidth(),
                ) {
                    Text(text = stringResource(id = R.string.log_in), fontSize = 20.sp)
                }
            }

            Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center) {
                Text(text = buildAnnotatedString {
                    append(stringResource(id = R.string.have_no_account) +" ")
                    withStyle(
                        style = SpanStyle(
                            fontSize = 20.sp,
                            color = MaterialTheme.colorScheme.tertiary,
                            fontFamily = FontFamily(Constants.EXTRA_BOLD)
                        )
                    ) {
                        append(stringResource(id = R.string.sign_up))
                    }
                },
                    fontFamily = FontFamily(Constants.REGULAR_FONT),
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 18.sp,
                    modifier = Modifier.clickable {
                        navController.navigate(Screens.SignUpScreen.route)
                    }
                )
            }

        }
        if (!isRequestFinished) {
            when (dataStateResult) {
                is DataStateResult.Loading -> {
                    SimpleProgressDialog(showDialog = showProgressDialog)
                }
                is DataStateResult.Success -> {
                    isRequestFinished = true
                    navController.navigate(Screens.HomeScreen.route)
                }
                is DataStateResult.Error -> {
                    isRequestFinished = true
                    errorMessageResId?.let {
                        AppUtils.Utils.showToast(context, errorMessageResId!!)
                    }
                }
            }
        }
    }
}
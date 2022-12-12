package com.example.superjet.ui.presentation.regestration

import com.example.superjet.R
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.ashy2e.data.DataStateResult
import com.example.superjet.navigation.Screens
import com.example.superjet.ui.presentation.regestration.component.RegistrationItem
import com.example.superjet.ui.presentation.regestration.component.SimpleProgressDialog
import com.example.superjet.util.AppUtils
import com.example.superjet.util.Constants

@ExperimentalCoilApi
@ExperimentalComposeUiApi
@ExperimentalMaterial3Api
@Composable
fun SignUp(
    navController: NavController,
    registrationViewModel: RegistrationViewModel = hiltViewModel()
    )
{
    val context = LocalContext.current



    val dataStateResult by registrationViewModel.dataStateResult.observeAsState()
    val errorMessageResId by registrationViewModel.errorMessageResId.observeAsState()

    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()

    var isRequestFinished by rememberSaveable { mutableStateOf(false) }

    val profileImage = rememberSaveable { mutableStateOf<Uri?>(null) }
    var name by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var address by rememberSaveable { mutableStateOf("") }
    var phone by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmedPassword by rememberSaveable { mutableStateOf("") }

    var isPasswordVisible by rememberSaveable { mutableStateOf(false) }
    var isConfirmedPasswordVisible by rememberSaveable { mutableStateOf(false) }

    val chooseProfileImage = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            profileImage.value = it
        }
    }

    val showProgressDialog = rememberSaveable { mutableStateOf(false) }


    Scaffold()
    {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .verticalScroll(scrollState)
        ){
                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Image(
                        painter = rememberImagePainter(
                            data = if (profileImage.value == null) R.drawable.avatar else profileImage.value
                        ),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .border(
                                2.dp,
                                MaterialTheme.colorScheme.primary,
                                CircleShape
                            )
                            .clickable {
                                chooseProfileImage.launch("image/*")
                            }
                            .fillMaxSize()
                    )

                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_baseline_camera_24),
                        contentDescription = "Pick Image",
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier
                            .background(Color.Transparent)
                            .align(alignment = Alignment.BottomCenter)
                            .offset(x = (20).dp)
                    )

                }

            Spacer(modifier = Modifier.height(10.dp))
                RegistrationItem(
                    value = name,
                    icon = Icons.Default.Person,
                    onValueChange = { userName ->
                        name = userName
                    },
                    label = "Full Name",
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {focusManager.moveFocus(FocusDirection.Next)}
                    )
                )

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
                    value = phone,
                    icon = Icons.Default.Phone,
                    onValueChange = { userPhone ->
                        phone = userPhone
                    },
                    label = "Phone",
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Phone,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {focusManager.moveFocus(FocusDirection.Next)}
                    )
                )

                RegistrationItem(
                    value = address,
                    icon = Icons.Default.LocationOn,
                    onValueChange = { userAddress ->
                        address = userAddress
                    },
                    label = "Address",
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
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
                    onVisibilityChange = { passVisibility ->
                        isPasswordVisible = passVisibility },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {focusManager.moveFocus(FocusDirection.Next)}
                    )
                )

                RegistrationItem(
                    value = confirmedPassword,
                    icon = Icons.Default.Lock,
                    onValueChange = { userConfirmedPass ->
                        confirmedPassword = userConfirmedPass
                    },
                    label = "Confirmed Password",
                    isInPasswordField = true,
                    isPasswordVisible = isConfirmedPasswordVisible,
                    onVisibilityChange = { confPassVisibility ->
                        isConfirmedPasswordVisible = confPassVisibility },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {focusManager.clearFocus()}
                    )
                )

                Button(
                    onClick = {
                        showProgressDialog.value = true
                        isRequestFinished = false
                        if (AppUtils.Utils.validateSignUp(
                                name = name,
                                phone = phone,
                                email = email,
                                address = address,
                                password = password,
                                confirmPassword = confirmedPassword,
                                profileImage = profileImage.value
                        ){ errorMessageResId ->
                                AppUtils.Utils.showToast(context = context,stringResId = errorMessageResId)
                            }
                        ){
                            val userdata = hashMapOf<String,Any>(
                                "name" to name , "address" to address ,
                                "email" to email , "phone" to phone
                            )
                            registrationViewModel.signUp(
                                userData = userdata,
                                password = password,
                                profileImage = profileImage.value!!
                            )
                        }
                    },
                    modifier = Modifier
                        .padding(vertical = 10.dp, horizontal = 10.dp)
                        .fillMaxWidth(0.6f),
                ) {
                    Text(text = stringResource(id = R.string.sign_up), fontSize = 20.sp)
                }

            Text(text = buildAnnotatedString {
                append(stringResource(id = R.string.have_account))
                withStyle(
                    style = SpanStyle(
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.tertiary,
                        fontFamily = FontFamily(Constants.EXTRA_BOLD)
                    )
                ) {
                    append(stringResource(id = R.string.log_in))
                }
            },
                fontFamily = FontFamily(Constants.REGULAR_FONT),
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(vertical = 10.dp)
                    .clickable {
                               navController.navigate(Screens.LoginScreen.route)
                    },
                fontSize = 18.sp
            )

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
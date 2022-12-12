package com.example.superjet.ui.presentation.ticket

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ashy2e.data.DataStateResult
import com.example.superjet.navigation.Screens
import com.example.superjet.ui.presentation.booking.BookingViewModel
import com.example.superjet.ui.presentation.regestration.component.SimpleProgressDialog
import com.example.superjet.util.AppUtils
import com.example.superjet.util.Constants

@ExperimentalMaterial3Api
@Composable
fun Receipt(navController: NavController,
            bookingViewModel: BookingViewModel
){
    val context = LocalContext.current

    val firstName = AppUtils.Utils.getCurrentUSerName().split(" ")[0]

    val receiptInfo = bookingViewModel.bookingDetails

    val dataStateResult by bookingViewModel.dataStateResult.observeAsState()
    val errorMessageResId by bookingViewModel.errorMessageResId.observeAsState()

    var isRequestFinished by rememberSaveable { mutableStateOf(false) }
    val showProgressDialog = rememberSaveable { mutableStateOf(false) }

    Scaffold(modifier = Modifier.fillMaxSize(),
    floatingActionButton = {
        FloatingActionButton(onClick = {
            showProgressDialog.value = true
            isRequestFinished = false
            bookingViewModel.saveBookingInfo()
        },
        containerColor = MaterialTheme.colorScheme.primary
            ) {
            Icon(imageVector = Icons.Default.Done,
                tint = MaterialTheme.colorScheme.onPrimary,
                contentDescription = "done icon")
        }
    },floatingActionButtonPosition = FabPosition.Center
    ) {

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(it)
            .padding(top = 40.dp,bottom = 100.dp,start = 10.dp),
        verticalArrangement = Arrangement.SpaceBetween) {

            Column(modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Hi $firstName",
                style = TextStyle(
                    color = MaterialTheme.colorScheme.primary,
                    fontFamily = FontFamily(Constants.EXTRA_BOLD),
                    fontSize = 18.sp
                )
                )

                Text(text = "All your booking info",
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.secondary,
                        fontFamily = FontFamily(Constants.MEDIUM_FONT),
                        fontSize = 16.sp
                    ))
            }
            Text(text = buildAnnotatedString {
                append("Traveling From "+"  ")
                withStyle(
                  style = SpanStyle(
                      color = MaterialTheme.colorScheme.primary,
                      fontSize = 18.sp,
                      fontFamily = FontFamily(Constants.MEDIUM_FONT)
                  )
                ){
                    append(receiptInfo!!.station)
                }
            },
                style = TextStyle(
                   fontFamily = FontFamily(Constants.BOLD),
                    color = MaterialTheme.colorScheme.secondary
                )

            )
            Text(text = buildAnnotatedString {
                append("Traveling To "+"  ")
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 18.sp,
                        fontFamily = FontFamily(Constants.MEDIUM_FONT)
                    )
                ){
                    append(receiptInfo!!.destination)
                }
            },
                style = TextStyle(
                    fontFamily = FontFamily(Constants.BOLD),
                    color = MaterialTheme.colorScheme.secondary
                )

            )
            Text(text = buildAnnotatedString {
                append("Date "+"  ")
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 18.sp,
                        fontFamily = FontFamily(Constants.MEDIUM_FONT)
                    )
                ){
                    append(receiptInfo!!.date)
                }
            },
                style = TextStyle(
                    fontFamily = FontFamily(Constants.BOLD),
                    color = MaterialTheme.colorScheme.secondary
                )

            )
            Text(text = buildAnnotatedString {
                append("Seat Number "+"  ")
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 18.sp,
                        fontFamily = FontFamily(Constants.MEDIUM_FONT)
                    )
                ){
                    append(receiptInfo!!.seatNumber)
                }
            },
                style = TextStyle(
                    fontFamily = FontFamily(Constants.BOLD),
                    color = MaterialTheme.colorScheme.secondary
                )

            )
            Text(text = buildAnnotatedString {
                append("Additional notes "+"  ")
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 18.sp,
                        fontFamily = FontFamily(Constants.MEDIUM_FONT)
                    )
                ){
                    append(receiptInfo!!.notes)
                }
            },
                style = TextStyle(
                    fontFamily = FontFamily(Constants.BOLD),
                    color = MaterialTheme.colorScheme.secondary
                )

            )
            Text(text = buildAnnotatedString {
                append("How to pay"+"  ")
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 18.sp,
                        fontFamily = FontFamily(Constants.MEDIUM_FONT)
                    )
                ){
                    append(receiptInfo!!.paymentMethod)
                }
            },
                style = TextStyle(
                    fontFamily = FontFamily(Constants.BOLD),
                    color = MaterialTheme.colorScheme.secondary
                )

            )
        }

        if (!isRequestFinished) {
            when (dataStateResult) {
                is DataStateResult.Loading -> {
                    SimpleProgressDialog(showDialog = showProgressDialog)
                }
                is DataStateResult.Success -> {
                    isRequestFinished = true
                    navController.navigate(Screens.TicketsScreen.route)
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
package com.example.superjet.ui.presentation.ticket.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.superjet.R
import com.example.superjet.domain.model.BookingInfo
import com.example.superjet.util.AppUtils
import com.example.superjet.util.Constants

@ExperimentalMaterial3Api
@Composable
fun TicketItem(bookingInfo: BookingInfo) {

    val shape = CustomShape()

    val userName = AppUtils.Utils.getCurrentUSerName()

    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 10.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            shape = shape
        ) {

            Row(modifier = Modifier
                .fillMaxSize()
                .background(shape = shape,color = Color.Blue)) {
                Row(modifier = Modifier.fillMaxWidth(0.3f).fillMaxHeight()
                    .background(color = MaterialTheme.colorScheme.primary),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center) {
                    Image(painter = painterResource(id =R.drawable.ticket_image),
                        modifier = Modifier.padding(top = 5.dp),
                        contentDescription = "ticket image")
                }
                Column(modifier = Modifier.fillMaxWidth().fillMaxHeight()
                    .background(color = MaterialTheme.colorScheme.secondary)) {
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 20.dp,top = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically) {
                        Text(text = bookingInfo.station,
                        style = TextStyle(
                            color = MaterialTheme.colorScheme.onSecondary,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = FontFamily(Constants.LIGHT_FONT),
                            fontSize = 16.sp
                        )
                        )

                        Icon(imageVector = Icons.Default.KeyboardArrowRight,
                            tint = MaterialTheme.colorScheme.onSecondary,
                            contentDescription = null)

                        Text(text = bookingInfo.destination,
                            style = TextStyle(
                                color = MaterialTheme.colorScheme.onSecondary,
                                fontWeight = FontWeight.SemiBold,
                                fontFamily = FontFamily(Constants.LIGHT_FONT),
                                fontSize = 16.sp
                            )
                        )
                    }

                    Spacer(modifier = Modifier.width(5.dp))

                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)) {
                        Text(text = userName,
                            style = TextStyle(
                                color = MaterialTheme.colorScheme.onSecondary,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily(Constants.LIGHT_FONT),
                                fontSize = 18.sp
                            )
                        )
                    }

                    Spacer(modifier = Modifier.width(5.dp))

                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)) {
                        Text(text = bookingInfo.date,
                            style = TextStyle(
                                color = MaterialTheme.colorScheme.onSecondary,
                                fontWeight = FontWeight.SemiBold,
                                fontFamily = FontFamily(Constants.LIGHT_FONT),
                                fontSize = 16.sp
                            )
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(text = "100 EGP",
                            style = TextStyle(
                                color = MaterialTheme.colorScheme.onSecondary,
                                fontWeight = FontWeight.SemiBold,
                                fontFamily = FontFamily(Constants.LIGHT_FONT),
                                fontSize = 18.sp
                            )
                        )
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "Seat No",
                                style = TextStyle(
                                    color = MaterialTheme.colorScheme.onSecondary,
                                    fontWeight = FontWeight.SemiBold,
                                    fontFamily = FontFamily(Constants.LIGHT_FONT),
                                    fontSize = 18.sp
                                )
                            )
                            Text(text = bookingInfo.seatNumber,
                                style = TextStyle(
                                    color = MaterialTheme.colorScheme.onSecondary,
                                    fontWeight = FontWeight.SemiBold,
                                    fontFamily = FontFamily(Constants.LIGHT_FONT),
                                    fontSize = 18.sp
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}
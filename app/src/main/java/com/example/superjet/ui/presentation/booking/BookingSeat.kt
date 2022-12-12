package com.example.superjet.ui.presentation.booking

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.superjet.R
import com.example.superjet.navigation.Screens
import com.example.superjet.ui.presentation.booking.component.BusFront
import com.example.superjet.ui.presentation.booking.component.SeatItem
import com.example.superjet.ui.presentation.booking.component.Status
import com.example.superjet.util.Constants
import kotlinx.coroutines.delay

@ExperimentalMaterial3Api
@Composable
fun BookingSeat(
    navController: NavController,
    bookingViewModel: BookingViewModel
) {

    val seats = arrayListOf<SeatItem>()

    var selectedSeat by remember { mutableStateOf(0) }


    val bookingInfo = bookingViewModel.bookingDetails



    for (i in 0..29){
        seats.add(i, SeatItem(number = i+1))
    }

    seats.map {
        if (it.number == 3 || it.number == 5 || it.number == 12 || it.number == 18 ||
            it.number == 21 || it.number == 27){
            it.status = Status.Reserved
        }
    }

     var toggleState by remember { mutableStateOf(false) }

    val offSetAnim by animateDpAsState(targetValue = if (toggleState) 0.dp else 600.dp,
    animationSpec = tween(1000))

    LaunchedEffect(true){
        delay(500)
        toggleState = ! toggleState
    }

    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colorScheme.secondary,
                title = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(all = 5.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = bookingInfo!!.station, color = MaterialTheme.colorScheme.onSecondary)
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = "from/to",
                            tint = MaterialTheme.colorScheme.onSecondary,

                        )
                        Text(text = bookingInfo.destination, color = MaterialTheme.colorScheme.onSecondary)
                    }
                }
            )
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .offset(y = offSetAnim)
                )
                {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.onPrimary,
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(9f)
                            .padding(top = padding.calculateTopPadding()),
                        shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
                        border = BorderStroke(width = 1.dp,color = MaterialTheme.colorScheme.secondary)
                    )
                    {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp)
                        ) {
                            BusFront()
                            Spacer(modifier = Modifier.height(10.dp))
                            Row(modifier = Modifier.fillMaxWidth())
                            {
                                Column(
                                    modifier = Modifier
                                        .weight(1f)

                                )
                                {
                                    LazyVerticalGrid(
                                        columns = GridCells.Fixed(2)
                                    ) {
                                        itemsIndexed(seats.subList(0,14)){ _, seat ->
                                                SuggestionChip(onClick = {},
                                                    label = {
                                                        SeatItem(
                                                            seat = seat,
                                                            onClick = {
                                                                selectedSeat = seat.number
                                                            }
                                                        )
                                                    })
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.width(20.dp))
                                Column(
                                    modifier = Modifier
                                        .weight(1f)
                                )
                                {
                                    LazyVerticalGrid(columns = GridCells.Fixed(2)
                                    ) {
                                        itemsIndexed(seats.subList(15,29)){ _, seat ->
                                            SuggestionChip(onClick = {},
                                                label = {
                                                    SeatItem(
                                                        seat = seat,
                                                        onClick = {
                                                            selectedSeat = seat.number
                                                        }
                                                    )
                                                })
                                        }
                                    }
                                }
                            }
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .background(color = MaterialTheme.colorScheme.secondary)
                            .padding(top = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Selected Seat : ",
                            style = TextStyle(
                                fontFamily = FontFamily(Constants.LIGHT_FONT),
                                color = MaterialTheme.colorScheme.onSecondary,
                                fontSize = 18.sp,
                            )
                        )
                        Text(
                            text = selectedSeat.toString(),
                            style = TextStyle(
                                color = MaterialTheme.colorScheme.onSecondary,
                                fontSize = 16.sp,
                                fontFamily = FontFamily(Constants.EXTRA_BOLD),
                                textAlign = TextAlign.Center
                            )
                        )
                        Spacer(modifier = Modifier.width(10.dp))

                        Icon(imageVector = ImageVector.vectorResource(id = R.drawable.ic_seat),
                            contentDescription ="seat icon",
                        tint = MaterialTheme.colorScheme.onSecondary,
                        modifier = Modifier.padding(end = 20.dp))
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    )
                    {
                        Button(onClick = {
                            bookingViewModel.updateSeatNumber(
                                selectedSeat.toString()
                            )
                            navController.navigate(Screens.ReceiptScreen.route)
                        }) {
                            Text(text = "Confirm")
                        }
                    }
                }
            }
        }
    }


/*@ExperimentalMaterial3Api
@Preview
@Composable
fun showPre(){
    //BookingSeat()
}*/


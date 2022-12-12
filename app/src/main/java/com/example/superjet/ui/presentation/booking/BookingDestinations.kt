package com.example.superjet.ui.presentation.booking

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import com.example.superjet.R
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.superjet.domain.model.BookingInfo
import com.example.superjet.navigation.Screens
import com.example.superjet.ui.presentation.booking.component.NotesCard
import com.example.superjet.ui.presentation.booking.component.PaymentSection
import com.example.superjet.util.Constants



@ExperimentalAnimationApi
@ExperimentalMaterial3Api
@Composable
fun BookingDestinations(
    navController: NavController,
    bookingViewModel: BookingViewModel
)
{
    val context = LocalContext.current

    val destinations = listOf("Cairo","Alex","Beni-Soueif","Aswan","Ghardaqa")

    var station by remember { mutableStateOf("") }
    var destination by remember { mutableStateOf("") }

    var paymentMethod by remember { mutableStateOf("") }

    val viewModel: DateTimeViewModel = viewModel()
    val dateTime = viewModel.time.observeAsState()

    var notes by remember { mutableStateOf("Thanks")}


    Scaffold {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(it)
        )
        {

        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 20.dp)) {
            Text(text = "From any bus station you coming ?",
                style = TextStyle(
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily(Constants.MEDIUM_FONT)
                )
            )

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp)) {
                Image(painter = painterResource(id = R.drawable.bus_station_image), contentDescription = "bus station image",
                    modifier = Modifier.size(100.dp))
                station = destinationMenu(list = destinations, label = "Stations", validateInput = true)
            }
        }

            Spacer(modifier = Modifier.height(5.dp))

            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
                verticalArrangement = Arrangement.Center) {
                Text(text = "Where are you traveling to ?",
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.secondary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily(Constants.MEDIUM_FONT))
                )

                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween) {
                    destination = destinationMenu(list = destinations, label = "Destinations" , validateInput = true)
                    Image(painter = painterResource(id = R.drawable.bus_stop_image), contentDescription = "bus station image",
                        modifier = Modifier.size(100.dp),contentScale = ContentScale.FillBounds)
                }
            }

            Spacer(modifier = Modifier.height(5.dp))

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween)
            {
               Text(text =
               buildAnnotatedString {
                   append("From : ")

                   withStyle(
                       style = SpanStyle(
                           color = MaterialTheme.colorScheme.primary,
                           fontSize = 18.sp,
                           fontWeight = FontWeight.Bold,
                           fontFamily = FontFamily(Constants.BOLD)
                       )
                   ) {
                       append(" $station ")
                   }
               }, color = MaterialTheme.colorScheme.secondary,
                   style = MaterialTheme.typography.labelLarge,
                   )

                Text(text =
                buildAnnotatedString {
                    append("To : ")

                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 20.sp,
                            fontFamily = FontFamily(Constants.BOLD)
                        )
                    ) {
                        append(" $destination ")
                    }
                }, color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.labelLarge,
                )

            }

            Spacer(modifier = Modifier.height(10.dp))

            val time = if (dateTime.value.isNullOrEmpty()) "" else "at 07:30 AM"
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp),
            verticalAlignment = Alignment.CenterVertically) {
                Button(onClick = {
                    viewModel.selectDateTime(context)
                }) {
                    Text(text = "Select Date")
                }
                Text(text = dateTime.value.toString()+" $time",
                color = MaterialTheme.colorScheme.primary,
                fontFamily =FontFamily(Constants.MEDIUM_FONT),
                maxLines = 1)
            }

            Spacer(modifier = Modifier.height(10.dp))

            NotesCard(
                onValueChange = { note ->
                    notes = note
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

             paymentMethod= PaymentSection()


            Spacer(modifier = Modifier.height(20.dp))

            Box(modifier = Modifier.fillMaxWidth()) {
                Button(onClick = {
                    bookingViewModel.updateBookingInfo(
                        BookingInfo(
                            station = station,destination = destination,date = dateTime.value.toString(),
                            notes = notes,paymentMethod = paymentMethod
                        )
                    )
                     navController.navigate(Screens.BookingSeatScreen.route)
                },modifier = Modifier.align(Alignment.Center)) {
                    Text(text = "Confirm")
                }
            }
        }
    }
}

@Composable
fun destinationMenu( list: List<String>,
                label: String,
                defaultValue: String = "",
                validateInput: Boolean
): String {
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(defaultValue) }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }
    var isError by remember { mutableStateOf(false) }

    if (validateInput && selectedText.isEmpty())
        isError = true

    val icon = if (expanded)
        Icons.Filled.KeyboardArrowDown
    else
        Icons.Filled.KeyboardArrowUp

    Column(modifier =
    Modifier
        .padding(vertical = 2.dp)
        .fillMaxWidth(0.8f)
    ) {
        OutlinedTextField(
            readOnly = true,
            value = selectedText,
            onValueChange = {
                selectedText = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    textFieldSize = coordinates.size.toSize()
                },
            label = { Text(label) },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = MaterialTheme.colorScheme.secondary
            ),
            trailingIcon = {
                Icon(icon, "ExpandedIcon",
                    Modifier.clickable { expanded = !expanded })
            },
            isError = isError
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current) { textFieldSize.width.toDp() })
        ) {
            list.forEach { item ->
                DropdownMenuItem(
                    text = { Text(text = item)},
                    onClick = {
                    selectedText = item
                    expanded = false
                    isError = false
                },
                modifier = Modifier.background(color = MaterialTheme.colorScheme.secondary)
                )
            }
        }
        if (isError) {
            Text(
                text = "$label can't be empty",
                color = Color.Red,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
    return selectedText
}

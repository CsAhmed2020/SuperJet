package com.example.superjet.ui.presentation.booking.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import com.example.superjet.R
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


enum class Status{
    Reserved,
    Available
}

data class SeatItem(
    var number:Int,
    var status: Status = Status.Available
)

@Composable
fun SeatItem(
    seat:SeatItem,
    onClick:() -> Unit
    ){
    Row(modifier = Modifier
        .fillMaxSize()
        .clickable {
            if (seat.status == Status.Available) {
                onClick.invoke()
            }
        })
    {
        Row(modifier = Modifier
            .fillMaxWidth(0.3f)
            .fillMaxHeight()
            .background(color = if (seat.status == Status.Available) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary)) {
            Text(text = seat.number.toString(),
                color = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier
                    .fillMaxSize(),
                style = TextStyle(
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )
            )
        }
        Box(modifier = Modifier.fillMaxWidth()) {
            Icon(imageVector = ImageVector.vectorResource(id = R.drawable.ic_seat), contentDescription = "Seat",
                modifier = Modifier.fillMaxSize().align(Alignment.Center),
                tint = if (seat.status == Status.Available) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary)
        }
    }
}

@Preview
@Composable
fun showSeat(){
    SeatItem(seat = SeatItem(number = 10)) {
    }
}


package com.example.superjet.ui.presentation.booking.component


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.superjet.ui.theme.Purple500
import com.example.superjet.ui.theme.Teal200

@ExperimentalMaterial3Api
@Composable
fun BusFront(
){
    Column(modifier = Modifier
        .fillMaxWidth()
        .height(180.dp)
        .background(color = MaterialTheme.colorScheme.onSecondary))
    {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(10.dp),
                shape = RoundedCornerShape(30.dp),
                elevation = CardDefaults.cardElevation(
                    focusedElevation = 5.dp,
                    hoveredElevation = 5.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    disabledContainerColor = MaterialTheme.colorScheme.secondary
                )
            )
            {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Canvas(modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp)
                        .background(color = MaterialTheme.colorScheme.secondary),
                        onDraw = {
                            drawRect(
                                color = Color.Green,
                                size = Size(100F,100F),
                                topLeft = Offset(0F, size.height /4)
                            )
                            drawRoundRect(
                                color = Color.White,
                                size = Size(500F,100F),
                                topLeft = Offset(190F,size.height / 4),
                                cornerRadius = CornerRadius(30f,30f)
                            )
                            drawRect(
                                color = Color.Green,
                                size = Size(100F,100F),
                                topLeft = Offset(size.width - (40.dp).toPx(), size.height /4)
                            )
                        })
                }
            }

            Spacer(modifier = Modifier.height(5.dp))

            busObj()

        }
    }


@Composable
fun busObj(){

    Column(modifier = Modifier
        .fillMaxWidth()
        .height(100.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 5.dp, end = 10.dp)
                .weight(1f),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        )
        {
            Text(text = "Available Seats",
            style = TextStyle(
                color = MaterialTheme.colorScheme.primary,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
            )

            Spacer(modifier = Modifier.width(20.dp))

            Text(text = "Reserved Seats",
                style = TextStyle(
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalArrangement = Arrangement.SpaceBetween
        )
        {
            Canvas(modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 5.dp),
                onDraw = {
                    val canvasSize = size
                    val canvasWidth = size.width
                    val canvasHeight = size.height

                    drawRect(
                        color = Teal200,
                        topLeft = Offset(x = canvasWidth / 1.5F, y = canvasHeight / 3F),
                        size = canvasSize / 3F
                    )

                    drawCircle(
                        color = Color.DarkGray,
                        center = Offset(x = canvasWidth / 2, y = canvasHeight / 2),
                        radius = size.minDimension / 4
                    )

                    drawRect(
                        color = Purple500,
                        topLeft = Offset(x = canvasWidth / canvasWidth, y = canvasHeight / 3F),
                        size = canvasSize / 3F
                    )
                })
        }
    }
}

@ExperimentalMaterial3Api
@Preview
@Composable
fun show(){
    BusFront()
}
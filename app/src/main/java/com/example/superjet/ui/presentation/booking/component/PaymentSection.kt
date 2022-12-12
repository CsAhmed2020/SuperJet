package com.example.superjet.ui.presentation.booking.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import com.example.superjet.R
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.superjet.ui.theme.ChipContentColor
import com.example.superjet.util.Constants

@ExperimentalMaterial3Api
@Composable
fun PaymentSection() : String{

    val context = LocalContext.current

    var selectedapyment by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxWidth()) {

        Text(text = "Select Your Payment way",
        style = TextStyle(
            color = MaterialTheme.colorScheme.secondary,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily(Constants.MEDIUM_FONT)
        ),
        modifier = Modifier.padding(horizontal = 5.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
        ) {

            SuggestionChip(
                onClick = {
                    selectedapyment = context.getString(R.string.cash)
                },
                label = {
                    Text(text = stringResource(R.string.cash))
                },
                icon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_pay_cash),
                        contentDescription = "cash icon",
                        tint = changeColors(
                            tag = selectedapyment,
                            res = R.string.cash,
                            selectedColor = MaterialTheme.colorScheme.onSecondary,
                            unSelectedColor = MaterialTheme.colorScheme.secondary
                        )
                        //if (tag == stringResource(R.string.cash)) MaterialTheme.colorScheme.onSecondary else MaterialTheme.colorScheme.secondary
                    )
                },
                colors = SuggestionChipDefaults.suggestionChipColors(
                    containerColor = changeColors(
                        tag = selectedapyment,
                        res = R.string.cash,
                        selectedColor = ChipContentColor,
                        unSelectedColor = MaterialTheme.colorScheme.onSecondary
                    )
                )
            )

            Spacer(modifier = Modifier.width(10.dp))

            SuggestionChip(
                onClick = {
                    selectedapyment = context.getString(R.string.credit)
                },
                label = {
                    Text(text = stringResource(R.string.credit))
                },
                icon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_credit_card),
                        contentDescription = "credit icon",
                        tint = changeColors(
                            tag = selectedapyment,
                            res = R.string.credit,
                            selectedColor = MaterialTheme.colorScheme.onSecondary,
                            unSelectedColor = MaterialTheme.colorScheme.secondary
                        )
                    )
                },
                colors = SuggestionChipDefaults.suggestionChipColors(
                    containerColor = changeColors(
                        tag = selectedapyment,
                        res = R.string.credit,
                        selectedColor = ChipContentColor,
                        unSelectedColor = MaterialTheme.colorScheme.onSecondary
                    )
                )

            )

            Spacer(modifier = Modifier.width(10.dp))

            SuggestionChip(
                onClick = {
                    selectedapyment = context.getString(R.string.vodafone)
                },
                label = {
                    Text(text = stringResource(R.string.vodafone))
                },
                icon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_vodafone_icon),
                        contentDescription = "vodafone icon"
                    )
                },
                colors = SuggestionChipDefaults.suggestionChipColors(
                    containerColor = changeColors(
                        tag = selectedapyment,
                        res = R.string.vodafone,
                        selectedColor = ChipContentColor,
                        unSelectedColor = MaterialTheme.colorScheme.onSecondary
                    )
                )
            )


        }
    }
    return selectedapyment
}
@Composable
fun changeColors(tag:String,res:Int,selectedColor : Color, unSelectedColor : Color):Color{
    return if (tag == stringResource(res)) selectedColor else unSelectedColor
}

@ExperimentalMaterial3Api
@Preview
@Composable
fun showPayment(){
    PaymentSection()
}
package com.example.superjet.ui.presentation.home

import android.widget.TextView
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.FabPosition
import androidx.compose.material.TopAppBar
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import androidx.navigation.NavController
import com.example.superjet.R
import com.example.superjet.navigation.Screens
import com.example.superjet.util.AppUtils
import com.example.superjet.util.Constants



@ExperimentalMaterial3Api
@Composable
fun  HomeScreen(
    navController: NavController,
){

    val firstName = AppUtils.Utils.getCurrentUSerName().split(" ")[0]

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colorScheme.secondary,
                elevation = 0.dp,
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            Icons.Outlined.Person,
                            contentDescription = "person",
                            tint = MaterialTheme.colorScheme.onSecondary
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = "Hi $firstName",
                            fontFamily = FontFamily(Constants.LIGHT_FONT),
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onSecondary
                        )
                    }
                },

            )
        },
        floatingActionButton = {
            FloatingActionButton(
                containerColor = MaterialTheme.colorScheme.primary,
                onClick = {
                    navController.navigate(Screens.BookingDestinationScreen.route)
                },
                shape = RoundedCornerShape(50),
                modifier = Modifier.size(60.dp)
            ) {
                Icon(
                    ImageVector.vectorResource(id = R.drawable.ic_booking_bus),
                    tint = Color.White,
                    contentDescription = "Search"
                )
            }
        },
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.Center,
        bottomBar = {
            androidx.compose.material.BottomAppBar(
                backgroundColor = MaterialTheme.colorScheme.secondary,
                cutoutShape = RoundedCornerShape(50),
                elevation = 2.dp,
                content = {
                    BottomNavigation(
                        backgroundColor = MaterialTheme.colorScheme.secondary,
                        elevation = 2.dp
                    ) {
                        BottomNavigationItem(
                            selected = true,
                            onClick = {
                            },
                            icon = {
                                androidx.compose.material.Icon(
                                    Icons.Outlined.Home,
                                    contentDescription = "Home"
                                )
                            },
                            unselectedContentColor = MaterialTheme.colorScheme.onSecondary,
                            selectedContentColor = MaterialTheme.colorScheme.primary
                        )
                        BottomNavigationItem(
                            selected = false,
                            onClick = {
                                      navController.navigate(Screens.TicketsScreen.route)
                            },
                            icon = {
                                androidx.compose.material.Icon(
                                    ImageVector.vectorResource(id = R.drawable.ic_baseline_bus_alert_24),
                                    contentDescription = "bus icon"
                                )
                            },
                            unselectedContentColor = MaterialTheme.colorScheme.onSecondary,
                            selectedContentColor = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.weight(1f, true))
                        BottomNavigationItem(
                            selected = false,
                            onClick = { },
                            icon = {
                                androidx.compose.material.Icon(
                                    Icons.Filled.Notifications,
                                    contentDescription = "Notifications"
                                )
                            },
                            unselectedContentColor = MaterialTheme.colorScheme.onSecondary,
                            selectedContentColor = MaterialTheme.colorScheme.primary
                        )

                        BottomNavigationItem(
                            selected = false,
                            onClick = { },
                            icon = {
                                androidx.compose.material.Icon(
                                    Icons.Filled.Person,
                                    contentDescription = "profile icon"
                                )
                            },
                            unselectedContentColor = MaterialTheme.colorScheme.onSecondary,
                            selectedContentColor = MaterialTheme.colorScheme.primary
                        )

                    }
                }
            )
        }
    )
    {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(it)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Spacer(modifier = Modifier.height(10.dp))
            Card(
                modifier = Modifier
                    .height(160.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                shape = RoundedCornerShape(15.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.onPrimary
                ),
                border = BorderStroke(width = 1.dp,color = MaterialTheme.colorScheme.secondary)
            )
            {
                Row(
                    modifier = Modifier
                        .fillMaxSize().padding(horizontal = 10.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                )
                {
                    Image(painter = painterResource(id = R.drawable.offer_image) , contentDescription = "offer image",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .weight(0.6f)
                    )
                    Column(
                        modifier = Modifier
                            .weight(0.4f)
                    )
                    {

                        AndroidView(
                            factory = { context ->
                                TextView(context).apply {
                                    text = HtmlCompat.fromHtml("<string><big><b><span style = \"color:#000000\">رحلة الغردقة 4 أيام والسعر مفاجأة </span><span style = \"color:#F54748\"> 6000 EGP</span></b></big></string>",
                                        HtmlCompat.FROM_HTML_MODE_LEGACY)
                                }
                            }
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Button(
                            onClick = { },
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(20.dp)),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Text(
                                text = "الذهاب للعرض",
                                fontFamily = FontFamily(Constants.BOLD),
                                color = Color.White,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}
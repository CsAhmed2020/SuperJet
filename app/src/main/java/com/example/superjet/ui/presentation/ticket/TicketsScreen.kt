package com.example.superjet.ui.presentation.ticket

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ashy2e.data.DataStateResult
import com.example.superjet.navigation.Screens
import com.example.superjet.ui.presentation.booking.BookingViewModel
import com.example.superjet.ui.presentation.ticket.components.LoadingProgressIndicator
import com.example.superjet.ui.presentation.ticket.components.TicketItem


@ExperimentalMaterial3Api
@Composable
fun TicketsScreen(
    navController: NavController,
    bookingViewModel:BookingViewModel = hiltViewModel()
) {



    val ticketsListState by bookingViewModel.ticketsListState.observeAsState()

    var isTicketsRequestFinished by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (!isTicketsRequestFinished) {
            bookingViewModel.getUserTickets()
        }
    }

    Scaffold(modifier = Modifier.fillMaxSize(),
    topBar = {
        SmallTopAppBar(
            navigationIcon = {
                Icon(imageVector = Icons.Default.KeyboardArrowLeft,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.clickable {
                        navController.popBackStack()
                        navController.navigate(Screens.HomeScreen.route)
                    },
                    contentDescription = null)
            },
            title = {
                Text(text = "Your Tickets",
                color = MaterialTheme.colorScheme.onPrimary)
            }
        )
    }) {


        Column(modifier = Modifier
            .fillMaxSize()
            .padding(it)) {

            when (ticketsListState) {
                is DataStateResult.Loading -> {
                    LoadingProgressIndicator()
                }
                is DataStateResult.Error -> {
                    isTicketsRequestFinished = true
                }
                is DataStateResult.Success -> {
                    isTicketsRequestFinished = true
                    ticketsListState?.let { result ->
                        val ticketsList = result.data!!
                        if (ticketsList.isNotEmpty()) {
                            LazyColumn(modifier = Modifier.fillMaxSize()) {
                                itemsIndexed(ticketsList) { _, ticket ->
                                    TicketItem(ticket)
                                }
                            }
                        }
                    }
                }
            }

        }
    }
}
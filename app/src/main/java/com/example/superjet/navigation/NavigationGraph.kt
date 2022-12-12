package com.example.superjet.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import com.example.superjet.ui.presentation.booking.BookingDestinations
import com.example.superjet.ui.presentation.booking.BookingSeat
import com.example.superjet.ui.presentation.booking.BookingViewModel
import com.example.superjet.ui.presentation.home.HomeScreen
import com.example.superjet.ui.presentation.regestration.LogIn
import com.example.superjet.ui.presentation.regestration.SignUp
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.example.superjet.ui.presentation.ticket.Receipt
import com.example.superjet.ui.presentation.ticket.TicketsScreen


@ExperimentalCoilApi
@ExperimentalMaterial3Api
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun NavigationGraph(navHostController: NavHostController){
    val bookingViewModel: BookingViewModel = hiltViewModel()

    AnimatedNavHost(navController = navHostController,
        startDestination = Screens.SignUpScreen.route){
        composable(route = Screens.SignUpScreen.route){
            SignUp(navController = navHostController)
        }
        composable(route = Screens.HomeScreen.route){
            HomeScreen(navController = navHostController)
        }
        composable(route = Screens.BookingDestinationScreen.route){
            BookingDestinations(navController = navHostController,bookingViewModel = bookingViewModel)
        }
        composable(route = Screens.BookingSeatScreen.route
        ){
            BookingSeat(navController = navHostController,bookingViewModel = bookingViewModel)
        }
        composable(route = Screens.ReceiptScreen.route){
            Receipt(navController = navHostController,bookingViewModel = bookingViewModel)
        }
        composable(route = Screens.TicketsScreen.route){
            TicketsScreen(navController = navHostController)
        }
        composable(route = Screens.LoginScreen.route){
            LogIn(navController = navHostController)
        }
    }
}
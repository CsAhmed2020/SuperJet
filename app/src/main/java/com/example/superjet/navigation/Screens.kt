package com.example.superjet.navigation

sealed class Screens(val route : String){

    object WelcomeScreen : Screens("welcome_screen")
    object HomeScreen : Screens("home_screen")
    object LoginScreen : Screens("login_screen")
    object SignUpScreen : Screens("sign_up_screen")
    object BookingDestinationScreen : Screens("booking_destinations_screen")
    object BookingSeatScreen : Screens("booking_seat_screen")
    object ReceiptScreen : Screens("receipt_screen")
    object TicketsScreen : Screens("tickets_screen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }

}

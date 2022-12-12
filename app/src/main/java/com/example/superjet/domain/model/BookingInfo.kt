package com.example.superjet.domain.model

data class BookingInfo(
    var station:String = "",
    var destination : String = "",
    var date:String = "",
    var notes:String = "",
    var seatNumber:String = "",
    var paymentMethod:String = "",
    var timestampValue: String = "123"
)

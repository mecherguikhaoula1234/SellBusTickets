package com.example.sellbustickets.Data.Models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * classe POJO représentant le model correspondant à un ticket
 */

@Parcelize
data class Ticket (var idTicket: Long = 0,
                   var typeTicket : String? = null,
                   var priceTicket: Double? = null,
                   var numberTicket: Int? = null ): Parcelable
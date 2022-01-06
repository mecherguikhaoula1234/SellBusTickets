package com.example.sellbustickets.Data.Models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Transaction(var id: Long = 0,
                       var idTransaction: String? = null,
                       var amountTransaction: Int? = null): Parcelable
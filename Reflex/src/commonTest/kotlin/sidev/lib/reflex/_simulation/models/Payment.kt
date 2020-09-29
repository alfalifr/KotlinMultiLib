package com.sigudang.android.models

//import java.io.Serializable

data class Payment(var id: String = "",
                   var no: String = "",
                   var expired_at: String = "",     // berupa timestamp yang nantinya dikonversi
                   var paid_at: String = "",
                   var total: Long = 0,
                   var paymentMethod: PaymentMethod? = null)//: Serializable
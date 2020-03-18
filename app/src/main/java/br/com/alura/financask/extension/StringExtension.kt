package br.com.alura.financask.extension

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

fun String.limitaEmAte(caracteres: Int) : String{
    if(this.length > caracteres){
        val primeiroCaracter = 0
        return "${this.substring(primeiroCaracter, caracteres)}..."
    }
    return this
}

@SuppressLint("SimpleDateFormat")
fun String.converteParaCalendar(): Calendar {
    val dataConvertida = SimpleDateFormat("dd/MM/yyyy").parse(this)
    val data = Calendar.getInstance()

    data.time = dataConvertida

    return data
}
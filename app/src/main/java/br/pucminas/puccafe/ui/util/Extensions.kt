package br.pucminas.puccafe.ui.util

import java.text.NumberFormat
import java.util.Currency
import java.util.Locale


fun Double.tocurrency(
    currencyCode: String,
    language: String,
    country: String = "",
    variant: String = ""
): String {

    return Locale(language, country, variant).let {
        NumberFormat.getCurrencyInstance(it).apply {
            currency = Currency.getInstance(currencyCode)
        }
    }.format(this)
}
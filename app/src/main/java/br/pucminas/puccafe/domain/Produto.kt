package br.pucminas.puccafe.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class Produto (
    val id: UUID,
    var nomeProduto: String,
    var valorUnitario: Double,
    var quantidade: Int = 0,
    var selecionado: Boolean
) : Parcelable
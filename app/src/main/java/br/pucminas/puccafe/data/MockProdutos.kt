package br.pucminas.puccafe.data

import br.pucminas.puccafe.domain.Produto
import java.util.UUID

object MockProdutos {

    fun produtos(): MutableList<Produto> = mutableListOf(
        Produto(
            id = UUID.randomUUID(),
            nomeProduto = "Café coado",
            valorUnitario = 2.0,
            quantidade = 0,
            selecionado = false
        ),
        Produto(
            id = UUID.randomUUID(),
            nomeProduto = "Cappuccino",
            valorUnitario = 12.0,
            quantidade = 0,
            selecionado = false
        ),
        Produto(
            id = UUID.randomUUID(),
            nomeProduto = "Latte",
            valorUnitario = 8.0,
            quantidade = 0,
            selecionado = false
        ),
        Produto(
            id = UUID.randomUUID(),
            nomeProduto = "Cold brew",
            valorUnitario = 12.0,
            quantidade = 0,
            selecionado = false
        ),
        Produto(
            id = UUID.randomUUID(),
            nomeProduto = "Mocha",
            valorUnitario = 8.50,
            quantidade = 0,
            selecionado = false
        ),
        Produto(
            id = UUID.randomUUID(),
            nomeProduto = "Macchiato",
            valorUnitario = 2.0,
            quantidade = 0,
            selecionado = false
        )
    )
}

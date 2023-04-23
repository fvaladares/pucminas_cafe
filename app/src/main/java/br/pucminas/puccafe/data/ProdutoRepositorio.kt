package br.pucminas.puccafe.data

import br.pucminas.puccafe.domain.Produto

class ProdutoRepositorio {
    fun getProdutos(): MutableList<Produto> = MockProdutos.produtos()
}
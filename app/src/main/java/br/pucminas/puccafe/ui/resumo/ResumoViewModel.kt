package br.pucminas.puccafe.ui.resumo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.pucminas.puccafe.domain.Produto

class ResumoViewModel : ViewModel() {
    private val _listaProdutos = MutableLiveData<List<Produto>>()
    val listaProdutos: LiveData<List<Produto>> = _listaProdutos
    private var _total = MutableLiveData<Double>()
    val total: LiveData<Double> = _total

    fun carregarDados(produtos: List<Produto>) {
        _listaProdutos.value = produtos
        _total.value = produtos.sumOf {
            it.quantidade * it.valorUnitario
        }
    }
}
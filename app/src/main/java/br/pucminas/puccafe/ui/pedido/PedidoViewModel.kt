package br.pucminas.puccafe.ui.pedido

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.pucminas.puccafe.data.ProdutoRepositorio
import br.pucminas.puccafe.domain.Produto

class PedidoViewModel(val produtoRepositorio: ProdutoRepositorio) : ViewModel() {
    private val _listaProdutos = MutableLiveData<MutableList<Produto>>()
    val listaProdutos: LiveData<MutableList<Produto>> = _listaProdutos
    private val _fecharPedidosLista = MutableLiveData<List<Produto>>()
    val fecharPedidoLista: LiveData<List<Produto>> = _fecharPedidosLista
    private val listaPedidosVazia = MutableLiveData(false)


    fun carregarProdutos() {
        if (_listaProdutos.value.isNullOrEmpty())
            _listaProdutos.value = produtoRepositorio.getProdutos()
    }

    fun aumentarQuantidade(produto: Produto): MutableList<Produto> {
        _listaProdutos.value!!.find { it.id == produto.id }!!.apply {
            quantidade += 1
            selecionado = true
        }

        Log.d("PUCMG", _listaProdutos.value.toString())
        return _listaProdutos.value!!
    }

    fun reduzirQuantidade(produto: Produto) {
        _listaProdutos.value!!.find { it.id == produto.id }!!.apply {
            if (quantidade > 0)
                quantidade -= 1
            else
                selecionado = false
        }

        Log.d("PUCMG", _listaProdutos.value.toString())
    }

    fun confirmarPedido() {
        val itensPedido = _listaProdutos.value?.filter {
            it.selecionado
        }

        if (itensPedido.isNullOrEmpty()) {
            listaPedidosVazia.value = true
        } else {
            listaPedidosVazia.value = false
            _fecharPedidosLista.value = itensPedido.toList()
        }
    }
}
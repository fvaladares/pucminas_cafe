package br.pucminas.puccafe.ui.pedido

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import br.pucminas.puccafe.data.MockProdutos
import br.pucminas.puccafe.data.ProdutoRepositorio
import br.pucminas.puccafe.domain.Produto
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.slot
import io.mockk.verify
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PedidoViewModelTest() {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private var sut: PedidoViewModel? = null
    private val listaMock = MockProdutos.produtos()
    private lateinit var fakeApi: ProdutoRepositorio

    @Before
    fun setUp() {

        mockkStatic(Log::class)
        every { Log.v(any(), any()) } returns 0
        every { Log.d(any(), any()) } returns 0
        every { Log.i(any(), any()) } returns 0
        every { Log.e(any(), any()) } returns 0

        fakeApi = mockk()
        every { fakeApi.getProdutos() } returns listaMock
        sut = PedidoViewModel(fakeApi)
    }

    @After
    fun tearDown() {
        sut = null
    }

    @Test
    fun `valida a obtencao da lista de produtos`() {
        sut!!.carregarProdutos()
        assertEquals(sut!!.listaProdutos.value!!.size, listaMock.size)
    }

    @Test
    fun `validar aumento na quantidade de um produto especifico com sucess`() {
        sut!!.carregarProdutos()
        sut!!.aumentarQuantidade(listaMock[0])
        sut!!.aumentarQuantidade(listaMock[0])
        sut!!.aumentarQuantidade(listaMock[0])

        val valorEsperado = 3
        val valorAtual = sut!!.listaProdutos.value!![0].quantidade

        assertEquals(valorEsperado, valorAtual)
    }

    @Test
    fun `validar confirmacao pedido`() {
        val observer = mockk<Observer<List<Produto>>>()
        val slot = slot<List<Produto>>()

        every { observer.onChanged(any()) } answers {}

        sut!!.fecharPedidoLista.observeForever(observer)

        sut!!.carregarProdutos()

        sut!!.aumentarQuantidade(listaMock[0])
        sut!!.aumentarQuantidade(listaMock[0])
        sut!!.aumentarQuantidade(listaMock[0])
        sut!!.aumentarQuantidade(listaMock[0])
        sut!!.reduzirQuantidade(listaMock[0])

        sut!!.confirmarPedido()

        val valorEsperado = 3
        val valorAtual = sut!!.fecharPedidoLista.value!!.first().quantidade
        assertEquals(valorEsperado, valorAtual)

        verify { observer.onChanged(capture(slot)) }

    }
}
package br.pucminas.puccafe.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.every
import io.mockk.spyk
import io.mockk.verify
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private var sut: MainViewModel? = null
    private val nome = "Fabricio Valadares"
    private val nomeIncorreto = "Fabricio Valadar"

    @Before
    fun setUp() {
        sut = MainViewModel()
    }

    @After
    fun tearDown() {
        sut = null
    }

    @Test
    fun `valida o nome informado com sucesso`() {
        assertTrue(sut!!.validarDados(nome))
        assertFalse(sut!!.nome.value == nomeIncorreto)
    }

    @Test
    fun `validar se inserir nome vazio falha`() {
        assertFalse(sut!!.validarDados("                     "))
    }

    @Test
    fun `valida comportamento da chamada de validacao do nome`() {

        val spy = spyk<MainViewModel>()

        spy.validarDados(nome)
        verify { spy.validarDados(nome) }
    }
}
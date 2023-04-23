package br.pucminas.puccafe.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private var _nome = MutableLiveData<String>()
    val nome: LiveData<String> = _nome

    fun validarDados(nome: String?): Boolean {
        return if (nome.isNullOrBlank()) {
            false
        } else {
            _nome.value = nome!!
            true
        }
    }

}
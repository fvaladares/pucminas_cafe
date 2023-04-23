package br.pucminas.puccafe.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import br.pucminas.puccafe.R
import br.pucminas.puccafe.databinding.FragmentMainBinding


class MainFragment : Fragment() {


    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepararObservers()
        prepararListeners()
    }

    private fun prepararObservers() {
        viewModel.nome.observe(viewLifecycleOwner) { nome ->
            avancarProximaTela(nome)
        }
    }

    private fun avancarProximaTela(nome: String) {
        val action = MainFragmentDirections.actionMainFragmentToPedidoFragment(nome)
        findNavController().navigate(action)
    }

    private fun prepararListeners() {
        prepararBtnContinuar()
    }

    private fun prepararBtnContinuar() {
        binding.buttonContinuarNome.setOnClickListener {
            exibirErro(!viewModel.validarDados(binding.editTextNomeCliente.text.toString()))
        }
    }

    private fun exibirErro(erro: Boolean) {
        binding.tilNome.error =
            if (erro) getString(R.string.erro_nome)
            else null
    }
}
package br.pucminas.puccafe.ui.resumo

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import br.pucminas.puccafe.R
import br.pucminas.puccafe.databinding.FragmentResumoBinding
import br.pucminas.puccafe.domain.Produto
import br.pucminas.puccafe.ui.util.Brazil
import br.pucminas.puccafe.ui.util.tocurrency


class ResumoFragment : Fragment() {

    private var _binding: FragmentResumoBinding? = null
    private val binding get() = _binding!!
    private val args: ResumoFragmentArgs by navArgs()
    private val resumoViewModel: ResumoViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentResumoBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var produtos = listOf<Produto>()
        arguments?.let {
            produtos = args.produtos.toList()
        }

        resumoViewModel.carregarDados(produtos = produtos)

        prepararListerners()
        prepararObservers()
        Log.d("PUCMG", produtos.toString())
    }

    private fun prepararListerners() {
        binding.buttonConcluir.setOnClickListener {
            val action = ResumoFragmentDirections.actionResumoFragmentToEncerramentoFragment()
            findNavController().navigate(action)
        }
    }

    private fun configurarRecyclerView(produtos: List<Produto>) {
        binding.recyclerViewTotal.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = ResumoAdapter(produtos, ::formatarValor)
            setHasFixedSize(true)
        }
    }

    private fun prepararObservers() {
        resumoViewModel.listaProdutos.observe(viewLifecycleOwner) {
            configurarRecyclerView(it)
            resumoViewModel.total.observe(viewLifecycleOwner) { valor ->
                binding.textViewValorUnitario.text = valor.tocurrency(
                    Brazil.CURRENCY_BRAZIL_REAIS,
                    Brazil.LANGUAGE_PORTUGUES_BRAZIL
                )
            }
        }
    }

    private fun formatarValor(valor: String, textView: TextView, opcao: Int) {
        textView.text = when (opcao) {
            1 -> getString(R.string.lbl_valor_unitario_place_holder, valor)
            2 -> getString(R.string.lbl_valor_total_place_holder, valor)
            else -> getString(R.string.lbl_quantidade_holder, valor)
        }
    }
}

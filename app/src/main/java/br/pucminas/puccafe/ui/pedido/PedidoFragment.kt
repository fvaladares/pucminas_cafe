package br.pucminas.puccafe.ui.pedido

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import br.pucminas.puccafe.R
import br.pucminas.puccafe.data.ProdutoRepositorio
import br.pucminas.puccafe.databinding.FragmentPedidoBinding
import br.pucminas.puccafe.domain.Produto
import br.pucminas.puccafe.ui.util.Brazil
import br.pucminas.puccafe.ui.util.tocurrency


class PedidoFragment : Fragment() {

    private var _binding: FragmentPedidoBinding? = null
    private val binding get() = _binding!!
    private lateinit var pedidoViewModel: PedidoViewModel
    private lateinit var nome: String
    private lateinit var adapter: ProdutosAdapter

    private val args: PedidoFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentPedidoBinding.inflate(inflater, container, false)
        pedidoViewModel = PedidoViewModel(ProdutoRepositorio())
        pedidoViewModel.carregarProdutos()
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = ProdutosAdapter(::aumentarQuantidade, ::reduzirQuantidade)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            nome = args.nome
            binding.textViewRotuloItemPedido.text =
                getString(R.string.lbl_selecionar_item_produto, nome)
            Toast.makeText(requireContext(), nome, Toast.LENGTH_LONG).show()
        }
        prepararObservers()
        prepararListeners()
    }

    private fun prepararListeners() {
        configurarBtnContinuarListener()
    }

    private fun configurarBtnContinuarListener() {
        binding.buttonAvancarPedido.setOnClickListener {
            pedidoViewModel.confirmarPedido()
        }
    }

    private fun prepararObservers() {
        configurarObserverListaProdutos()
        pedidoViewModel.fecharPedidoLista.observe(viewLifecycleOwner) {
            avancarProximaTela(it)
        }
    }

    private fun avancarProximaTela(produtos: List<Produto>?) {
        val action =
            PedidoFragmentDirections.actionPedidoFragmentToResumoFragment(produtos = produtos!!.toTypedArray())
        findNavController().navigate(action)
    }

    private fun configurarObserverListaProdutos() {
        pedidoViewModel.listaProdutos.observe(viewLifecycleOwner) {
            exibirListaProdutos(it)
        }
    }

    private fun exibirListaProdutos(produtos: MutableList<Produto>?) {
        Log.d("PUCMinas :: ", "Produtos $produtos")
        Log.d(
            "PUCMinas :: ",
            "Preço ${
                (produtos?.first()?.valorUnitario?.tocurrency(
                    Brazil.CURRENCY_BRAZIL_REAIS,
                    Brazil.LANGUAGE_PORTUGUES_BRAZIL
                ))
            }"
        )

        binding.recyclerViewItemPedido.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewItemPedido.adapter = adapter
        binding.recyclerViewItemPedido.setHasFixedSize(true)

        adapter.atualizarListaProdutos(produtos!!)
    }

    private fun aumentarQuantidade(produto: Produto) {
        adapter.atualizarListaProdutos(pedidoViewModel.aumentarQuantidade(produto))
    }

    private fun reduzirQuantidade(produto: Produto) {
        pedidoViewModel.reduzirQuantidade(produto)
        adapter.atualizarListaProdutos(pedidoViewModel.listaProdutos.value!!)
    }
}
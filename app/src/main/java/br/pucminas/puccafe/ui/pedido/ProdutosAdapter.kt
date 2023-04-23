package br.pucminas.puccafe.ui.pedido

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import br.pucminas.puccafe.databinding.ListItemItemPedidoBinding
import br.pucminas.puccafe.databinding.ListItemItemPedidoBinding.*
import br.pucminas.puccafe.domain.Produto
import br.pucminas.puccafe.ui.util.Brazil
import br.pucminas.puccafe.ui.util.tocurrency


class ProdutosAdapter(
    private val aumentarQuantidade: (Produto) -> Unit,
    private val reduzirQuantidade: (Produto) -> Unit
) :
    RecyclerView.Adapter<ProdutosAdapter.ProdutoViewHolder>() {

    private var _binding: ListItemItemPedidoBinding? = null
    private val binding get() = _binding!!
    private val asyncListDiffer: AsyncListDiffer<Produto> = AsyncListDiffer(this, DiffCallback)


    inner class ProdutoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(produto: Produto) {

            var qtd: Int = try {
                binding.editTextQuantidade.text.toString().toInt()
            } catch (ex: NumberFormatException) {
                Log.d("PUCMG", "A quantidade não é válida. Retornando zero.")
                0
            }

            binding.buttonReduzirQtd.isEnabled = qtd > 0

            binding.apply {
                textViewLblProduto.text = produto.nomeProduto
                textViewValorUnitario.text = produto.valorUnitario.tocurrency(
                    Brazil.CURRENCY_BRAZIL_REAIS,
                    Brazil.LANGUAGE_PORTUGUES_BRAZIL
                )

                buttonAumentarQtd.setOnClickListener {
                    editTextQuantidade.text = (++qtd).toString()
                    buttonReduzirQtd.isEnabled = (qtd > 0)
                    aumentarQuantidade(produto)
                }

                buttonReduzirQtd.setOnClickListener {
                    editTextQuantidade.text = (--qtd).toString()
                    buttonReduzirQtd.isEnabled = (qtd > 0)
                    reduzirQuantidade(produto)
                }
            }
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProdutoViewHolder {
        _binding =
            inflate(LayoutInflater.from(parent.context), parent, false)

        return ProdutoViewHolder(binding.root)
    }

    override fun getItemCount(): Int = asyncListDiffer.currentList.size

    override fun onBindViewHolder(holder: ProdutoViewHolder, position: Int) {
        val item = asyncListDiffer.currentList[position]
        holder.bind(item)
    }

    fun atualizarListaProdutos(produtos: MutableList<Produto>) {
        asyncListDiffer.submitList(produtos)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    object DiffCallback : DiffUtil.ItemCallback<Produto>() {
        override fun areItemsTheSame(oldItem: Produto, newItem: Produto): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Produto, newItem: Produto): Boolean {
            Log.d(
                "FAbricio",
                "New item = $newItem, old item $oldItem, comparison ${oldItem == newItem}"
            )
            return oldItem.quantidade == newItem.quantidade
        }
    }
}
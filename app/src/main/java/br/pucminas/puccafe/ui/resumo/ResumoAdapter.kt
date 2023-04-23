package br.pucminas.puccafe.ui.resumo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.pucminas.puccafe.databinding.ListItemProdutoBinding
import br.pucminas.puccafe.domain.Produto
import br.pucminas.puccafe.ui.util.Brazil
import br.pucminas.puccafe.ui.util.tocurrency


class ResumoAdapter(
    private val produtos: List<Produto>,
    private val formatarValor: (String, TextView, Int) -> Unit
) : RecyclerView.Adapter<ResumoAdapter.ViewHoder>() {
    private var _binding: ListItemProdutoBinding? = null
    private val binding get() = _binding!!

    inner class ViewHoder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Produto) {
            binding.apply {
                textViewNomeProduto.text = item.nomeProduto
                formatarValor(
                    item.valorUnitario.tocurrency(
                        Brazil.CURRENCY_BRAZIL_REAIS,
                        Brazil.LANGUAGE_PORTUGUES_BRAZIL
                    ),
                    textViewValorUnitario,
                    1
                )
                formatarValor(
                    (item.valorUnitario * item.quantidade).tocurrency(
                        Brazil.CURRENCY_BRAZIL_REAIS,
                        Brazil.LANGUAGE_PORTUGUES_BRAZIL
                    ),
                    textViewValorTotal,
                    2
                )
                formatarValor(
                    item.quantidade.toString(),
                    textViewQuantidadeProduto,
                    3
                )
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHoder {
        _binding =
            ListItemProdutoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        return ViewHoder(binding.root)
    }

    override fun getItemCount(): Int = produtos.size

    override fun onBindViewHolder(holder: ViewHoder, position: Int) {
        val item = produtos[position]

        holder.bind(item)
    }
}
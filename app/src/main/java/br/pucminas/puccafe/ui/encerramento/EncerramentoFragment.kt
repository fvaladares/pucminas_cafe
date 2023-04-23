package br.pucminas.puccafe.ui.encerramento

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import br.pucminas.puccafe.R
import br.pucminas.puccafe.databinding.FragmentEncerramentoBinding
import br.pucminas.puccafe.ui.main.MainViewModel

class EncerramentoFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private var _binding: FragmentEncerramentoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        _binding = FragmentEncerramentoBinding.inflate(inflater, container, false)

        configurarView()
        configurarListerners()

        return binding.root
    }

    private fun configurarListerners() {
        binding.btnConcluir.setOnClickListener {
            activity?.finish()
        }
    }

    private fun configurarView() {
        binding.textViewValeu.text = getString(R.string.lbl_valeu, viewModel.nome.value)
        binding.textViewLogoLogo.text = getString(R.string.logologo)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
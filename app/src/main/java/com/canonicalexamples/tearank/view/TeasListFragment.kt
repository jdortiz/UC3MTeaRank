package com.canonicalexamples.tearank.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.canonicalexamples.tearank.R
import com.canonicalexamples.tearank.app.TeaRankApp
import com.canonicalexamples.tearank.databinding.FragmentTeasListBinding
import com.canonicalexamples.tearank.util.observeEvent
import com.canonicalexamples.tearank.viewmodels.TeasListViewModel
import com.canonicalexamples.tearank.viewmodels.TeasListViewModelFactory

class TeasListFragment : Fragment() {

    private lateinit var binding: FragmentTeasListBinding
    private val viewModel: TeasListViewModel by viewModels {
        val app = activity?.application as TeaRankApp
        TeasListViewModelFactory(app.database, app.webservice)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTeasListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.adapter = TeasListAdapter(viewModel = viewModel)
        binding.fab.setOnClickListener {
            viewModel.addButtonClicked()
        }

        viewModel.navigate.observeEvent(viewLifecycleOwner) { navigate ->
            if (navigate) {
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            }
        }
    }
}

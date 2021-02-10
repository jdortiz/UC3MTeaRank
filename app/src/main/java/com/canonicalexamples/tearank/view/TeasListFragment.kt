package com.canonicalexamples.tearank.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.canonicalexamples.tearank.R
import com.canonicalexamples.tearank.databinding.FragmentTeasListBinding
import com.canonicalexamples.tearank.viewmodels.TeasListViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class TeasListFragment : Fragment() {

    private lateinit var binding: FragmentTeasListBinding
    private lateinit var viewModel: TeasListViewModel

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
        viewModel = ViewModelProvider(this).get(TeasListViewModel::class.java)

        binding.recyclerView.adapter = TeasListAdapter()
        binding.fab.setOnClickListener {
            viewModel.addButtonClicked()
        }

        viewModel.navigate.observe(viewLifecycleOwner) { navigate ->
            if (navigate) {
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            }
        }
    }
}

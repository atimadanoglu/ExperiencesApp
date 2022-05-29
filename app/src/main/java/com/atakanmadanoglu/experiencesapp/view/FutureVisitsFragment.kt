package com.atakanmadanoglu.experiencesapp.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.atakanmadanoglu.experiencesapp.ExperiencesApplication
import com.atakanmadanoglu.experiencesapp.R
import com.atakanmadanoglu.experiencesapp.adapter.FutureVisitsAdapter
import com.atakanmadanoglu.experiencesapp.databinding.FragmentFutureVisitsBinding
import com.atakanmadanoglu.experiencesapp.viewmodel.FutureVisitsViewModel
import com.atakanmadanoglu.experiencesapp.viewmodel.FutureVisitsViewModelFactory
import com.google.android.material.appbar.MaterialToolbar

class FutureVisitsFragment : Fragment() {

    private var _binding: FragmentFutureVisitsBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: FutureVisitsAdapter
    private lateinit var viewModel: FutureVisitsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFutureVisitsBinding.inflate(inflater, container, false)
        val view = binding.root
        setViewModel()
        setToolbar()
        setAdapter()
        observeValues()
        goToAddFutureVisitFragmentClickListener()
        return view
    }

    private fun observeValues() {
        viewModel.list.observe(viewLifecycleOwner) {
            it?.let {
                adapter.submitList(it)
            }
        }
        viewModel.futureVisitDoneStatus.observe(viewLifecycleOwner) {
            it?.let {
                viewModel.updateDoneStatus()
            }
        }
    }

    private fun setViewModel() {
        val sharedPref =
            requireActivity().getSharedPreferences("userInformation", Context.MODE_PRIVATE)
        val email = sharedPref.getString("email", "")
        email?.let {
            val futureVisitsViewModel: FutureVisitsViewModel by activityViewModels {
                FutureVisitsViewModelFactory(
                    (requireActivity().application as ExperiencesApplication).futureVisitDao,
                    it
                )
            }
            viewModel = futureVisitsViewModel
        }
    }

    private fun goToAddFutureVisitFragmentClickListener() {
        binding.floatingActionButton.setOnClickListener {
            val action = FutureVisitsFragmentDirections
                .actionFutureVisitsFragmentToAddFutureVisitFragment()
            findNavController().navigate(action)
        }
    }

    private fun setAdapter() {
        adapter = FutureVisitsAdapter { futureVisit, isChecked ->
            viewModel.setValues(futureVisit, isChecked)
        }
        binding.futureVisitsRecyclerView.adapter = adapter
    }

    private fun setToolbar() {
        val searchView = requireActivity().findViewById<SearchView>(R.id.home_page_search_view)
        searchView.visibility = View.VISIBLE
        val toolbar = requireActivity().findViewById<MaterialToolbar>(R.id.toolbar)
        toolbar.setTitle(R.string.places_i_want_to_go)
    }
}
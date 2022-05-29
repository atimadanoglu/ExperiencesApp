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
import com.atakanmadanoglu.experiencesapp.adapter.HomePageAdapter
import com.atakanmadanoglu.experiencesapp.databinding.FragmentHomePageBinding
import com.atakanmadanoglu.experiencesapp.viewmodel.HomeViewModel
import com.atakanmadanoglu.experiencesapp.viewmodel.HomeViewModelFactory
import com.google.android.material.appbar.MaterialToolbar

class HomePageFragment : Fragment() {

    private var _binding: FragmentHomePageBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: HomePageAdapter
    private lateinit var viewModel: HomeViewModel
    private lateinit var searchView: SearchView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomePageBinding.inflate(inflater, container, false)
        val view = binding.root
        setViewModel()
        observeValues()
        setAdapter()
        setToolbar()
        fabClickListener()
        searchElement()
        return view
    }

    private fun setViewModel() {
        val sharedPref = requireActivity().getSharedPreferences("userInformation", Context.MODE_PRIVATE)
        val email = sharedPref.getString("email", "")
        email?.let {
            val homeViewModel: HomeViewModel by activityViewModels {
                HomeViewModelFactory(
                    (requireActivity().application as ExperiencesApplication).experienceDao,
                    it
                )
            }
            viewModel = homeViewModel
        }
    }

    private fun fabClickListener() {
        binding.floatingActionButton.setOnClickListener {
            val action = HomePageFragmentDirections
                .actionHomePageFragmentToAddExperienceFragment()
            findNavController().navigate(action)
        }
    }

    private fun setToolbar() {
        searchView = requireActivity().findViewById(R.id.home_page_search_view)
        searchView.visibility = View.VISIBLE
        val toolbar = requireActivity().findViewById<MaterialToolbar>(R.id.toolbar)
        toolbar.setTitle(R.string.home_page_experiences)
    }

    private fun setAdapter() {
        adapter = HomePageAdapter {
            viewModel.setExperience(it)
        }
        binding.homeRecyclerView.adapter = adapter
        binding.lifecycleOwner = viewLifecycleOwner
    }

    private fun observeValues() {
        viewModel.navigate.observe(viewLifecycleOwner) {
            it?.let {
                val action = HomePageFragmentDirections
                    .actionHomePageFragmentToExperienceDetailsFragment()
                findNavController().navigate(action)
                viewModel.navigated()
            }
        }
        viewModel.list.observe(viewLifecycleOwner) {
            it?.let {
                viewModel.setCopyList(it)
            }
        }
        viewModel.copyList.observe(viewLifecycleOwner) {
            it?.let {
                adapter.submitList(it)
            }
        }
    }
    private fun searchElement() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.filter(newText)
                return true
            }
        })
    }
}
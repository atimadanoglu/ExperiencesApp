package com.atakanmadanoglu.experiencesapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.atakanmadanoglu.experiencesapp.R
import com.atakanmadanoglu.experiencesapp.databinding.FragmentHomePageBinding
import com.google.android.material.appbar.MaterialToolbar

class HomePageFragment : Fragment() {

    private var _binding: FragmentHomePageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomePageBinding.inflate(inflater, container, false)
        val view = binding.root
        setToolbar()
        fabClickListener()
        return view
    }

    private fun fabClickListener() {
        binding.floatingActionButton.setOnClickListener {
            val action = HomePageFragmentDirections
                .actionHomePageFragmentToAddExperienceFragment()
            findNavController().navigate(action)
        }
    }

    private fun setToolbar() {
        val searchView = requireActivity().findViewById<SearchView>(R.id.home_page_search_view)
        searchView.visibility = View.VISIBLE
        val toolbar = requireActivity().findViewById<MaterialToolbar>(R.id.toolbar)
        toolbar.setTitle(R.string.home_page_experiences)
    }
}
package com.atakanmadanoglu.experiencesapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.atakanmadanoglu.experiencesapp.ExperiencesApplication
import com.atakanmadanoglu.experiencesapp.R
import com.atakanmadanoglu.experiencesapp.databinding.FragmentAddExperienceBinding
import com.atakanmadanoglu.experiencesapp.viewmodel.AddExperienceViewModel
import com.atakanmadanoglu.experiencesapp.viewmodel.AddExperienceViewModelFactory
import com.google.android.material.appbar.MaterialToolbar

class AddExperienceFragment : Fragment() {

    private var _binding: FragmentAddExperienceBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AddExperienceViewModel by viewModels {
        AddExperienceViewModelFactory(
            (requireActivity().application as ExperiencesApplication).experienceDao)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAddExperienceBinding.inflate(inflater, container, false)
        val view = binding.root
        setToolbar()
        goToMapsPageClickListener()
        return view
    }

    private fun goToMapsPageClickListener() {
        binding.chooseLocationButton.setOnClickListener {
            val action = AddExperienceFragmentDirections
                .actionAddExperienceFragmentToMapsFragment()
            findNavController().navigate(action)
        }
    }

    private fun setToolbar() {
        val toolbar = requireActivity().findViewById<MaterialToolbar>(R.id.toolbar)
        toolbar.setTitle(R.string.add_experience)
        val searchView = requireActivity().findViewById<SearchView>(R.id.home_page_search_view)
        searchView.visibility = View.GONE
    }
}
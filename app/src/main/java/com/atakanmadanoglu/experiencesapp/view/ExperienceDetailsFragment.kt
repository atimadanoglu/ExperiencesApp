package com.atakanmadanoglu.experiencesapp.view

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
import com.atakanmadanoglu.experiencesapp.databinding.FragmentExperienceDetailsBinding
import com.atakanmadanoglu.experiencesapp.viewmodel.HomeViewModel
import com.atakanmadanoglu.experiencesapp.viewmodel.HomeViewModelFactory
import com.google.android.material.appbar.MaterialToolbar

class ExperienceDetailsFragment : Fragment() {

    private var _binding: FragmentExperienceDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by activityViewModels {
        HomeViewModelFactory(
            (requireActivity().application as ExperiencesApplication).experienceDao,
            ""
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentExperienceDetailsBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        setToolbar()
        binding.executePendingBindings()
        binding.imageView.setImageBitmap(viewModel.experience.value?.pictureBitmap)
        binding.backToExperiencesButton.setOnClickListener {
            val action = ExperienceDetailsFragmentDirections
                .actionExperienceDetailsFragmentToHomePageFragment()
            findNavController().navigate(action)
        }
        return view
    }

    private fun setToolbar() {
        val searchView = requireActivity().findViewById<SearchView>(R.id.home_page_search_view)
        searchView.visibility = View.GONE
        val toolbar = requireActivity().findViewById<MaterialToolbar>(R.id.toolbar)
        toolbar.setTitle(R.string.home_page_experiences)
    }
}
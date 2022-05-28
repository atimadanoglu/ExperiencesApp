package com.atakanmadanoglu.experiencesapp.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.atakanmadanoglu.experiencesapp.ExperiencesApplication
import com.atakanmadanoglu.experiencesapp.R
import com.atakanmadanoglu.experiencesapp.databinding.FragmentAddExperienceBinding
import com.atakanmadanoglu.experiencesapp.viewmodel.AddExperienceViewModel
import com.atakanmadanoglu.experiencesapp.viewmodel.AddExperienceViewModelFactory
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.snackbar.Snackbar

class AddExperienceFragment : Fragment() {

    private var _binding: FragmentAddExperienceBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AddExperienceViewModel by activityViewModels {
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
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        setToolbar()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setEditTexts()
        goToMapsPageClickListener()
        observeValues()
        addButtonClickListener()
    }

    private fun observeValues() {
        viewModel.navigate.observe(viewLifecycleOwner) {
            it?.let {
                if (it && viewModel.savedTitle.value != null) {
                    val action = AddExperienceFragmentDirections
                        .actionAddExperienceFragmentToMapsFragment()
                    findNavController().navigate(action)
                    viewModel.navigated()
                }
            }
        }
        viewModel.latitude.observe(viewLifecycleOwner) {
            it?.let {
                if (it != 0.0) {
                    println(it)
                    binding.locationStatus.visibility = View.VISIBLE
                    binding.locationStatus.setText(R.string.location_chosen)
                }
            }
        }
    }

    private fun setEditTexts() {
        if (!viewModel.areTheyNull()) {
            viewModel.title.value = viewModel.savedTitle.value
            viewModel.comments.value = viewModel.savedComments.value
        }
    }

    private fun goToMapsPageClickListener() {
        binding.chooseLocationButton.setOnClickListener {
            viewModel.saveValues()
        }
    }

    private fun addButtonClickListener() {
        binding.addButton.setOnClickListener {
            if (viewModel.title.value.isNullOrEmpty())
                binding.titleLayout.error = " "
            if (viewModel.comments.value.isNullOrEmpty())
                binding.commentLayout.error = " "
            if (!binding.locationStatus.isVisible) {
                Snackbar.make(
                    requireView(),
                    "Please choose a location!",
                    Snackbar.LENGTH_LONG
                ).show()
            }
            if (!viewModel.areTheyNull() && binding.locationStatus.isVisible) {
                val sharedPref = requireActivity()
                    .getSharedPreferences("userInformation", Context.MODE_PRIVATE)
                val email = sharedPref.getString("email", "")
                if (email != null) {
                    viewModel.insert(email).also {
                        val action = AddExperienceFragmentDirections
                            .actionAddExperienceFragmentToHomePageFragment2()
                        findNavController().navigate(action)
                    }
                }
            }
        }
    }

    private fun setToolbar() {
        val toolbar = requireActivity().findViewById<MaterialToolbar>(R.id.toolbar)
        toolbar.setTitle(R.string.add_experience)
        val searchView = requireActivity().findViewById<SearchView>(R.id.home_page_search_view)
        searchView.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.clearData()
    }
}
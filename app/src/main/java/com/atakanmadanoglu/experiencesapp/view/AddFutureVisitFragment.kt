package com.atakanmadanoglu.experiencesapp.view

import android.content.Context
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
import com.atakanmadanoglu.experiencesapp.databinding.FragmentAddFutureVisitBinding
import com.atakanmadanoglu.experiencesapp.utilities.Constants
import com.atakanmadanoglu.experiencesapp.viewmodel.AddFutureVisitViewModel
import com.atakanmadanoglu.experiencesapp.viewmodel.AddFutureVisitsViewModelFactory
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.snackbar.Snackbar

class AddFutureVisitFragment : Fragment() {

    private var _binding: FragmentAddFutureVisitBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AddFutureVisitViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAddFutureVisitBinding.inflate(inflater, container, false)
        val view = binding.root
        setViewModel()
        setToolbar()
        addButtonClickListener()
        checkChipItemsListener()
        return view
    }

    private fun addButtonClickListener() {
        binding.addFutureVisitButton.setOnClickListener {
            if (viewModel.placeName.value.isNullOrEmpty())
                binding.placeNameLayout.error = " "
            else
                binding.placeNameLayout.error = null
            if (viewModel.city.value.isNullOrEmpty())
                binding.cityLayout.error = " "
            else
                binding.cityLayout.error = null
            if (viewModel.district.value.isNullOrEmpty())
                binding.districtLayout.error = " "
            else
                binding.districtLayout.error = null
            if (binding.chipGroup.checkedChipIds.size == 0) {
                Snackbar.make(
                    requireView(),
                    R.string.please_choose_priority_rate,
                    Snackbar.LENGTH_LONG
                ).show()
            }
            if (!viewModel.areTheyNullOrEmpty()) {
                viewModel.insert()
                val action = AddFutureVisitFragmentDirections.actionAddFutureVisitFragmentToFutureVisitsFragment()
                findNavController().navigate(action)
            }
        }
    }

    private fun setViewModel() {
        val sharedPref =
            requireActivity().getSharedPreferences("userInformation", Context.MODE_PRIVATE)
        val email = sharedPref.getString("email", "")
        email?.let {
            val addFutureVisitsViewModel: AddFutureVisitViewModel by viewModels {
                AddFutureVisitsViewModelFactory(
                    (requireActivity().application as ExperiencesApplication).futureVisitDao,
                    it
                )
            }
            viewModel = addFutureVisitsViewModel
            binding.viewModel = viewModel
            binding.lifecycleOwner = viewLifecycleOwner
        }
    }

    private fun checkChipItemsListener() {
        binding.chipGroup.setOnCheckedStateChangeListener { group, _ ->
            val itemName = when(group.checkedChipId) {
                R.id.low -> Constants.LOW
                R.id.medium -> Constants.MEDIUM
                R.id.high -> Constants.HIGH
                else -> ""
            }
            viewModel.setCheckedChip(itemName)
        }
    }

    private fun setToolbar() {
        val searchView = requireActivity().findViewById<SearchView>(R.id.home_page_search_view)
        searchView.visibility = View.GONE
        val toolbar = requireActivity().findViewById<MaterialToolbar>(R.id.toolbar)
        toolbar.setTitle(R.string.add_future_visit)
    }
}
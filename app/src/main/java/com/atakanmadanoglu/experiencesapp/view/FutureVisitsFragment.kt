package com.atakanmadanoglu.experiencesapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import com.atakanmadanoglu.experiencesapp.R
import com.atakanmadanoglu.experiencesapp.databinding.FragmentFutureVisitsBinding
import com.google.android.material.appbar.MaterialToolbar

class FutureVisitsFragment : Fragment() {

    private var _binding: FragmentFutureVisitsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFutureVisitsBinding.inflate(inflater, container, false)
        val view = binding.root
        setToolbar()
        return view
    }

    private fun setToolbar() {
        val searchView = requireActivity().findViewById<SearchView>(R.id.home_page_search_view)
        searchView.visibility = View.VISIBLE
        val toolbar = requireActivity().findViewById<MaterialToolbar>(R.id.toolbar)
        toolbar.setTitle(R.string.places_i_want_to_go)
    }

}
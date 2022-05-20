package com.atakanmadanoglu.experiencesapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.atakanmadanoglu.experiencesapp.databinding.FragmentAddExperienceBinding

class AddExperienceFragment : Fragment() {

    private var _binding: FragmentAddExperienceBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAddExperienceBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

}
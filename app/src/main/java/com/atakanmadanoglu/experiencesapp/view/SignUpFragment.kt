package com.atakanmadanoglu.experiencesapp.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.atakanmadanoglu.experiencesapp.ExperiencesApplication
import com.atakanmadanoglu.experiencesapp.R
import com.atakanmadanoglu.experiencesapp.databinding.FragmentSignUpBinding
import com.atakanmadanoglu.experiencesapp.viewmodel.SignUpViewModel
import com.atakanmadanoglu.experiencesapp.viewmodel.SignUpViewModelFactory
import com.google.android.material.snackbar.Snackbar

class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SignUpViewModel by viewModels {
        SignUpViewModelFactory((requireActivity().application as ExperiencesApplication).userDao)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        observeLiveDataValues()
        goToLoginPageClickListener()
        return view
    }

    private fun setSharedPreferences() {
        val sharedPref = requireActivity()
            .getSharedPreferences("userInformation", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.apply {
            putString("email", viewModel.email.value)
            putBoolean("isSignedIn", true)
            apply()
        }
    }

    private fun goToLoginPageClickListener() {
        binding.goToLoginPageButton.setOnClickListener {
            val action = SignUpFragmentDirections
                .actionSignUpFragmentToLoginFragment()
            findNavController().navigate(action)
        }
    }

    private fun observeLiveDataValues() {
        viewModel.navigateToMainPage.observe(viewLifecycleOwner) {
            it?.let {
                if (it) {
                    setSharedPreferences()
                    val intent = Intent(requireContext(), HomePageActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                }
            }
        }
        viewModel.arePasswordsSame.observe(viewLifecycleOwner) {
            it?.let {
                if (!it) {
                    val errorMessage = resources.getString(R.string.passwords_dont_match)
                    binding.passwordLayout.error = errorMessage
                    binding.confirmPasswordLayout.error = " "
                } else {
                    binding.passwordLayout.error = null
                    binding.confirmPasswordLayout.error = null
                }
            }
        }
        viewModel.areNullOrEmpty.observe(viewLifecycleOwner) {
            it?.let {
                if (it) {
                    Snackbar.make(
                        requireView(),
                        R.string.fill_in_the_blanks,
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}
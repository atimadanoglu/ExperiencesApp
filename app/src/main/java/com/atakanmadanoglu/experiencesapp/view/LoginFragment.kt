package com.atakanmadanoglu.experiencesapp.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.atakanmadanoglu.experiencesapp.ExperiencesApplication
import com.atakanmadanoglu.experiencesapp.R
import com.atakanmadanoglu.experiencesapp.databinding.FragmentLoginBinding
import com.atakanmadanoglu.experiencesapp.viewmodel.LoginViewModel
import com.atakanmadanoglu.experiencesapp.viewmodel.LoginViewModelFactory
import com.google.android.material.snackbar.Snackbar

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LoginViewModel by viewModels {
        LoginViewModelFactory((requireActivity().application as ExperiencesApplication).userDao)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        observeData()
        signUpTextClickListener()
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

    private fun observeData() {
        viewModel.user.observe(viewLifecycleOwner) {
            if (it == null) {
                Snackbar.make(
                    requireView(),
                    R.string.there_is_no_such_a_user,
                    Snackbar.LENGTH_LONG
                ).show()
            }
            it?.let {
                viewModel.setDoesExist()
            }
        }
        viewModel.doesExist.observe(viewLifecycleOwner) {
            if (it == true) {
                viewModel.retrieveAndCheckPassword()
            } else {
                Snackbar.make(
                    requireView(),
                    R.string.there_is_no_such_a_user,
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
        viewModel.areEmptyOrNull.observe(viewLifecycleOwner) {
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
        viewModel.isPasswordCorrect.observe(viewLifecycleOwner) {
            it?.let {
                if (it) {
                    setSharedPreferences()
                    val intent = Intent(requireContext(), HomePageActivity::class.java)
                    startActivity(intent)
                    activity?.finish()
                    viewModel.navigatedToHomePage()
                } else {
                    Snackbar.make(
                        requireView(),
                        R.string.wrong_password,
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun signUpTextClickListener() {
        binding.signUpText.setOnClickListener {
            val action = LoginFragmentDirections
                .actionLoginFragmentToSignUpFragment()
            findNavController().navigate(action)
        }
    }
}
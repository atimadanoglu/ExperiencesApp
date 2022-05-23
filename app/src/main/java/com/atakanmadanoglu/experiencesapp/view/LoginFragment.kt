package com.atakanmadanoglu.experiencesapp.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.atakanmadanoglu.experiencesapp.ExperiencesApplication
import com.atakanmadanoglu.experiencesapp.databinding.FragmentLoginBinding
import com.atakanmadanoglu.experiencesapp.viewmodel.LoginViewModel
import com.atakanmadanoglu.experiencesapp.viewmodel.LoginViewModelFactory

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
        loginButtonClickListener()
        return view
    }

    private fun observeData() {
        viewModel.email.observe(viewLifecycleOwner) {
            it?.let {
                println("email: $it")
            }
        }
        viewModel.password.observe(viewLifecycleOwner) {
            it?.let {
                println("password: $it")
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

    private fun loginButtonClickListener() {
        binding.loginButton.setOnClickListener {
            val intent = Intent(requireContext(), HomePageActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }
}
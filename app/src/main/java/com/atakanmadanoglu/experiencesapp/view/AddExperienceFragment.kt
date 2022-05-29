package com.atakanmadanoglu.experiencesapp.view

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.atakanmadanoglu.experiencesapp.ExperiencesApplication
import com.atakanmadanoglu.experiencesapp.R
import com.atakanmadanoglu.experiencesapp.databinding.FragmentAddExperienceBinding
import com.atakanmadanoglu.experiencesapp.viewmodel.AddExperienceViewModel
import com.atakanmadanoglu.experiencesapp.viewmodel.AddExperienceViewModelFactory
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class AddExperienceFragment : Fragment() {

    private var _binding: FragmentAddExperienceBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AddExperienceViewModel by activityViewModels {
        AddExperienceViewModelFactory(
            (requireActivity().application as ExperiencesApplication).experienceDao
        )
    }
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    private var selectedPicture : Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAddExperienceBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        registerLauncher()
        setToolbar()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setEditTexts()
        goToMapsPageClickListener()
        observeValues()
        addButtonClickListener()
        selectImageButtonClickListener()
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
        viewModel.selectedPicture.observe(viewLifecycleOwner) {
            it?.let {
                binding.experienceImageView.setImageURI(it)
                getBitmap(it)
            }
        }
    }

    private fun getBitmap(uri: Uri) {
        viewLifecycleOwner.lifecycleScope.launch {
            val loading = ImageLoader(requireContext())
            val request = ImageRequest.Builder(requireContext())
                .data(uri)
                .build()
            (loading.execute(request) as SuccessResult).drawable.also {
                viewModel.setImageBitmap((it as BitmapDrawable).bitmap)
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
            else
                binding.titleLayout.error = null
            if (viewModel.comments.value.isNullOrEmpty())
                binding.commentLayout.error = " "
            else
                binding.commentLayout.error = null
            if (!binding.locationStatus.isVisible) {
                Snackbar.make(
                    requireView(),
                    R.string.please_choose_location,
                    Snackbar.LENGTH_LONG
                ).show()
            }
            if (viewModel.selectedPicture.value == null) {
                Snackbar.make(
                    requireView(),
                    R.string.please_add_picture,
                    Snackbar.LENGTH_LONG
                ).show()
            }
            if (viewModel.isValid()) {
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

    private fun selectImageButtonClickListener() {
        binding.experienceImageView.setOnClickListener {
            if (ContextCompat
                    .checkSelfPermission(
                        requireContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED
            ) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    Snackbar.make(requireView(), R.string.permission_needed_for_gallery, Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.give_permission) {
                            permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                        }.show()
                } else {
                    permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                }
            }else {
                val intentToGallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                activityResultLauncher.launch(intentToGallery)
            }
        }
    }

    private fun registerLauncher() {
        activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intentFromResult = result.data
                if (intentFromResult != null) {
                    selectedPicture = intentFromResult.data
                    viewModel.setSelectedPicture(selectedPicture)
                }
            }
        }

        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
            if (result) {
                // permission granted
                val intentToGallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                activityResultLauncher.launch(intentToGallery)
            } else {
                Toast.makeText(this.context, R.string.permission_needed_for_gallery, Toast.LENGTH_LONG).show()
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
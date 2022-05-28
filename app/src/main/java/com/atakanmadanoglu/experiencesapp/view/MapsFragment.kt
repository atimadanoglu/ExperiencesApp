package com.atakanmadanoglu.experiencesapp.view

import android.app.Service.LOCATION_SERVICE
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.atakanmadanoglu.experiencesapp.databinding.FragmentMapsBinding
import com.atakanmadanoglu.experiencesapp.viewmodel.MapsViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar

class MapsFragment : Fragment() {

    private var _binding: FragmentMapsBinding? = null
    private val binding get() = _binding!!
    private lateinit var map: GoogleMap
    private lateinit var locationManager: LocationManager
    private lateinit var locationListener: LocationListener
    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    private lateinit var sharedPref: SharedPreferences
    private var trackBoolean: Boolean? = null
    private val viewModel: MapsViewModel by viewModels()

    private val callback = OnMapReadyCallback { googleMap ->
        map = googleMap
        mapLongClickListener(googleMap)

        /*mapLongClickListener(googleMap)*/

        locationManager = requireActivity().getSystemService(LOCATION_SERVICE) as LocationManager
        locationListener = LocationListener { p0 ->
            trackBoolean = sharedPref.getBoolean("trackBoolean", false)
            if (!trackBoolean!!) {
                val userLocation = LatLng(p0.latitude, p0.longitude)
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(userLocation))
                sharedPref.edit().putBoolean("trackBoolean", true).apply()
            }
        }
        if (ContextCompat.checkSelfPermission(requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Snackbar.make(
                requireView(),
                "Permission needed for location",
                Snackbar.LENGTH_INDEFINITE
            ).setAction("Give Permission") {
                permissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
            }.show()
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, locationListener)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = binding.map.getFragment() as SupportMapFragment
        mapFragment.getMapAsync(callback)

        registerLauncher()
        sharedPref = requireActivity().getSharedPreferences("maps", Context.MODE_PRIVATE)
        trackBoolean = false
    }

    private fun registerLauncher() {
        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
            if (result) {
                if (ContextCompat
                        .checkSelfPermission(requireContext(),
                        android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager
                        .requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            0,
                            0f,
                            locationListener)
                    val lastLocation =
                        locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                    if (lastLocation != null) {
                        val lastUserLocation = LatLng(lastLocation.latitude, lastLocation.longitude)
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(lastUserLocation, 15f))
                    }
                    map.isMyLocationEnabled = true
                } else {
                    Snackbar.make(
                        requireView(),
                        "Permission needed!",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun mapLongClickListener(googleMap: GoogleMap) {
        googleMap.setOnMapLongClickListener {
            googleMap.clear()
            googleMap.addMarker(MarkerOptions().position(it))
            viewModel.setLatitude(it.latitude)
            viewModel.setLongitude(it.longitude)
        }
    }
}
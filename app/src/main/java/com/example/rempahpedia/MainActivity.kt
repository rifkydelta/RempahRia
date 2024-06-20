package com.example.rempahpedia

import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import com.example.rempahpedia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                viewModel.showToastMessage("Permission request granted")
            } else {
                viewModel.showToastMessage("Permission request denied")
            }
            viewModel.permissionGranted.value = isGranted
        }

    companion object {
        const val REQUIRED_PERMISSION = android.Manifest.permission.CAMERA
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        replaceFragment(HomeFragment())
        setTopBarVisibility(MainViewModel.FragmentTag.HOME)

        viewModel.permissionGranted.observe(this, Observer { isGranted ->
            if (isGranted == false) {
                requestPermissionLauncher.launch(REQUIRED_PERMISSION)
            }
        })

        viewModel.checkPermission(REQUIRED_PERMISSION)

        // Observasi currentFragment dari ViewModel
        viewModel.currentFragment.observe(this, Observer { fragmentTag ->
            fragmentTag?.let { tag ->
                when (tag) {
                    MainViewModel.FragmentTag.HOME -> {
                        replaceFragment(HomeFragment())
                        setTopBarVisibility(MainViewModel.FragmentTag.HOME)
                    }

                    MainViewModel.FragmentTag.SCAN -> {
                        replaceFragment(ScanFragment())
                        setTopBarVisibility(MainViewModel.FragmentTag.SCAN)
                    }

                    MainViewModel.FragmentTag.LIST -> {
                        replaceFragment(ListFragment())
                        setTopBarVisibility(MainViewModel.FragmentTag.LIST)
                    }
                }
            }
        })

        // Set listener untuk bottom navigation
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    viewModel.setCurrentFragment(MainViewModel.FragmentTag.HOME)
                    true
                }

                R.id.scan -> {
                    viewModel.setCurrentFragment(MainViewModel.FragmentTag.SCAN)
                    true
                }

                R.id.list -> {
                    viewModel.setCurrentFragment(MainViewModel.FragmentTag.LIST)
                    true
                }

                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: androidx.fragment.app.Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }

    private fun setTopBarVisibility(fragmentTag: MainViewModel.FragmentTag) {
        when (fragmentTag) {
            MainViewModel.FragmentTag.HOME, MainViewModel.FragmentTag.LIST -> {
                binding.topBar.visibility = View.VISIBLE
                binding.imgTopBar.visibility = View.VISIBLE
            }

            MainViewModel.FragmentTag.SCAN -> {
                binding.topBar.visibility = View.GONE
                binding.imgTopBar.visibility = View.GONE
            }
        }
    }
}

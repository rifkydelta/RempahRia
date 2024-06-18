package com.example.rempahpedia

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.rempahpedia.databinding.ActivityDetailSpiceBinding

class DetailSpiceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailSpiceBinding
    private val viewModel: DetailSpiceViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailSpiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra(EXTRA_NAME)
        val latinName = intent.getStringExtra(EXTRA_LATIN_NAME)
        val photo = intent.getIntExtra(EXTRA_PHOTO, -1)

        viewModel.loadSpiceDetails(name, latinName, photo)

        viewModel.spiceName.observe(this, Observer { name ->
            binding.tvSpiceName.text = name
        })

        viewModel.latinName.observe(this, Observer { latinName ->
            binding.tvSpiceLatin.text = latinName
        })

        viewModel.photoResId.observe(this, Observer { photoResId ->
            if (photoResId != -1) {
                binding.mainImage.setImageResource(photoResId)
            }
        })
    }

    companion object {
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_LATIN_NAME = "extra_latin_name"
        const val EXTRA_PHOTO = "extra_photo"
    }
}

package com.example.rempahpedia

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.rempahpedia.databinding.ActivityDetailSpiceBinding

class DetailSpice : AppCompatActivity() {
    private lateinit var binding: ActivityDetailSpiceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailSpiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra(EXTRA_NAME)
        val latinName = intent.getStringExtra(EXTRA_LATIN_NAME)
        val photo = intent.getIntExtra(EXTRA_PHOTO, -1)

        binding.tvSpiceName.text = name
        binding.tvSpiceLatin.text = latinName
        if (photo != -1) {
            binding.mainImage.setImageResource(photo)
        }
    }

    companion object {
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_LATIN_NAME = "extra_latin_name"
        const val EXTRA_PHOTO = "extra_photo"
    }
}
package com.example.rempahpedia

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.rempahpedia.databinding.ActivityDetailSpiceBinding
import org.json.JSONObject

class DetailSpiceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailSpiceBinding
    private val viewModel: DetailSpiceViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailSpiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra(EXTRA_NAME)
        if (name.isNullOrEmpty()) {
            val response = intent.getStringExtra("response")
            response?.let {
                displayDataFromResponse(it)
            }
        } else {
            displayDataFromIntent()
        }
    }

    private fun displayDataFromIntent() {
        val name = intent.getStringExtra(EXTRA_NAME)
        val latinName = intent.getStringExtra(EXTRA_LATIN_NAME)
        val photoUrl = intent.getStringExtra(EXTRA_PHOTO)
        val benefit = intent.getStringExtra(EXTRA_BENEFIT)
        val description = intent.getStringExtra(EXTRA_DESCRIPTION)
        val uniqueFact = intent.getStringExtra(EXTRA_UNIQUE_FACT)
        val similar = intent.getIntExtra(EXTRA_SIMILAR.toString(), 0)

        viewModel.loadSpiceDetails(
            name,
            latinName,
            photoUrl,
            benefit,
            description,
            uniqueFact,
            similar
        )

        viewModel.spiceName.observe(this) { name ->
            binding.tvSpiceName.text = name
        }

        viewModel.latinName.observe(this) { latinName ->
            binding.tvSpiceLatin.text = latinName
        }

        viewModel.photoUrl.observe(this) { photoUrl ->
            Glide.with(this).load(photoUrl).into(binding.mainImage)
        }

        viewModel.benefit.observe(this) { benefit ->
            binding.tvBenefit.text = benefit
        }

        viewModel.description.observe(this) { description ->
            binding.tvDescription.text = description
        }

        viewModel.uniqueFact.observe(this) { uniqueFact ->
            binding.tvFunFact.text = uniqueFact
        }

        viewModel.similar.observe(this) { similar ->
            val similarityPercentage = similar?.times(100)
            if (similarityPercentage != null && similarityPercentage != 0) {
                binding.tvSimilarity.text = "Kemiripan ${similarityPercentage}%"
            } else {
                binding.tvSimilarity.text = ""
            }
        }
    }

    private fun displayDataFromResponse(response: String) {
        val jsonResponse = JSONObject(response)
        val spiceClass = jsonResponse.getJSONObject("class")

        val name = spiceClass.getString("name")
        val description = spiceClass.getString("description")
        val benefit = spiceClass.getString("benefit")
        val scientificName = spiceClass.getString("cientific_name")
        val uniqueFact = spiceClass.getString("unique_fact")
        val imageUrl1 = spiceClass.getString("image_url1")
        val imageUrl2 = spiceClass.getString("image_url2")
        val confidence = jsonResponse.getDouble("confidence")

        binding.tvSpiceName.text = name
        binding.tvSpiceLatin.text = scientificName
        binding.tvDescription.text = description
        binding.tvBenefit.text = benefit
        binding.tvFunFact.text = uniqueFact
        binding.tvSimilarity.text = "Kemiripan: ${(confidence * 100).toInt()}%"

        Glide.with(this).load(imageUrl1).into(binding.mainImage)
    }

    companion object {
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_LATIN_NAME = "extra_latin_name"
        const val EXTRA_PHOTO = "extra_photo_url"
        const val EXTRA_BENEFIT = "extra_benefit"
        const val EXTRA_DESCRIPTION = "extra_description"
        const val EXTRA_UNIQUE_FACT = "extra_unique_fact"
        const val EXTRA_SIMILAR = 0
    }
}

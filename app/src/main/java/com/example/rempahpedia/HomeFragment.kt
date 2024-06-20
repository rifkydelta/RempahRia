package com.example.rempahpedia

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rempahpedia.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var rvCardOne: RecyclerView
    private lateinit var rvFunFact: RecyclerView
    private lateinit var progressBar: View
    private lateinit var highlight: View
    private lateinit var faktaUnik: View

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvCardOne = binding.rvCardOne
        rvFunFact = binding.rvFunFact
        progressBar = binding.progressBarHome
        highlight = binding.highlights
        faktaUnik = binding.faktaUnik

        progressBar.visibility = View.VISIBLE
        highlight.visibility = View.GONE
        faktaUnik.visibility = View.GONE

        rvCardOne.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvCardOne.setHasFixedSize(true)

        // Observe spices data
        viewModel.spices.observe(viewLifecycleOwner, Observer { spices ->
            showRecyclerList(spices)
        })

        // Load spices data
        viewModel.loadSpices()

        // Set up GridLayoutManager for rv_fun_fact
        val layoutManager = GridLayoutManager(context, 2)
        rvFunFact.layoutManager = layoutManager
        rvFunFact.setHasFixedSize(true)
    }

    private fun showRecyclerList(spices: List<Spices>) {
        progressBar.visibility = View.GONE
        rvCardOne.visibility = View.VISIBLE
        rvFunFact.visibility = View.VISIBLE
        highlight.visibility = View.VISIBLE
        faktaUnik.visibility = View.VISIBLE

        val randomList = spices.shuffled().take(4)
        val listHomeAdapter = ListHomeAdapter(randomList)
        val funFactAdapter = FunFactAdapter(randomList)
        rvCardOne.adapter = listHomeAdapter
        rvFunFact.adapter = funFactAdapter

        listHomeAdapter.setOnItemClickCallback(object : ListHomeAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Spices) {
                showSelectedSpices(data)
            }
        })

        funFactAdapter.setOnItemClickCallback(object : ListHomeAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Spices) {
                showSelectedSpices(data)
            }
        })
    }

    private fun showSelectedSpices(spice: Spices) {
        val intent = Intent(activity, DetailSpiceActivity::class.java)
        intent.putExtra(DetailSpiceActivity.EXTRA_NAME, spice.name)
        intent.putExtra(DetailSpiceActivity.EXTRA_LATIN_NAME, spice.cientificName)
        intent.putExtra(DetailSpiceActivity.EXTRA_PHOTO, spice.imageUrl1)
        intent.putExtra(DetailSpiceActivity.EXTRA_DESCRIPTION, spice.description)
        intent.putExtra(DetailSpiceActivity.EXTRA_UNIQUE_FACT, spice.uniqueFact)
        intent.putExtra(DetailSpiceActivity.EXTRA_BENEFIT, spice.benefit)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

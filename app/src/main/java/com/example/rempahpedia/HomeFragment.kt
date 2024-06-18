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

        rvCardOne.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvCardOne.setHasFixedSize(true)

        // Observe spices data
        viewModel.spices.observe(viewLifecycleOwner, Observer { spices ->
            showRecyclerList(spices)
        })

        // Load spices data
        viewModel.loadSpices(resources)

        // Set up GridLayoutManager for rv_fun_fact
        val layoutManager = GridLayoutManager(context, 2)
        rvFunFact.layoutManager = layoutManager
        rvFunFact.setHasFixedSize(true)

        // Observe fun facts data
        viewModel.funFacts.observe(viewLifecycleOwner, Observer { funFacts ->
            val funFactAdapter = FunFactAdapter(funFacts)
            rvFunFact.adapter = funFactAdapter
        })
    }

    private fun showRecyclerList(spices: List<Spice>) {
        val randomList = spices.shuffled().take(5)
        val listHomeAdapter = ListHomeAdapter(randomList)
        rvCardOne.adapter = listHomeAdapter

        listHomeAdapter.setOnItemClickCallback(object : ListHomeAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Spice) {
                showSelectedSpices(data)
            }
        })
    }

    private fun showSelectedSpices(spice: Spice) {
        val intent = Intent(activity, DetailSpiceActivity::class.java)
        intent.putExtra(DetailSpiceActivity.EXTRA_NAME, spice.name)
        intent.putExtra(DetailSpiceActivity.EXTRA_LATIN_NAME, spice.latinName)
        intent.putExtra(DetailSpiceActivity.EXTRA_PHOTO, spice.photo)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

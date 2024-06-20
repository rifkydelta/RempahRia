package com.example.rempahpedia

import ListViewModel
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rempahpedia.databinding.FragmentListBinding

class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private lateinit var rvSpices: RecyclerView
    private lateinit var progressBar: View
    private lateinit var tvListRempah: View
    private val viewModel: ListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvSpices = binding.rvSpices
        progressBar = binding.progressBarList
        tvListRempah = binding.tvListRempah

        progressBar.visibility = View.VISIBLE
        tvListRempah.visibility = View.GONE

        rvSpices.layoutManager = LinearLayoutManager(context)
        rvSpices.setHasFixedSize(true)

        // Observe data dari ViewModel
        viewModel.spices.observe(viewLifecycleOwner, Observer { spices ->
            showRecyclerList(spices)
        })

        // Load data spices
        viewModel.loadSpices()
    }

    private fun showRecyclerList(spices: List<Spices>) {
        progressBar.visibility = View.GONE
        rvSpices.visibility = View.VISIBLE
        tvListRempah.visibility = View.VISIBLE

        val listSpicesAdapter = ListSpicesAdapter(spices)
        rvSpices.adapter = listSpicesAdapter

        listSpicesAdapter.setOnItemClickCallback(object : ListSpicesAdapter.OnItemClickCallback {
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

package com.example.rempahpedia

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
        rvSpices.layoutManager = LinearLayoutManager(context)
        rvSpices.setHasFixedSize(true)

        // Observe data dari ViewModel
        viewModel.spices.observe(viewLifecycleOwner, Observer { spices ->
            showRecyclerList(spices)
        })

        // Load data spices
        viewModel.loadSpices(resources)
    }

    private fun showRecyclerList(spices: List<Spice>) {
        val listSpicesAdapter = ListSpicesAdapter(spices)
        rvSpices.adapter = listSpicesAdapter

        listSpicesAdapter.setOnItemClickCallback(object : ListSpicesAdapter.OnItemClickCallback {
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

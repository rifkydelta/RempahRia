package com.example.rempahpedia

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rempahpedia.databinding.FragmentHomeBinding

class Home : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var rvCardOne: RecyclerView
    private val list = ArrayList<Spice>()

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
        rvCardOne.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvCardOne.setHasFixedSize(true)

        list.addAll(getListSpices())
        showRecyclerList()
    }

    private fun getListSpices(): ArrayList<Spice> {
        val dataName = resources.getStringArray(R.array.spice_name_list)
        val dataLatinName = resources.getStringArray(R.array.spice_latin_name_list)
        val dataPhoto = resources.obtainTypedArray(R.array.spice_photo_list)
        val listSpices = ArrayList<Spice>()
        for (i in dataName.indices) {
            val spice = Spice(dataName[i], dataLatinName[i], dataPhoto.getResourceId(i, -1))
            listSpices.add(spice)
        }
        dataPhoto.recycle()
        return listSpices
    }

    private fun showRecyclerList() {
        val randomList = list.shuffled().take(5)
        val listHomeAdapter = ListHomeAdapter(randomList)
        rvCardOne.adapter = listHomeAdapter

        listHomeAdapter.setOnItemClickCallback(object : ListHomeAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Spice) {
                showSelectedSpices(data)
            }
        })
    }

    private fun showSelectedSpices(spice: Spice) {
        val intent = Intent(activity, DetailSpice::class.java)
        intent.putExtra(DetailSpice.EXTRA_NAME, spice.name)
        intent.putExtra(DetailSpice.EXTRA_LATIN_NAME, spice.latinName)
        intent.putExtra(DetailSpice.EXTRA_PHOTO, spice.photo)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

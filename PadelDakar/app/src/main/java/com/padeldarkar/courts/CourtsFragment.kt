package com.padeldarkar.courts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.padeldarkar.adapters.CourtAdapter
import com.padeldarkar.database.AppDatabase
import com.padeldarkar.databinding.FragmentCourtsBinding
import kotlinx.coroutines.launch

class CourtsFragment : Fragment() {

    private var _binding: FragmentCourtsBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: CourtAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCourtsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = CourtAdapter(emptyList())
        binding.rvTerrains.layoutManager = LinearLayoutManager(requireContext())
        binding.rvTerrains.adapter = adapter

        chargerTerrains()
    }

    private fun chargerTerrains() {
        binding.progressBar.visibility = View.VISIBLE
        val db = AppDatabase.getInstance(requireContext())

        lifecycleScope.launch {
            val terrains = db.courtDao().tousLesTerrains()
            binding.progressBar.visibility = View.GONE
            adapter.updateList(terrains)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

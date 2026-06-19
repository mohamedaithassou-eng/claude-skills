package com.padeldarkar.courts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.padeldarkar.adapters.CourtAdapter
import com.padeldarkar.databinding.FragmentCourtsBinding
import com.padeldarkar.models.Court

class CourtsFragment : Fragment() {

    private var _binding: FragmentCourtsBinding? = null
    private val binding get() = _binding!!
    private val db = FirebaseFirestore.getInstance()
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

        db.collection("terrains")
            .get()
            .addOnSuccessListener { documents ->
                binding.progressBar.visibility = View.GONE
                val terrains = documents.toObjects(Court::class.java)

                if (terrains.isEmpty()) {
                    // Ajouter des terrains fictifs pour la démo
                    val terrainsDemo = listOf(
                        Court("1", "Padel Club Dakar", "Route de Ouakam", "Ouakam", "+221 77 000 0001", 15000, 3),
                        Court("2", "Almadies Padel", "Les Almadies", "Almadies", "+221 77 000 0002", 12000, 2),
                        Court("3", "Mermoz Padel Center", "Mermoz", "Mermoz", "+221 77 000 0003", 10000, 4),
                        Court("4", "Point E Tennis & Padel", "Point E", "Plateau", "+221 77 000 0004", 14000, 2),
                        Court("5", "Ngor Padel", "Ngor", "Ngor", "+221 77 000 0005", 11000, 1)
                    )
                    adapter.updateList(terrainsDemo)
                } else {
                    adapter.updateList(terrains)
                }
            }
            .addOnFailureListener {
                binding.progressBar.visibility = View.GONE
                Toast.makeText(requireContext(), "Erreur de chargement", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

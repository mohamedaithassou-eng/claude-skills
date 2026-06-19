package com.padeldarkar.players

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.padeldarkar.adapters.PlayerAdapter
import com.padeldarkar.databinding.FragmentPlayersBinding
import com.padeldarkar.models.Player

class PlayersFragment : Fragment() {

    private var _binding: FragmentPlayersBinding? = null
    private val binding get() = _binding!!
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private lateinit var adapter: PlayerAdapter
    private var niveauFiltre = "Tous"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPlayersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = PlayerAdapter(emptyList()) { joueur -> demanderMatch(joueur) }
        binding.rvJoueurs.layoutManager = LinearLayoutManager(requireContext())
        binding.rvJoueurs.adapter = adapter

        // Filtre par niveau
        val niveaux = listOf("Tous", "Débutant", "Intermédiaire", "Avancé")
        val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, niveaux)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerNiveau.adapter = spinnerAdapter

        binding.spinnerNiveau.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                niveauFiltre = niveaux[position]
                chargerJoueurs()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        chargerJoueurs()
    }

    private fun chargerJoueurs() {
        binding.progressBar.visibility = View.VISIBLE
        val moi = auth.currentUser?.uid

        var query = db.collection("joueurs")
            .whereEqualTo("disponible", true)

        db.collection("joueurs")
            .whereEqualTo("disponible", true)
            .get()
            .addOnSuccessListener { documents ->
                binding.progressBar.visibility = View.GONE
                var joueurs = documents.toObjects(Player::class.java)
                    .filter { it.uid != moi }  // Exclure soi-même

                if (niveauFiltre != "Tous") {
                    joueurs = joueurs.filter { it.niveau == niveauFiltre }
                }

                if (joueurs.isEmpty()) {
                    binding.tvAucunJoueur.visibility = View.VISIBLE
                    binding.rvJoueurs.visibility = View.GONE
                } else {
                    binding.tvAucunJoueur.visibility = View.GONE
                    binding.rvJoueurs.visibility = View.VISIBLE
                    adapter.updateList(joueurs)
                }
            }
            .addOnFailureListener {
                binding.progressBar.visibility = View.GONE
                Toast.makeText(requireContext(), "Erreur de chargement", Toast.LENGTH_SHORT).show()
            }
    }

    private fun demanderMatch(joueur: Player) {
        val moi = auth.currentUser ?: return

        db.collection("joueurs").document(moi.uid).get()
            .addOnSuccessListener { doc ->
                val monProfil = doc.toObject(Player::class.java) ?: return@addOnSuccessListener
                val demande = hashMapOf(
                    "demandeurUid" to moi.uid,
                    "demandeurNom" to "${monProfil.prenom} ${monProfil.nom}",
                    "destinataireUid" to joueur.uid,
                    "niveau" to joueur.niveau,
                    "statut" to "en_attente",
                    "timestamp" to System.currentTimeMillis()
                )

                db.collection("demandes_match").add(demande)
                    .addOnSuccessListener {
                        Toast.makeText(requireContext(),
                            "Demande envoyée à ${joueur.prenom} ${joueur.nom} !", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(requireContext(), "Erreur lors de l'envoi", Toast.LENGTH_SHORT).show()
                    }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

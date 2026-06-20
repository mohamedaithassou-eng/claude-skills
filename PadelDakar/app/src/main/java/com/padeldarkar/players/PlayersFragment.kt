package com.padeldarkar.players

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.padeldarkar.adapters.PlayerAdapter
import com.padeldarkar.database.AppDatabase
import com.padeldarkar.databinding.FragmentPlayersBinding
import com.padeldarkar.models.MatchRequest
import com.padeldarkar.models.Player
import com.padeldarkar.utils.SessionManager
import kotlinx.coroutines.launch

class PlayersFragment : Fragment() {

    private var _binding: FragmentPlayersBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: PlayerAdapter
    private lateinit var session: SessionManager
    private var niveauFiltre = "Tous"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPlayersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        session = SessionManager(requireContext())

        adapter = PlayerAdapter(emptyList()) { joueur -> demanderMatch(joueur) }
        binding.rvJoueurs.layoutManager = LinearLayoutManager(requireContext())
        binding.rvJoueurs.adapter = adapter

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
        val db = AppDatabase.getInstance(requireContext())
        val monId = session.getUid()

        lifecycleScope.launch {
            val joueurs = if (niveauFiltre == "Tous") {
                db.playerDao().joueursDipsonibles(monId)
            } else {
                db.playerDao().joueursParNiveau(niveauFiltre, monId)
            }

            binding.progressBar.visibility = View.GONE

            if (joueurs.isEmpty()) {
                binding.tvAucunJoueur.visibility = View.VISIBLE
                binding.rvJoueurs.visibility = View.GONE
            } else {
                binding.tvAucunJoueur.visibility = View.GONE
                binding.rvJoueurs.visibility = View.VISIBLE
                adapter.updateList(joueurs)
            }
        }
    }

    private fun demanderMatch(joueur: Player) {
        val db = AppDatabase.getInstance(requireContext())
        val monId = session.getUid()
        val monNom = session.getNomComplet()

        lifecycleScope.launch {
            val demande = MatchRequest(
                demandeurId = monId,
                demandeurNom = monNom,
                destinataireId = joueur.uid,
                niveau = joueur.niveau
            )
            db.matchRequestDao().inserer(demande)

            activity?.runOnUiThread {
                Toast.makeText(requireContext(),
                    "Demande envoyée à ${joueur.prenom} ${joueur.nom} !", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

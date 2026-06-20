package com.padeldarkar.players

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.padeldarkar.database.AppDatabase
import com.padeldarkar.databinding.FragmentProfileBinding
import com.padeldarkar.utils.SessionManager
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var session: SessionManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        session = SessionManager(requireContext())
        chargerProfil()

        binding.switchDisponible.setOnCheckedChangeListener { _, isChecked ->
            val uid = session.getUid()
            val db = AppDatabase.getInstance(requireContext())
            lifecycleScope.launch {
                db.playerDao().majDisponibilite(uid, isChecked)
                val statut = if (isChecked) "Disponible" else "Non disponible"
                activity?.runOnUiThread {
                    Toast.makeText(requireContext(), "Statut : $statut", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.btnSauvegarder.setOnClickListener { sauvegarderProfil() }
    }

    private fun chargerProfil() {
        val uid = session.getUid()
        val db = AppDatabase.getInstance(requireContext())
        binding.progressBar.visibility = View.VISIBLE

        lifecycleScope.launch {
            val joueur = db.playerDao().trouverParId(uid)
            binding.progressBar.visibility = View.GONE

            if (joueur == null) return@launch

            binding.tvNomComplet.text = "${joueur.prenom} ${joueur.nom}"
            binding.tvEmail.text = joueur.email
            binding.etTelephone.setText(joueur.telephone)
            binding.etQuartier.setText(joueur.quartier)
            binding.switchDisponible.isChecked = joueur.disponible
            binding.tvMatchsJoues.text = "Matchs joués : ${joueur.matchsJoues}"

            binding.rgNiveau.check(
                when (joueur.niveau) {
                    "Intermédiaire" -> binding.rbIntermediaire.id
                    "Avancé" -> binding.rbAvance.id
                    else -> binding.rbDebutant.id
                }
            )

            val couleur = when (joueur.niveau) {
                "Avancé" -> android.graphics.Color.parseColor("#FF6B35")
                "Intermédiaire" -> android.graphics.Color.parseColor("#2196F3")
                else -> android.graphics.Color.parseColor("#4CAF50")
            }
            binding.tvNiveau.setBackgroundColor(couleur)
            binding.tvNiveau.text = joueur.niveau
        }
    }

    private fun sauvegarderProfil() {
        val uid = session.getUid()
        val db = AppDatabase.getInstance(requireContext())

        val niveau = when (binding.rgNiveau.checkedRadioButtonId) {
            binding.rbIntermediaire.id -> "Intermédiaire"
            binding.rbAvance.id -> "Avancé"
            else -> "Débutant"
        }

        lifecycleScope.launch {
            val joueur = db.playerDao().trouverParId(uid) ?: return@launch
            val mise_a_jour = joueur.copy(
                telephone = binding.etTelephone.text.toString(),
                quartier = binding.etQuartier.text.toString(),
                niveau = niveau
            )
            db.playerDao().mettrAJour(mise_a_jour)

            activity?.runOnUiThread {
                Toast.makeText(requireContext(), "Profil mis à jour !", Toast.LENGTH_SHORT).show()
                chargerProfil()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

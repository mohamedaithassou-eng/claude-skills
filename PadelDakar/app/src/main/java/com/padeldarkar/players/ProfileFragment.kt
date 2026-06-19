package com.padeldarkar.players

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.padeldarkar.databinding.FragmentProfileBinding
import com.padeldarkar.models.Player

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chargerProfil()

        binding.switchDisponible.setOnCheckedChangeListener { _, isChecked ->
            val uid = auth.currentUser?.uid ?: return@setOnCheckedChangeListener
            db.collection("joueurs").document(uid)
                .update("disponible", isChecked)
                .addOnSuccessListener {
                    val statut = if (isChecked) "Disponible" else "Non disponible"
                    Toast.makeText(requireContext(), "Statut : $statut", Toast.LENGTH_SHORT).show()
                }
        }

        binding.btnSauvegarder.setOnClickListener { sauvegarderProfil() }
    }

    private fun chargerProfil() {
        val uid = auth.currentUser?.uid ?: return
        binding.progressBar.visibility = View.VISIBLE

        db.collection("joueurs").document(uid).get()
            .addOnSuccessListener { doc ->
                binding.progressBar.visibility = View.GONE
                val joueur = doc.toObject(Player::class.java) ?: return@addOnSuccessListener

                binding.tvNomComplet.text = "${joueur.prenom} ${joueur.nom}"
                binding.tvEmail.text = joueur.email
                binding.etTelephone.setText(joueur.telephone)
                binding.etQuartier.setText(joueur.quartier)
                binding.switchDisponible.isChecked = joueur.disponible
                binding.tvMatchsJoues.text = "Matchs joués : ${joueur.matchsJoues}"

                val niveauIndex = listOf("Débutant", "Intermédiaire", "Avancé").indexOf(joueur.niveau)
                binding.rgNiveau.check(
                    when (niveauIndex) {
                        0 -> binding.rbDebutant.id
                        1 -> binding.rbIntermediaire.id
                        2 -> binding.rbAvance.id
                        else -> binding.rbDebutant.id
                    }
                )

                // Badge couleur selon niveau
                val couleur = when (joueur.niveau) {
                    "Avancé" -> android.graphics.Color.parseColor("#FF6B35")
                    "Intermédiaire" -> android.graphics.Color.parseColor("#F7C59F")
                    else -> android.graphics.Color.parseColor("#4CAF50")
                }
                binding.tvNiveau.setBackgroundColor(couleur)
                binding.tvNiveau.text = joueur.niveau
            }
            .addOnFailureListener {
                binding.progressBar.visibility = View.GONE
            }
    }

    private fun sauvegarderProfil() {
        val uid = auth.currentUser?.uid ?: return
        val niveau = when (binding.rgNiveau.checkedRadioButtonId) {
            binding.rbDebutant.id -> "Débutant"
            binding.rbIntermediaire.id -> "Intermédiaire"
            binding.rbAvance.id -> "Avancé"
            else -> "Débutant"
        }

        val mises_a_jour = hashMapOf<String, Any>(
            "telephone" to binding.etTelephone.text.toString(),
            "quartier" to binding.etQuartier.text.toString(),
            "niveau" to niveau
        )

        db.collection("joueurs").document(uid).update(mises_a_jour)
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "Profil mis à jour !", Toast.LENGTH_SHORT).show()
                chargerProfil()
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Erreur lors de la mise à jour", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

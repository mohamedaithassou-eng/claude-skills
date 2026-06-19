package com.padeldarkar.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.padeldarkar.databinding.ItemPlayerBinding
import com.padeldarkar.models.Player

class PlayerAdapter(
    private var joueurs: List<Player>,
    private val onDemanderMatch: (Player) -> Unit
) : RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder>() {

    inner class PlayerViewHolder(val binding: ItemPlayerBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val binding = ItemPlayerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlayerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        val joueur = joueurs[position]
        with(holder.binding) {
            tvNomJoueur.text = "${joueur.prenom} ${joueur.nom}"
            tvQuartier.text = joueur.quartier.ifEmpty { "Dakar" }
            tvMatchsJoues.text = "${joueur.matchsJoues} matchs"

            val (texteNiveau, couleur) = when (joueur.niveau) {
                "Avancé" -> Pair("Avancé", android.graphics.Color.parseColor("#FF6B35"))
                "Intermédiaire" -> Pair("Intermédiaire", android.graphics.Color.parseColor("#2196F3"))
                else -> Pair("Débutant", android.graphics.Color.parseColor("#4CAF50"))
            }
            tvNiveau.text = texteNiveau
            tvNiveau.setBackgroundColor(couleur)

            btnDemanderMatch.setOnClickListener { onDemanderMatch(joueur) }
        }
    }

    override fun getItemCount() = joueurs.size

    fun updateList(nouveauxJoueurs: List<Player>) {
        joueurs = nouveauxJoueurs
        notifyDataSetChanged()
    }
}

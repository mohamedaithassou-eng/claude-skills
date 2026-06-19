package com.padeldarkar.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.padeldarkar.databinding.ItemCourtBinding
import com.padeldarkar.models.Court

class CourtAdapter(private var terrains: List<Court>) :
    RecyclerView.Adapter<CourtAdapter.CourtViewHolder>() {

    inner class CourtViewHolder(val binding: ItemCourtBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourtViewHolder {
        val binding = ItemCourtBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CourtViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CourtViewHolder, position: Int) {
        val terrain = terrains[position]
        with(holder.binding) {
            tvNomTerrain.text = terrain.nom
            tvAdresse.text = "${terrain.adresse}, ${terrain.quartier}"
            tvPrix.text = "${terrain.prixParHeure} FCFA/h"
            tvNbTerrains.text = "${terrain.nbTerrains} terrain(s)"
            tvHoraires.text = "${terrain.heureOuverture} - ${terrain.heureFermeture}"
            tvTelephone.text = terrain.telephone

            val statut = if (terrain.disponible) "Ouvert" else "Fermé"
            val couleur = if (terrain.disponible)
                android.graphics.Color.parseColor("#4CAF50")
            else
                android.graphics.Color.parseColor("#F44336")
            tvStatut.text = statut
            tvStatut.setTextColor(couleur)
        }
    }

    override fun getItemCount() = terrains.size

    fun updateList(nouveauxTerrains: List<Court>) {
        terrains = nouveauxTerrains
        notifyDataSetChanged()
    }
}

package com.padeldarkar.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "terrains")
data class Court(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nom: String = "",
    val adresse: String = "",
    val quartier: String = "",
    val telephone: String = "",
    val prixParHeure: Int = 0,
    val nbTerrains: Int = 1,
    val heureOuverture: String = "08:00",
    val heureFermeture: String = "22:00",
    val disponible: Boolean = true
)

package com.padeldarkar.models

data class Court(
    val id: String = "",
    val nom: String = "",
    val adresse: String = "",
    val quartier: String = "",
    val telephone: String = "",
    val prixParHeure: Int = 0,         // en FCFA
    val nbTerrains: Int = 1,
    val heureOuverture: String = "08:00",
    val heureFermeture: String = "22:00",
    val disponible: Boolean = true,
    val latitude: Double = 14.7167,    // coordonnées Dakar par défaut
    val longitude: Double = -17.4677
)

package com.padeldarkar.models

data class Player(
    val uid: String = "",
    val nom: String = "",
    val prenom: String = "",
    val email: String = "",
    val telephone: String = "",
    val niveau: String = "Débutant",   // Débutant, Intermédiaire, Avancé
    val quartier: String = "",
    val photoUrl: String = "",
    val disponible: Boolean = true,
    val matchsJoues: Int = 0
)

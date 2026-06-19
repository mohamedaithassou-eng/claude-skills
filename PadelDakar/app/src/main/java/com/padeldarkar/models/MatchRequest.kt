package com.padeldarkar.models

data class MatchRequest(
    val id: String = "",
    val demandeurUid: String = "",
    val demandeurNom: String = "",
    val destinataireUid: String = "",
    val niveau: String = "",
    val terrain: String = "",
    val dateProposee: String = "",
    val heureProposee: String = "",
    val statut: String = "en_attente",  // en_attente, accepte, refuse
    val timestamp: Long = System.currentTimeMillis()
)

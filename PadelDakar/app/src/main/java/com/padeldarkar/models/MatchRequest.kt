package com.padeldarkar.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "demandes_match")
data class MatchRequest(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val demandeurId: Int = 0,
    val demandeurNom: String = "",
    val destinataireId: Int = 0,
    val niveau: String = "",
    val statut: String = "en_attente",
    val timestamp: Long = System.currentTimeMillis()
)

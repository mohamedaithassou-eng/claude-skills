package com.padeldarkar.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "joueurs")
data class Player(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    val nom: String = "",
    val prenom: String = "",
    val email: String = "",
    val telephone: String = "",
    val motDePasse: String = "",
    val niveau: String = "Débutant",
    val quartier: String = "",
    val disponible: Boolean = true,
    val matchsJoues: Int = 0
)

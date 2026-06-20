package com.padeldarkar.database

import androidx.room.*
import com.padeldarkar.models.Player

@Dao
interface PlayerDao {

    @Insert
    suspend fun inserer(joueur: Player): Long

    @Update
    suspend fun mettrAJour(joueur: Player)

    @Query("SELECT * FROM joueurs WHERE email = :email AND motDePasse = :motDePasse LIMIT 1")
    suspend fun connecter(email: String, motDePasse: String): Player?

    @Query("SELECT * FROM joueurs WHERE email = :email LIMIT 1")
    suspend fun trouverParEmail(email: String): Player?

    @Query("SELECT * FROM joueurs WHERE uid = :uid LIMIT 1")
    suspend fun trouverParId(uid: Int): Player?

    @Query("SELECT * FROM joueurs WHERE disponible = 1 AND uid != :monId")
    suspend fun joueursDipsonibles(monId: Int): List<Player>

    @Query("SELECT * FROM joueurs WHERE disponible = 1 AND niveau = :niveau AND uid != :monId")
    suspend fun joueursParNiveau(niveau: String, monId: Int): List<Player>

    @Query("UPDATE joueurs SET disponible = :disponible WHERE uid = :uid")
    suspend fun majDisponibilite(uid: Int, disponible: Boolean)
}

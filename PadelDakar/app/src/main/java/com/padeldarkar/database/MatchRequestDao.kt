package com.padeldarkar.database

import androidx.room.*
import com.padeldarkar.models.MatchRequest

@Dao
interface MatchRequestDao {

    @Insert
    suspend fun inserer(demande: MatchRequest)

    @Query("SELECT * FROM demandes_match WHERE destinataireId = :uid ORDER BY timestamp DESC")
    suspend fun demandesRecues(uid: Int): List<MatchRequest>

    @Query("UPDATE demandes_match SET statut = :statut WHERE id = :id")
    suspend fun majStatut(id: Int, statut: String)
}

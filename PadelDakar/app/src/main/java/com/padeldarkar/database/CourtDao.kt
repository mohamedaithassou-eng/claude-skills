package com.padeldarkar.database

import androidx.room.*
import com.padeldarkar.models.Court

@Dao
interface CourtDao {

    @Insert
    suspend fun inserer(terrain: Court)

    @Insert
    suspend fun insererTous(terrains: List<Court>)

    @Query("SELECT * FROM terrains ORDER BY nom ASC")
    suspend fun tousLesTerrains(): List<Court>

    @Query("SELECT COUNT(*) FROM terrains")
    suspend fun compter(): Int
}

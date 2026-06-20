package com.padeldarkar.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.padeldarkar.models.Court
import com.padeldarkar.models.MatchRequest
import com.padeldarkar.models.Player
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Player::class, Court::class, MatchRequest::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun playerDao(): PlayerDao
    abstract fun courtDao(): CourtDao
    abstract fun matchRequestDao(): MatchRequestDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(context, AppDatabase::class.java, "padel_dakar.db")
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            // Pré-remplir les terrains au premier lancement
                            CoroutineScope(Dispatchers.IO).launch {
                                getInstance(context).courtDao().insererTous(terrainsDeDemo())
                            }
                        }
                    })
                    .build()
                    .also { INSTANCE = it }
            }
        }

        private fun terrainsDeDemo() = listOf(
            Court(nom = "Padel Club Dakar", adresse = "Route de Ouakam", quartier = "Ouakam",
                telephone = "+221 77 000 0001", prixParHeure = 15000, nbTerrains = 3),
            Court(nom = "Almadies Padel", adresse = "Les Almadies", quartier = "Almadies",
                telephone = "+221 77 000 0002", prixParHeure = 12000, nbTerrains = 2),
            Court(nom = "Mermoz Padel Center", adresse = "Avenue Cheikh Anta Diop", quartier = "Mermoz",
                telephone = "+221 77 000 0003", prixParHeure = 10000, nbTerrains = 4),
            Court(nom = "Point E Tennis & Padel", adresse = "Rue 10, Point E", quartier = "Plateau",
                telephone = "+221 77 000 0004", prixParHeure = 14000, nbTerrains = 2),
            Court(nom = "Ngor Padel", adresse = "Village de Ngor", quartier = "Ngor",
                telephone = "+221 77 000 0005", prixParHeure = 11000, nbTerrains = 1),
            Court(nom = "Sacré-Cœur Padel", adresse = "Sacré-Cœur 3", quartier = "Sacré-Cœur",
                telephone = "+221 77 000 0006", prixParHeure = 13000, nbTerrains = 2)
        )
    }
}

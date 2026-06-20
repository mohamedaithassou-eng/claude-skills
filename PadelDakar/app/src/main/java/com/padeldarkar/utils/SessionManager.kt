package com.padeldarkar.utils

import android.content.Context

class SessionManager(context: Context) {

    private val prefs = context.getSharedPreferences("padel_session", Context.MODE_PRIVATE)

    fun connecter(uid: Int, nom: String, prenom: String) {
        prefs.edit()
            .putInt("uid", uid)
            .putString("nom", nom)
            .putString("prenom", prenom)
            .putBoolean("connecte", true)
            .apply()
    }

    fun deconnecter() {
        prefs.edit().clear().apply()
    }

    fun estConnecte(): Boolean = prefs.getBoolean("connecte", false)

    fun getUid(): Int = prefs.getInt("uid", -1)

    fun getNomComplet(): String {
        val prenom = prefs.getString("prenom", "") ?: ""
        val nom = prefs.getString("nom", "") ?: ""
        return "$prenom $nom".trim()
    }
}

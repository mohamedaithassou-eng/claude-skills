package com.padeldarkar.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.padeldarkar.database.AppDatabase
import com.padeldarkar.databinding.ActivityRegisterBinding
import com.padeldarkar.home.HomeActivity
import com.padeldarkar.models.Player
import com.padeldarkar.utils.SessionManager
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var session: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        session = SessionManager(this)

        binding.btnInscription.setOnClickListener {
            val nom = binding.etNom.text.toString().trim()
            val prenom = binding.etPrenom.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val telephone = binding.etTelephone.text.toString().trim()
            val quartier = binding.etQuartier.text.toString().trim()
            val motDePasse = binding.etMotDePasse.text.toString()

            val niveau = when (binding.rgNiveau.checkedRadioButtonId) {
                binding.rbDebutant.id -> "Débutant"
                binding.rbIntermediaire.id -> "Intermédiaire"
                binding.rbAvance.id -> "Avancé"
                else -> "Débutant"
            }

            if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || motDePasse.isEmpty()) {
                Toast.makeText(this, "Veuillez remplir les champs obligatoires (*)", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (motDePasse.length < 4) {
                Toast.makeText(this, "Le mot de passe doit contenir au moins 4 caractères", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            binding.btnInscription.isEnabled = false

            lifecycleScope.launch {
                val db = AppDatabase.getInstance(applicationContext)

                val existant = db.playerDao().trouverParEmail(email)
                if (existant != null) {
                    runOnUiThread {
                        binding.btnInscription.isEnabled = true
                        Toast.makeText(this@RegisterActivity, "Cet email est déjà utilisé", Toast.LENGTH_SHORT).show()
                    }
                    return@launch
                }

                val joueur = Player(
                    nom = nom, prenom = prenom, email = email,
                    telephone = telephone, quartier = quartier,
                    motDePasse = motDePasse, niveau = niveau
                )
                val uid = db.playerDao().inserer(joueur).toInt()

                runOnUiThread {
                    session.connecter(uid, nom, prenom)
                    startActivity(Intent(this@RegisterActivity, HomeActivity::class.java))
                    finish()
                }
            }
        }

        binding.tvConnexion.setOnClickListener { finish() }
    }
}

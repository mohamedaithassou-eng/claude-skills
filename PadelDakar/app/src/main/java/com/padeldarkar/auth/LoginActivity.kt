package com.padeldarkar.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.padeldarkar.database.AppDatabase
import com.padeldarkar.databinding.ActivityLoginBinding
import com.padeldarkar.home.HomeActivity
import com.padeldarkar.utils.SessionManager
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var session: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        session = SessionManager(this)

        if (session.estConnecte()) {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
            return
        }

        binding.btnConnexion.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val motDePasse = binding.etMotDePasse.text.toString()

            if (email.isEmpty() || motDePasse.isEmpty()) {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            binding.btnConnexion.isEnabled = false

            lifecycleScope.launch {
                val db = AppDatabase.getInstance(applicationContext)
                val joueur = db.playerDao().connecter(email, motDePasse)

                runOnUiThread {
                    binding.btnConnexion.isEnabled = true
                    if (joueur != null) {
                        session.connecter(joueur.uid, joueur.nom, joueur.prenom)
                        startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this@LoginActivity, "Email ou mot de passe incorrect", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        binding.tvInscription.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}

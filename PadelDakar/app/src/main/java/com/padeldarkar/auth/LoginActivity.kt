package com.padeldarkar.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.padeldarkar.databinding.ActivityLoginBinding
import com.padeldarkar.home.HomeActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        // Si déjà connecté, aller directement à l'accueil
        if (auth.currentUser != null) {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }

        binding.btnConnexion.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val motDePasse = binding.etMotDePasse.text.toString()

            if (email.isEmpty() || motDePasse.isEmpty()) {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            binding.btnConnexion.isEnabled = false
            auth.signInWithEmailAndPassword(email, motDePasse)
                .addOnSuccessListener {
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                }
                .addOnFailureListener {
                    binding.btnConnexion.isEnabled = true
                    Toast.makeText(this, "Email ou mot de passe incorrect", Toast.LENGTH_SHORT).show()
                }
        }

        binding.tvInscription.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}

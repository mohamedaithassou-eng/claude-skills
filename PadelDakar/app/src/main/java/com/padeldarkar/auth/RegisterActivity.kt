package com.padeldarkar.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.padeldarkar.databinding.ActivityRegisterBinding
import com.padeldarkar.home.HomeActivity
import com.padeldarkar.models.Player

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

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
                Toast.makeText(this, "Veuillez remplir tous les champs obligatoires", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (motDePasse.length < 6) {
                Toast.makeText(this, "Le mot de passe doit contenir au moins 6 caractères", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            binding.btnInscription.isEnabled = false

            auth.createUserWithEmailAndPassword(email, motDePasse)
                .addOnSuccessListener { result ->
                    val uid = result.user!!.uid
                    val joueur = Player(
                        uid = uid,
                        nom = nom,
                        prenom = prenom,
                        email = email,
                        telephone = telephone,
                        quartier = quartier,
                        niveau = niveau
                    )

                    db.collection("joueurs").document(uid).set(joueur)
                        .addOnSuccessListener {
                            startActivity(Intent(this, HomeActivity::class.java))
                            finish()
                        }
                        .addOnFailureListener {
                            binding.btnInscription.isEnabled = true
                            Toast.makeText(this, "Erreur lors de la sauvegarde du profil", Toast.LENGTH_SHORT).show()
                        }
                }
                .addOnFailureListener {
                    binding.btnInscription.isEnabled = true
                    Toast.makeText(this, "Erreur : ${it.message}", Toast.LENGTH_SHORT).show()
                }
        }

        binding.tvConnexion.setOnClickListener { finish() }
    }
}

# PadelDakar 🎾

Application Android de mise en relation des joueurs de padel à Dakar.

## Fonctionnalités (version gratuite)

- Inscription et connexion des joueurs
- Profil avec niveau de jeu (Débutant / Intermédiaire / Avancé)
- Liste des terrains de padel à Dakar
- Recherche de joueurs filtrée par niveau
- Envoi de demandes de match
- Statut de disponibilité

## Technologies utilisées

- **Kotlin** — langage principal
- **Firebase Authentication** — connexion des utilisateurs
- **Firebase Firestore** — base de données en temps réel
- **Jetpack Navigation** — navigation entre les écrans
- **Material Design** — interface moderne

## Structure du projet

```
app/src/main/java/com/padeldarkar/
├── auth/           → Connexion et inscription
├── home/           → Activité principale avec navigation
├── courts/         → Liste des terrains
├── players/        → Liste des joueurs + profil
├── adapters/       → Adaptateurs RecyclerView
└── models/         → Modèles de données
```

## Installation

### Étape 1 — Prérequis
- Installer [Android Studio](https://developer.android.com/studio)
- Créer un compte [Firebase](https://firebase.google.com)

### Étape 2 — Firebase
1. Aller sur [console.firebase.google.com](https://console.firebase.google.com)
2. Créer un nouveau projet nommé **PadelDakar**
3. Ajouter une application Android avec l'ID : `com.padeldarkar`
4. Télécharger le fichier `google-services.json`
5. Placer ce fichier dans le dossier `app/`
6. Activer **Authentication** (Email/Mot de passe) dans Firebase
7. Activer **Firestore Database** en mode test

### Étape 3 — Lancer l'application
1. Ouvrir le dossier `PadelDakar` dans Android Studio
2. Attendre la synchronisation Gradle
3. Connecter votre téléphone Android (ou lancer un émulateur)
4. Cliquer sur **Run** ▶️

## Prochaines fonctionnalités (version premium)

- Chat entre joueurs
- Réservation de terrains en ligne
- Classement et statistiques avancées
- Notifications push pour les demandes de match
- Calendrier des matchs

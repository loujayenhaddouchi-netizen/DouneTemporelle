package com.example.douanetemporelle;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class VerificationActivity extends AppCompatActivity {

    // Quatre derniers chiffres du matricule étudiant utilisé heya mtaa carte mtaa loujayen haddouchi 3bi a03.
    private static final String MATRICULE_MEMBRE = "9491";

    // Déclaration des composants graphiques mtaa l'activité
    TextView tvNom, tvAge, tvMotif;
    Button btnApprouver, btnRefuser;

    // Variables fihom les informations mtaa voyageur
    String nom;
    String motif;
    int ageTemporel;

    //* Méthode statique permettant de construire les clés Intent
     //Elle reçoit le matricule en paramètre et récupère ses quatre
     // derniers chiffres  bch tasna3 une clé personnalisée

    public static String creerCleExtra(String matricule, String nomChamp) {

        if (matricule == null || matricule.length() < 4) {
            throw new IllegalArgumentException(
                    "Le matricule doit contenir au moins quatre chiffres."
            );
        }

        String quatreChiffres =
                matricule.substring(matricule.length() - 4);

        return quatreChiffres + "_" + nomChamp;
    }


       //Méthode tvérifier si un nombre est premier

    private boolean estPremier(int nombre) {

        if (nombre < 2) {
            return false;
        }

        for (int i = 2; i <= Math.sqrt(nombre); i++) {

            if (nombre % i == 0) {
                return false;
            }
        }

        return true;
    }

    /*
     * Méthode principale exécutée automatiquement lors de
     * l'ouverture de l'activité de vérification.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Chargement de l'interface graphique définie dans le fichier XML
        setContentView(R.layout.activity_verification);

        // Association mta composants Java maa identifiants XML


        tvNom = findViewById(R.id.txtNom_lh);
        tvAge = findViewById(R.id.txtAge_lh);
        tvMotif = findViewById(R.id.txtMotif_lh);

        btnApprouver = findViewById(R.id.btnApprouver_lh);
        btnRefuser = findViewById(R.id.btnRefuser_lh);
        // Récupération mtaa l'Intent envoyé par MainActivity
        Intent intent = getIntent();

        // Construction des clés personnalisées ml matricule.
        String cleNom =
                creerCleExtra(MATRICULE_MEMBRE, "NOM_VOYAGEUR");

        String cleMotif =
                creerCleExtra(MATRICULE_MEMBRE, "MOTIF_VOYAGE");

        String cleNaissance =
                creerCleExtra(MATRICULE_MEMBRE, "ANNEE_NAISSANCE");

        String cleDestination =
                creerCleExtra(MATRICULE_MEMBRE, "ANNEE_DESTINATION");

        // Récupération mta les informations transmises par l'activité principale
        nom = intent.getStringExtra(cleNom);
        motif = intent.getStringExtra(cleMotif);

        int anneeNaissance = intent.getIntExtra(cleNaissance, 0);
        int anneeDestination = intent.getIntExtra(cleDestination, 0);

        // Calcul de la différence entre l'année de destination et l'année de naissance du voyageur
        ageTemporel = anneeDestination - anneeNaissance;
        // Affichage des informations du voyageur dans les TextView.
        tvNom.setText("Voyageur : " + nom);
        tvAge.setText("Âge temporel : " + ageTemporel + " ans");
        tvMotif.setText("Motif : " + motif);

        /*
         * Vérification de la condition particulière imposée
         * idha ken le motif est "Modification du passé" widha la différenc entre les deux années est un nombre premier,
         * le bouton Approuver lezem désactivé dès le chargement.
         */
        if ("Modification du passé".equalsIgnoreCase(motif)
                && estPremier(ageTemporel)) {

            btnApprouver.setEnabled(false);

            Toast.makeText(
                    this,
                    "Approbation impossible : différence première.",
                    Toast.LENGTH_LONG
            ).show();
        }

        // Gestion du clic sur le bouton Approuver
        // La valeur true tmathel un verdict positif.
        btnApprouver.setOnClickListener(v -> {
            envoyerVerdict(true);
        });

        // Gestion du clic sur le bouton Refuser
        // La valeur false tmathel un verdict négatif.
        btnRefuser.setOnClickListener(v -> {
            envoyerVerdict(false);
        });
    }

    /*
     * Méthode permettant de transmettre le verdict w le nom du voyageurvà ResultActivity à l'aide d'un Intent.
     */
    private void envoyerVerdict(boolean verdict) {

        // Création d'un Intent bch  thel l'activité des résultats
        Intent intent = new Intent(
                VerificationActivity.this,
                ResultActivity.class
        );

        // Construction des clés personnalisées pour le résultat
        String cleVerdict =
                creerCleExtra(MATRICULE_MEMBRE, "VERDICT");

        String cleNom =
                creerCleExtra(MATRICULE_MEMBRE, "NOM_VOYAGEUR");

        // Transmission du verdict et du nom du voyageur
        intent.putExtra(cleVerdict, verdict);
        intent.putExtra(cleNom, nom);

        // Ouverture de l'activité ili taffichi le résultat du contrôle
        startActivity(intent);
    }
}
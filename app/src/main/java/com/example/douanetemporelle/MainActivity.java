package com.example.douanetemporelle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    // Matricule utilisé pour personnaliser les clés de transmission
    private static final String MATRICULE_MEMBRE = "9491";

    // Déclaration mtaa composants graphiques
    private EditText edtNom;
    private EditText edtNaissance;
    private EditText edtDestination;
    private RadioGroup rgMotif;
    private Button btnInspecter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialisation mtaa l'écran principal
        setContentView(R.layout.activity_main);

        // Gestion des marges de l'écran
        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.main), (v, insets) -> {
                    Insets systemBars = insets.getInsets(
                            WindowInsetsCompat.Type.systemBars()
                    );

                    v.setPadding(
                            systemBars.left,
                            systemBars.top,
                            systemBars.right,
                            systemBars.bottom
                    );

                    return insets;
                }
        );

        // Récupération des composants depuis le fichier XML
        edtNom = findViewById(R.id.edt_nom_lh);
        edtNaissance = findViewById(R.id.edt_naissance_lh);
        edtDestination = findViewById(R.id.edt_destination_lh);
        rgMotif = findViewById(R.id.rg_motif_lh);
        btnInspecter = findViewById(R.id.btn_inspecter_lh);

        // Détection du clic sur le bouton Inspecter le profil
        btnInspecter.setOnClickListener(v -> {

            // Récupération des informations saisies
            String nom = edtNom.getText().toString().trim();
            String naissanceText = edtNaissance.getText().toString().trim();
            String destinationText = edtDestination.getText().toString().trim();

            // Vérification des champs obligatoires
            if (nom.isEmpty() || naissanceText.isEmpty()
                    || destinationText.isEmpty()) {

                Toast.makeText(
                        MainActivity.this,
                        "Veuillez remplir tous les champs.",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            // Vérification du motif sélectionné
            int idMotif = rgMotif.getCheckedRadioButtonId();

            if (idMotif == -1) {
                Toast.makeText(
                        MainActivity.this,
                        "Veuillez choisir un motif de voyage.",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            // Récupération du texte du RadioButton sélectionné
            RadioButton radioSelectionne = findViewById(idMotif);
            String motif = radioSelectionne.getText().toString();

            // Conversion des années en entiers
            int anneeNaissance;
            int anneeDestination;

            try {
                anneeNaissance = Integer.parseInt(naissanceText);
                anneeDestination = Integer.parseInt(destinationText);

            } catch (NumberFormatException e) {

                Toast.makeText(
                        MainActivity.this,
                        "Veuillez saisir des années valides.",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            // Vérification de la cohérence des années
            if (anneeNaissance <= 0 || anneeDestination <= anneeNaissance) {

                Toast.makeText(
                        MainActivity.this,
                        "L'année de destination doit être supérieure à l'année de naissance.",
                        Toast.LENGTH_LONG
                ).show();

                return;
            }

            // Création de l'Intent pour ouvrir l'écran de vérification
            Intent intent = new Intent(
                    MainActivity.this,
                    VerificationActivity.class
            );

            // Création des clés personnaliséesc bl matricule
            String cleNom = creerCleExtra(MATRICULE_MEMBRE, "NOM_VOYAGEUR");
            String cleMotif = creerCleExtra(MATRICULE_MEMBRE, "MOTIF_VOYAGE");
            String cleNaissance = creerCleExtra(MATRICULE_MEMBRE, "ANNEE_NAISSANCE");
            String cleDestination = creerCleExtra(MATRICULE_MEMBRE, "ANNEE_DESTINATION");

            // Transmission des informations lel VerificationActivity
            intent.putExtra(cleNom, nom);
            intent.putExtra(cleMotif, motif);
            intent.putExtra(cleNaissance, anneeNaissance);
            intent.putExtra(cleDestination, anneeDestination);

            // Ouverture mtaa l'écran de vérification
            startActivity(intent);
        });
    }

    // Méthode statique lel construire mtaa clé personnalisée
    public static String creerCleExtra(String matricule, String nomChamp) {
        return matricule + "_" + nomChamp;
    }
}
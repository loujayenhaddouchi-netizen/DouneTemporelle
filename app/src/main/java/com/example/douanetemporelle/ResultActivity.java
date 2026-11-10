package com.example.douanetemporelle;

import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // 1. Récupération mtaa l composants
        LinearLayout layout = findViewById(R.id.layoutResult);
        TextView txtResultat = findViewById(R.id.txtResultat);
        Button btnNouveau = findViewById(R.id.btnNouveau);

        // 2. Réception des données transmises par VerificationActivity
        Intent intent = getIntent();

// Construction des mêmes clés personnalisées avec matricule  mtei
        String cleVerdict = VerificationActivity.creerCleExtra("9491", "VERDICT");
        String cleNom = VerificationActivity.creerCleExtra("9491", "NOM_VOYAGEUR");

// Récupération du verdict et du nom à partir des clés personnalisées
        boolean verdict = intent.getBooleanExtra(cleVerdict, false);
        String nom = intent.getStringExtra(cleNom);

        // 3. Affichage du verdict
        if (verdict) {
            layout.setBackgroundColor(0xFF4CAF50);
            txtResultat.setText("Bon voyage " + nom + " !");
        } else {
            layout.setBackgroundColor(0xFFD94343);
            txtResultat.setText(nom + " est en état d'arrestation temporelle !");
        }

        // 4. Bouton nouveau voyageur
        btnNouveau.setOnClickListener(v -> {
            Intent retour = new Intent(ResultActivity.this, MainActivity.class);
            retour.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(retour);
            finish();
        });
    }
}
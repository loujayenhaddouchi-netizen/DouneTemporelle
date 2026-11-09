package com.example.douanetemporelle;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
//declaration mtaa classe principale
public class MainActivity extends AppCompatActivity {
//methode mtaa l'execution
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //appel lel methode de la calsse mére bch ninisialisiw l'activité
        super.onCreate(savedInstanceState);
        WindowCompat.enableEdgeToEdge(getWindow());
        //bch norbet bin l'activité wel interface graphique
        setContentView(R.layout.activity_main);
        //gestion des marges
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
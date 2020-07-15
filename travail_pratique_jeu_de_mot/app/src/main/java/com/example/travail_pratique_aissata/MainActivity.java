package com.example.travail_pratique_aissata;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    Button btnJouer, btnRegle, btnScore;
    Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        ctx = this;

        btnJouer = findViewById(R.id.btnJouer);
        btnRegle = findViewById(R.id.btnRegle);
        btnScore = findViewById(R.id.btnScore);


        btnJouer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animationBouton(btnJouer);
                openJouerActivity();
            }
        });


        btnRegle.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animationBouton(btnRegle);
                AlertDialogRegleJeu.alertDialog(ctx);
            }
        }));


        btnScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animationBouton(btnScore);
            }
        });
//        openJouerActivity();
    }

    public void openJouerActivity(){
        Intent intent = new Intent(this, BoutonJouerActivity.class);
        startActivity(intent);
    }

    ///Methode permettant de faire une petite animation sur les boutons
    public static void animationBouton(Button btn) {
        AlphaAnimation alpha = new AlphaAnimation(0f, 1f);
        alpha.setDuration(500);
        btn.startAnimation(alpha);
    }


}

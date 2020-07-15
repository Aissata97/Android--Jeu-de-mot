package com.example.travail_pratique_aissata;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class AlertDialogRegleJeu extends AppCompatActivity{
    public static Button btnQuitter, btnCommencer;


    public static void alertDialog(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View layoutFromMonXml = inflater.inflate(R.layout.btn_regle_jeu, null);
        builder.setView(layoutFromMonXml);
        AlertDialog dialog = builder.create();
        dialog.show();

        btnCommencer = dialog.findViewById(R.id.btnCommencer);
        btnQuitter = dialog.findViewById(R.id.btnQuitter);

        btnQuitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.animationBouton(btnQuitter);
               // Intent intent = new Intent(AlertDialogRegleJeu.this, MainActivity.class);

            }
        });

        btnCommencer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.animationBouton(btnCommencer);
            }
        });

    }



}

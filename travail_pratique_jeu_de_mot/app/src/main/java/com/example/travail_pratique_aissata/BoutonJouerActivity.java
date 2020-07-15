package com.example.travail_pratique_aissata;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class BoutonJouerActivity extends AppCompatActivity {

    private static final int SECONDES_PAR_PARTIE = 60;

    Random random = new Random();
    CompteARebour compteARebour;
    TextView nombreDeRrussit;
    Button btnPasser;
    Button btnGo;
    TextView motPrefix;
    TextView motDevine;
    TextView chronometre;
    ProgressBar barreDeProgression;

    MapDesMots mapDesMots;
    ArrayList<String> prefixes;
    ArrayList<String> listeMotTrouve = new ArrayList<>();
    String prefixCurrent;
    int nombreMotsPasser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bouton_jouer);

        nombreDeRrussit = (TextView)findViewById(R.id.nombreDeRrussit);
        btnPasser = (Button)findViewById(R.id.btnPasser);
        btnGo = (Button)findViewById(R.id.btnGo);
        motPrefix = (TextView)findViewById(R.id.motPrefix);
        motDevine = (TextView)findViewById(R.id.motDevine);
        chronometre = (TextView)findViewById(R.id.chronometre);
        barreDeProgression = (ProgressBar)findViewById(R.id.barreDeProgression);

        btnPasser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animationBouton(btnPasser);
                passerClick();
            }
        });
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animationBouton(btnGo);
                goClick();
            }
        });

        LinearLayout container = (LinearLayout)findViewById(R.id.controlContainer);
        Clavier.KeyPressHandler eventHandler = new Clavier.KeyPressHandler(){
            @Override
            public void on(char c){
                clavierClick(c);
            }
        };
        container.addView(Clavier.creer(this, eventHandler), 0);
        ArrayList<String> listeMots = getListeDeMots();
        mapDesMots = new MapDesMots(listeMots);
        prefixes = mapDesMots.getPrefixes();
        compteARebour = new CompteARebour(this) {
            @Override
            public void onChange(int seconds) {
                setTemps(seconds);
            }

            @Override
            public void onTimeout() {
                tempsFini();
            }
        };
        commenceLeJeu();
    }

    public void commenceLeJeu(){
        reset();
        compteARebour.start(SECONDES_PAR_PARTIE);
        motSuivant();
    }

    public void tempsFini(){
        openScoreActivity();
    }

    public void reset(){
        listeMotTrouve.clear();
        prefixCurrent = "";
        nombreMotsPasser = 0;
        setTemps(SECONDES_PAR_PARTIE);
    }

    public void clavierClick(char c){
        String str = motDevine.getText().toString();
        if(c == '-'){
            motDevine.setText("");
        }else if(c == '<'){
            if(str.length() > 0){
                motDevine.setText(str.substring(0, str.length() - 1));
            }
        }else{
            motDevine.setText(str + c);
        }
    }

    public void passerClick(){
        nombreMotsPasser++;
        motSuivant();
    }

    public void goClick(){
        String motComplet = prefixCurrent + motDevine.getText().toString();
        final int result = reussit(motComplet);
        if(result == 1){
            listeMotTrouve.add(motComplet);
            nombreDeRrussit.setText(listeMotTrouve.size() + "");
            motSuivant();
        }else if(result == -1){
            message("Mot déja utiliser!", null);
        }else{
            message("Mot incorrect!", null);
        }
    }

    public int reussit(String guess){
        ArrayList<String> mots = mapDesMots.getMotsParPrefix(prefixCurrent);
        boolean valide = false;
        for (String mot: mots) {
            if(mot.equals(guess)){
                valide = true;
                break;
            }
        }
        if(valide){
            for(String mot: listeMotTrouve){
                if(mot.equals(guess)){
                    return -1;
                }
            }
            return 1;
        }
        return 0;
    }

    public void motSuivant(){
        String nouvauxPrefix = "";
        do {
            nouvauxPrefix = prefixes.get(random.nextInt(prefixes.size()));
        }while (nouvauxPrefix.equals(prefixCurrent));

        setMotADevine(nouvauxPrefix);
    }

    public void setMotADevine(String prefix){
        prefixCurrent = prefix;
        motPrefix.setText(prefix);
        motDevine.setText("");
    }

    public void setTemps(int seconds){
        chronometre.setText(seconds + "");
        barreDeProgression.setProgress(Math.round(((float)seconds / SECONDES_PAR_PARTIE * 100f)));
    }

    public void openScoreActivity(){
        ScoreData scoreData = new ScoreData(listeMotTrouve, nombreMotsPasser);
        Intent intent = new Intent(this, ScoreActivity.class);
        intent.putExtra("scoreData", scoreData);
        startActivity(intent);
    }

    public void message(String msg, @Nullable final Callback callback){
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
        dlgAlert.setMessage(msg);
        dlgAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(callback != null) callback.call();
            }
        });
        dlgAlert.show();
    }

    public ArrayList<String> getListeDeMots(){
        ArrayList<String> listeMots = new ArrayList<String>();
        try{
            BufferedReader buffer = new BufferedReader(new InputStreamReader(getAssets().open("liste_francais.txt")));
            String line;

            while ((line = buffer.readLine()) != null){
               listeMots.add(line);
            }
           buffer.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        return listeMots;
    }

    public static void animationBouton(Button btn) {
        AlphaAnimation alpha = new AlphaAnimation(0.5f, 1f);
        alpha.setDuration(200);
        btn.startAnimation(alpha);
    }

    interface Callback{
        void call();
    }
}

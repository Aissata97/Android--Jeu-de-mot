package com.example.travail_pratique_aissata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class ScoreActivity extends AppCompatActivity {

    ScoreData scoreData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        scoreData = (ScoreData)getIntent().getSerializableExtra("scoreData");
        int score = calcScore();
        int nbTrouves = scoreData.motsTrouvez.size();
        int nbPasser = scoreData.nombreMotsPasser;
        int ratio = Math.round((float)nbTrouves / (nbTrouves + nbPasser) * 100);

        ((TextView)findViewById(R.id.score)).setText("Score: " + score);
        ((TextView)findViewById(R.id.nbMotsTrouves)).setText(nbTrouves + "");
        ((TextView)findViewById(R.id.nbMotsPasser)).setText(nbPasser + "");
        ((TextView)findViewById(R.id.touvesRatioText)).setText(ratio + "% de mots trouves");

        int barreWidth = Math.round(getScreenWidth() * ratio / 100);
        ((TextView)findViewById(R.id.touvesRatioBar)).getLayoutParams().width = barreWidth;

    }

    private int calcScore(){
        int score = 0;

        ArrayList<String> listeMotsTrouver = scoreData.motsTrouvez;
        int nbMotTrouve = listeMotsTrouver.size();
        for (int i = 0; i < nbMotTrouve; i++){
            //score += nbMotTrouve
            score += (listeMotsTrouver.get(i).length())*2;
        }
        return score;
    }

    int getScreenWidth(){
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }

    public void rejouerBtnClick(View v){
        Intent intent = new Intent(this, BoutonJouerActivity.class);
        startActivity(intent);
    }

    public void AccueilBtnClick(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}

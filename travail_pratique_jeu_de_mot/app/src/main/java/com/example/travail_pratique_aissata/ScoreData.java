package com.example.travail_pratique_aissata;

import java.io.Serializable;
import java.util.ArrayList;

public class ScoreData implements Serializable {

    public ArrayList<String> motsTrouvez;
    public int nombreMotsPasser;

    public ScoreData(ArrayList<String> _motsTrouvez, int _nombreMotsPasser){
        motsTrouvez = _motsTrouvez;
        nombreMotsPasser = _nombreMotsPasser;
    }

}

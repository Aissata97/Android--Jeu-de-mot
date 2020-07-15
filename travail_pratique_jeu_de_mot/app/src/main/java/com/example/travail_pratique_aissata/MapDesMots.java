package com.example.travail_pratique_aissata;

import java.util.ArrayList;
import java.util.HashMap;

public class MapDesMots {

    private HashMap<String, ArrayList<String>> data;
    private ArrayList<String> prefixes;

    public MapDesMots(ArrayList<String> mots){
        data = new HashMap<>();
        prefixes = new ArrayList<>();
        final int l = mots.size();
        for(int i = 0; i < l; i++){
            final String mot = mots.get(i).toUpperCase();
            final int prefixLen = mot.length() > 3 ? 3 : mot.length() - 1;
            final String prefix = mot.substring(0, prefixLen);
            ArrayList<String> liste = data.get(prefix);
            if(liste == null){
                liste = new ArrayList<>();
                data.put(prefix, liste);
                prefixes.add(prefix);
            }
            liste.add(mot);
        }
    }

    public ArrayList<String> getMotsParPrefix(String prefix){
        return data.get(prefix);
    }

    public ArrayList<String> getPrefixes() {
        return prefixes;
    }
}

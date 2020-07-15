package com.example.travail_pratique_aissata;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public abstract class CompteARebour {

    private int currentSeconds;
    private Timer timer;
    private Runnable runnable;
    private AppCompatActivity ctx;

    public CompteARebour(AppCompatActivity _ctx){
        ctx = _ctx;
        runnable = new Runnable() {
            @Override
            public void run() {
                currentSeconds--;
                onChange(currentSeconds);
                if(currentSeconds == 0){
                    timer.cancel();
                    onTimeout();
                }
            }
        };
    }

    public void start(int nombreDeScondes){
        currentSeconds = nombreDeScondes;
        timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                ctx.runOnUiThread(runnable);
            }
        };
        timer.scheduleAtFixedRate(timerTask, 1000, 1000);
    }

    public void stop(){
        timer.cancel();
    }

    protected abstract void onChange(int seconds);
    protected abstract void onTimeout();

}

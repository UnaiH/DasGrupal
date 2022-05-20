package com.example.trabajogrupal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.ColorRes;

public class ThemesWorker {
    public ThemesWorker(){

    }
    public void setThemes(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        if (prefs.contains("tema")) {
            String mod = prefs.getString("tema", "Default");
            Log.i("Temas", "setThemes: "+mod);
            if (mod.equals("Dark") || mod.equals("Iluna") || mod.equals("Oscuro")) {
                context.setTheme(R.style.Dark);
            }
            else if(mod.equals("Blue") || mod.equals("Urdina") || mod.equals("Azul")) {
                context.setTheme(R.style.Blue);
            }
            else if(mod.equals("Green") || mod.equals("Berdea") || mod.equals("Verde")) {
                context.setTheme(R.style.Green);
            }
            else {
                context.setTheme(R.style.Menu);
            }
        }
        else{
            context.setTheme(R.style.Menu);
        }
    }

    public void setButColorBlack(Button but, Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        if (prefs.contains("temas")) {
            String mod = prefs.getString("temas", "Default");
            if (mod.equals("Dark") || mod.equals("Iluna") || mod.equals("Oscuro")) {
                this.setDarkGrey(but);
            } else {
                this.setBlack(but);
            }
        }
        else{
            this.setBlack(but);
        }
    }
    public void setButColorWhite(Button but, Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        if (prefs.contains("temas")) {
            String mod = prefs.getString("temas", "Default");
            if (mod.equals("Dark") || mod.equals("Iluna") || mod.equals("Oscuro")) {
                this.setLigthGrey(but);
            } else {
                this.setWhite(but);
            }
        }
        else{
            this.setWhite(but);
        }
    }

    public void setVTextColorWhite(TextView text, Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        if (prefs.contains("temas")) {
            String mod = prefs.getString("temas", "Default");
            if (mod.equals("Dark") || mod.equals("Iluna") || mod.equals("Oscuro")) {
                this.setBlackTV(text);
            } else {
                this.setWhiteTV(text);
            }
        }
        else{
            this.setWhiteTV(text);
        }
    }

    @SuppressLint("ResourceAsColor")
    private void setWhiteTV(TextView text){
        text.setBackgroundColor(R.color.white);
        text.setTextColor(R.color.black);
    }
    @SuppressLint("ResourceAsColor")
    private void setBlackTV(TextView text){
        text.setBackgroundColor(R.color.black);
        text.setTextColor(R.color.white);
    }

    @SuppressLint("ResourceAsColor")
    private void setWhite(Button but){
        but.setBackgroundColor(R.color.white);
        but.setTextColor(R.color.black);
    }
    @SuppressLint("ResourceAsColor")
    private void setBlack(Button but){
        but.setBackgroundColor(R.color.black);
        but.setTextColor(R.color.white);
    }
    @SuppressLint("ResourceAsColor")
    private void setDarkGrey(Button but){
        but.setBackgroundColor(R.color.darkgrey);
        but.setTextColor(R.color.white);
    }
    @SuppressLint("ResourceAsColor")
    private void setLigthGrey(Button but){
        but.setBackgroundColor(R.color.lgrey);
        but.setTextColor(R.color.white);
    }
}

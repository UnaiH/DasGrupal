package com.example.trabajogrupal;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;

import java.util.Locale;

public class Languages {
    private String langua="en";
    public Languages(){

    }
    public void setLangua(Context context){
        String langu;
        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(context);
        if(prefs.contains("idiomas")){
            String lan = prefs.getString("idiomas", "English");
            if (lan.equals("Español")){
                langu = "es";
                this.changeLanguage(langu,context);
            }
            else if (lan.equals("Euskara")){
                langu = "eu";
                this.changeLanguage(langu,context);
            }
            else{
                langu = "en";
                this.changeLanguage(langu,context);
            }
        }
    }
    private void changeLanguage(String idioma,Context contexto){
        Locale local = new Locale(idioma);
        Locale.setDefault(local);
        Configuration config = contexto.getResources().getConfiguration();
        config.setLocale(local);
        config.setLayoutDirection(local);
        Context con = contexto.createConfigurationContext(config);
        contexto.getResources().updateConfiguration(config,con.getResources().getDisplayMetrics());
    }
}

package com.example.trabajogrupal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceFragment;

public class Preferencies extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferencias);
        Fragment fragmento = new preferencias();
        FragmentTransaction ftransc = getFragmentManager().beginTransaction();
        if (savedInstanceState==null){
            ftransc.add(R.id.layoutpref,fragmento,"fragset");
            ftransc.commit();
        }
        else{
            fragmento = getFragmentManager().findFragmentById(Integer.parseInt("fragset"));
        }
    }


    public static class preferencias extends PreferenceFragment {
        //This class is the Preference class.
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferencias);
        }
    }
    public void onBackPressed(){
        Intent i = new Intent(Preferencies.this, SelectMenuActivity.class);
        finish();
        startActivity(i);
    }
}
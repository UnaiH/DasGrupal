package com.example.trabajogrupal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceFragment;

public class PreferenciesActivity extends AppCompatActivity {

    private String user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new LanguagesWorker().setLangua(this);
        super.onCreate(savedInstanceState);

        ThemesWorker tem=new ThemesWorker();
        tem.setThemes(this);
        PlayerCatalogue catalogue = PlayerCatalogue.getMyPlayerCatalogue();
        setContentView(R.layout.activity_preferencias);
        user = catalogue.getCurrentUser();
        Fragment fragmento = new preferencias();
        FragmentTransaction ftransc = getFragmentManager().beginTransaction();
        ftransc.add(R.id.layoutpref,fragmento,"fragset");
        ftransc.commit();
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
        Intent i = new Intent(PreferenciesActivity.this, SelectMenuActivity.class);
        //i.putExtra("user", user);
        setResult(RESULT_OK, i);
        finish();
        startActivity(i);
    }
}
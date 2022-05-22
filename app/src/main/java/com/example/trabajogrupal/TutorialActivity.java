package com.example.trabajogrupal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Locale;

public class TutorialActivity extends AppCompatActivity {
    Button btnCheckers, btnChess;
    TextView txtTutorial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new LanguagesWorker().setLangua(this);
        super.onCreate(savedInstanceState);
        ThemesWorker tem = new ThemesWorker();
        tem.setThemes(this);
        setContentView(R.layout.activity_tutorial);
        btnCheckers = findViewById(R.id.btn_tutoCheckers);
        btnChess = findViewById(R.id.btn_tutoChess);
        txtTutorial = findViewById(R.id.tv_Tutorial);
        btnCheckers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tutorial = leerTutorial("Checkers");
                txtTutorial.setText(tutorial);
            }
        });
        btnChess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tutorial = leerTutorial("Chess");
                txtTutorial.setText(tutorial);
            }
        });
    }

    public String leerTutorial(String juego) {
        String tutorial = null;


        try {
            String lineaActual;
            InputStream fich = null;
            BufferedReader buff;
            switch (juego) {
                case "Checkers":
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
                    if (prefs.contains("idiomas")) {
                        String lan = prefs.getString("idiomas", "English");
                        if (lan.equals("Español")) {
                            fich = getResources().openRawResource(R.raw.instrucciones_damas);
                        } else if (lan.equals("Euskara")) {
                            fich = getResources().openRawResource(R.raw.arauak_damak);
                        } else {
                            fich = getResources().openRawResource(R.raw.instructions_checkers);
                        }
                    }
                    buff = new BufferedReader(new InputStreamReader(fich));
                    tutorial = "";
                    while ((lineaActual = buff.readLine()) != null) {
                        tutorial += lineaActual;
                    }
                    break;
                case "Chess":

                    prefs = PreferenceManager.getDefaultSharedPreferences(this);
                    if (prefs.contains("idiomas")) {
                        String lan = prefs.getString("idiomas", "English");
                        if (lan.equals("Español")) {
                            fich = getResources().openRawResource(R.raw.instrucciones_ajedrez);
                        } else if (lan.equals("Euskara")) {
                            fich = getResources().openRawResource(R.raw.arauak_xakea);
                        } else {
                            fich = getResources().openRawResource(R.raw.instructions_chess);
                        }
                    }
                    buff = new BufferedReader(new InputStreamReader(fich));
                    tutorial = "";
                    while ((lineaActual = buff.readLine()) != null) {
                        tutorial += lineaActual;
                    }
                    break;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return tutorial;
    }
}
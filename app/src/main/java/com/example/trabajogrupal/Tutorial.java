package com.example.trabajogrupal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;

public class Tutorial extends AppCompatActivity {
    Button btnCheckers, btnChess;
    TextView txtTutorial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
            InputStream fich;
            BufferedReader buff;
            switch (juego) {
                case "Checkers":
                    fich = getResources().openRawResource(R.raw.instrucciones_damas);
                    buff = new BufferedReader(new InputStreamReader(fich));
                    lineaActual = buff.readLine();
                    tutorial = null;
                    while (lineaActual != null) {
                        tutorial += lineaActual;
                    }
                    break;
                case "Chess":
                    fich = getResources().openRawResource(R.raw.instrucciones_ajedrez);
                    buff = new BufferedReader(new InputStreamReader(fich));
                    lineaActual = buff.readLine();
                    tutorial = null;
                    while (lineaActual != null) {
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
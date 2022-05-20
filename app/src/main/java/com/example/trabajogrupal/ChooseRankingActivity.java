package com.example.trabajogrupal;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChooseRankingActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView paisesTexto;
    private String[] usernames;
    private int[] elos;
    private ListView list;
    private HashMap<String, Player> usuPaisCheck = new HashMap<String, Player>();
    private HashMap<String, Player> usuPaisChess = new HashMap<String, Player>();
    private ListView ranking;
    private Spinner listaPaises;
    String[] paises, jugadoresNombre;

    ArrayList<String> paisesArrayList;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new LanguagesWorker().setLangua(this);
        super.onCreate(savedInstanceState);
        ThemesWorker tem = new ThemesWorker();
        tem.setThemes(this);
        setContentView(R.layout.activity_choose_ranking);
        paisesTexto = findViewById(R.id.PaisesText);
        new DefineCountryWorker().getCountry(this, this, paisesTexto);
        Country count = Country.getMiCountry();

        ranking = findViewById(R.id.listas);
        listaPaises = findViewById(R.id.spinnerPais);

        PlayerCatalogue catalogue = PlayerCatalogue.getMyPlayerCatalogue();
        paisesArrayList = catalogue.getPaises();
        System.out.println(paisesArrayList);
        paises = new String[paisesArrayList.size() + 2];
        paises[0] = "Select";
        paises[1] = "Global";
        int pos = 2;
        for (String pais : paisesArrayList) {
            paises[pos] = pais;
            pos++;
        }
        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<>(ChooseRankingActivity.this, android.R.layout.simple_spinner_dropdown_item, paises);
        listaPaises.setAdapter(adapterSpinner);

    }

    @Override
    public void onClick(View view) {
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onClickChess(View view) {
        PlayerCatalogue catalogue = PlayerCatalogue.getMyPlayerCatalogue();
        Log.i("Select", "onClickChess: "+listaPaises);
        String selected = listaPaises.getSelectedItem().toString();
        if (selected.equals("Select")) {

            Toast.makeText(this, R.string.porselectpais, Toast.LENGTH_SHORT).show();

        } else {
            if (listaPaises.getSelectedItem().toString().equals("Global")) {
                List<Player> jugadoresList = catalogue.getUsersDescOrder("Chess");
                jugadoresNombre = new String[jugadoresList.size()];
                int pos = 0;
                for (Player i : jugadoresList) {
                    jugadoresNombre[pos] = i.getUsername();
                    pos++;
                }
                System.out.println(jugadoresNombre);
                ArrayAdapter elAdaptador = new ArrayAdapter<String>(ChooseRankingActivity.this, android.R.layout.simple_list_item_2, android.R.id.text1, jugadoresNombre) {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        View vista = super.getView(position, convertView, parent);
                        TextView lineaPrincipal = (TextView) vista.findViewById(android.R.id.text1);
                        TextView lineaSecundaria = (TextView) vista.findViewById(android.R.id.text2);
                        String nombre = jugadoresList.get(position).getUsername() + " " + jugadoresList.get(position).getPais();
                        String puntuacion = String.valueOf(jugadoresList.get(position).getEloChess());
                        lineaPrincipal.setText(nombre);
                        lineaSecundaria.setText(puntuacion);
                        return vista;
                    }
                };
                ranking.setAdapter(elAdaptador);
            } else {
                String pais = listaPaises.getSelectedItem().toString();
                List<Player> jugadoresList = catalogue.getUsersByCountryChess(pais);
                jugadoresNombre = new String[jugadoresList.size()];
                int pos = 0;
                for (Player i : jugadoresList) {
                    jugadoresNombre[pos] = i.getUsername();

                    pos++;
                }
                ArrayAdapter elAdaptador = new ArrayAdapter<String>(ChooseRankingActivity.this, android.R.layout.simple_list_item_2, android.R.id.text1, jugadoresNombre) {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        View vista = super.getView(position, convertView, parent);
                        TextView lineaPrincipal = (TextView) vista.findViewById(android.R.id.text1);
                        TextView lineaSecundaria = (TextView) vista.findViewById(android.R.id.text2);
                        String nombre = jugadoresList.get(position).getUsername();
                        String puntuacion = String.valueOf(jugadoresList.get(position).getEloChess());
                        lineaPrincipal.setText(nombre);
                        lineaSecundaria.setText(puntuacion);
                        return vista;
                    }
                };
                ranking.setAdapter(elAdaptador);
                System.out.println(jugadoresNombre);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onClickCheckers(View view) {
        PlayerCatalogue catalogue = PlayerCatalogue.getMyPlayerCatalogue();

        String selected = listaPaises.getSelectedItem().toString();
        if (selected.equals("Select")) {
            Toast.makeText(this, R.string.porselectpais, Toast.LENGTH_SHORT).show();
        } else {
            if (listaPaises.getSelectedItem().toString().equals("Global")) {
                List<Player> jugadoresList = catalogue.getUsersDescOrder("Checkers");
                Log.i("Ranking", "num-> " + jugadoresList.size());
                jugadoresNombre = new String[jugadoresList.size()];
                int pos = 0;
                for (Player i : jugadoresList) {
                    jugadoresNombre[pos] = i.getUsername();
                    pos++;
                }
                ArrayAdapter elAdaptador = new ArrayAdapter<String>(ChooseRankingActivity.this, android.R.layout.simple_list_item_2, android.R.id.text1, jugadoresNombre) {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        View vista = super.getView(position, convertView, parent);
                        TextView lineaPrincipal = (TextView) vista.findViewById(android.R.id.text1);
                        TextView lineaSecundaria = (TextView) vista.findViewById(android.R.id.text2);
                        String nombre = jugadoresList.get(position).getUsername() + " " + jugadoresList.get(position).getPais();
                        String puntuacion = String.valueOf(jugadoresList.get(position).getEloCheckers());
                        lineaPrincipal.setText(nombre);
                        lineaSecundaria.setText(puntuacion);
                        return vista;
                    }
                };
                ranking.setAdapter(elAdaptador);
            } else {
                String pais = listaPaises.getSelectedItem().toString();
                List<Player> jugadoresList = catalogue.getUsersByCountryCheckers(pais);
                jugadoresNombre = new String[jugadoresList.size()];
                int pos = 0;
                for (Player i : jugadoresList) {
                    jugadoresNombre[pos] = i.getUsername();
                    pos++;
                }
                ArrayAdapter elAdaptador = new ArrayAdapter<String>(ChooseRankingActivity.this, android.R.layout.simple_list_item_2, android.R.id.text1, jugadoresNombre) {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        View vista = super.getView(position, convertView, parent);
                        TextView lineaPrincipal = (TextView) vista.findViewById(android.R.id.text1);
                        TextView lineaSecundaria = (TextView) vista.findViewById(android.R.id.text2);
                        String nombre = jugadoresList.get(position).getUsername();
                        String puntuacion = String.valueOf(jugadoresList.get(position).getEloCheckers());
                        lineaPrincipal.setText(nombre);
                        lineaSecundaria.setText(puntuacion);
                        return vista;
                    }
                };
                ranking.setAdapter(elAdaptador);
                System.out.println(jugadoresNombre);
            }
        }


    }

}
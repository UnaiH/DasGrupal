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
        llenarCatalogo();
        ranking = findViewById(R.id.listas);
        listaPaises = findViewById(R.id.spinnerPais);


    }

    @Override
    public void onClick(View view) {
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onClickChess(View view) {
        PlayerCatalogue catalogue = PlayerCatalogue.getMyPlayerCatalogue();
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

    public void llenarCatalogo() {
        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(WorkerGetUsersForRanking.class).build();
        WorkManager.getInstance(ChooseRankingActivity.this).getWorkInfoByIdLiveData(otwr.getId()).observe(ChooseRankingActivity.this, new Observer<WorkInfo>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onChanged(WorkInfo workInfo) {
                if (workInfo != null && workInfo.getState().isFinished()) {
                    Boolean resultadoPhp = workInfo.getOutputData().getBoolean("exito", false);
                    System.out.println("RESULTADO --> " + resultadoPhp);
                    if (resultadoPhp) {
                        String[] resultados = workInfo.getOutputData().getStringArray("datosUsuario");

                        for (int i = 0; i < resultados.length; i++) {
                            if (!resultados[i].equals("false")) {
                                getDatosJugador(resultados[i]);
                            }
                        }
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
                        //Log.i("Resultado", "onChanged: " + catalogue.getUsersByCountry());
                        List<Player> jugadoresList = catalogue.getUsersDescOrder("Checkers");
                        Log.i("GLOBAL", "--> " + jugadoresList);
                        Log.i("FiltradoPais", "ES-> " + catalogue.getUsersByCountryCheckers("ES"));
                        Log.i("FiltradoPais", "ES-> " + catalogue.getUsersByCountryChess("ES"));

                        Log.i("FiltradoPais", "FR-> " + catalogue.getUsersByCountryCheckers("FR"));
                        Log.i("FiltradoPais", "FR-> " + catalogue.getUsersByCountryChess("FR"));

                        Log.i("FiltradoPais", "CN-> " + catalogue.getUsersByCountryCheckers("CN"));
                        Log.i("FiltradoPais", "CN-> " + catalogue.getUsersByCountryChess("CN"));

                    }
                }
            }
        });
        WorkManager.getInstance(ChooseRankingActivity.this).enqueue(otwr);
    }

    public void getDatosJugador(String s) {
        // "email":"daniel.juape3@gmail.com","username":"Daddy","eloCheckers":"1001","country":"FR"
        String[] datosSinComas = s.split(",");
        ArrayList<String> valores = new ArrayList<>();
        for (int i = 0; i < datosSinComas.length; i++) {
            valores.add(datosSinComas[i].split(":")[1].replace('"', ' ').trim());
        }
        String ultimoValor = valores.get(valores.size() - 1);
        valores.remove(valores.size() - 1);
        valores.add(ultimoValor.substring(0, ultimoValor.length() - 2));

        System.out.println("DatosJugador--> " + valores); //[daniel.juape3@gmail.com, Daddy, 1001, FR]
        String email = valores.get(0), username = valores.get(1), pais = valores.get(4);
        int eloCheckers = Integer.parseInt(valores.get(2)), eloChess = Integer.parseInt(valores.get(3));
        PlayerCatalogue catalogoJugador = PlayerCatalogue.getMyPlayerCatalogue();

        catalogoJugador.addPlayer(email, eloCheckers, eloChess, username, pais);

    }


    public void onBackPressed() {
        Intent i = new Intent(ChooseRankingActivity.this, MainActivity.class);
        setResult(RESULT_OK, i);
        finish();
        startActivity(i);
    }
}
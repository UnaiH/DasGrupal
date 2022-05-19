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
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class ChooseRankingActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView paisesTexto;
    private String[] usernames;
    private int[] elos;
    private ListView list;
    private HashMap<String, Player> usuPaisCheck = new HashMap<String, Player>();
    private HashMap<String, Player> usuPaisChess = new HashMap<String, Player>();

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
        Log.i("Direcciones", "onCreate: " + count.getNombre());

    }

    @Override
    public void onClick(View view) {
    }

    public void onClickCheckers(View view) {

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
                        Log.i("Resultado", "onChanged: " + catalogue.getUsersByCountry());
                        Log.i("FiltradoPais","ES-> "+catalogue.getUsersByCountryCheckers("ES"));
                        Log.i("FiltradoPais","ES-> "+catalogue.getUsersByCountryChess("ES"));

                        Log.i("FiltradoPais","FR-> "+catalogue.getUsersByCountryCheckers("FR"));
                        Log.i("FiltradoPais","FR-> "+catalogue.getUsersByCountryChess("FR"));

                        Log.i("FiltradoPais","CN-> "+catalogue.getUsersByCountryCheckers("CN"));
                        Log.i("FiltradoPais","CN-> "+catalogue.getUsersByCountryChess("CN"));

                    }
                }
            }
        });
        WorkManager.getInstance(ChooseRankingActivity.this).enqueue(otwr);

        usernames = new String[0];
        elos = new int[0];
        setContentView(R.layout.activity_global_ranking);
        GlobalRankingAdapter adapter = new GlobalRankingAdapter(this, usernames, elos);
        list = findViewById(R.id.globalRanking);
        list.setAdapter(adapter);
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

    public void onClickChess(View view) {
        usernames = new String[0];
        elos = new int[0];
        setContentView(R.layout.activity_global_ranking);
        GlobalRankingAdapter adapter = new GlobalRankingAdapter(this, usernames, elos);
        list = findViewById(R.id.globalRanking);
        list.setAdapter(adapter);
    }

    public void onBackPressed() {
        Intent i = new Intent(ChooseRankingActivity.this, MainActivity.class);
        setResult(RESULT_OK, i);
        finish();
        startActivity(i);
    }
}
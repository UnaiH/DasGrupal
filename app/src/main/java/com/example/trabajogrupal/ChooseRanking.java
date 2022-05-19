package com.example.trabajogrupal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class ChooseRanking extends AppCompatActivity implements View.OnClickListener {
    private TextView paisesTexto;
    private String[] usernames;
    private int[] elos;
    private ListView list;
    private HashMap<String,Player> usuPaisCheck =new HashMap<String,Player>();
    private HashMap<String,Player> usuPaisChess =new HashMap<String,Player>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new LanguagesWorker().setLangua(this);
        super.onCreate(savedInstanceState);
        ThemesWorker tem=new ThemesWorker();
        tem.setThemes(this);
        setContentView(R.layout.activity_choose_ranking);
        paisesTexto = findViewById(R.id.PaisesText);
        new DefineCountryWorker().getCountry(this, this, paisesTexto);
        Country count=Country.getMiCountry();
        Log.i("Direcciones", "onCreate: "+ count.getNombre());
    }

    @Override
    public void onClick(View view) {
    }
    public void onClickCheckers(View view) {

        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(WorkerGetUsersByCountryCheckers.class).build();
        WorkManager.getInstance(ChooseRanking.this).getWorkInfoByIdLiveData(otwr.getId()).observe(ChooseRanking.this, new Observer<WorkInfo>() {
            @Override
            public void onChanged(WorkInfo workInfo) {
                if (workInfo != null && workInfo.getState().isFinished()) {
                    Boolean resultadoPhp = workInfo.getOutputData().getBoolean("exito", false);
                    System.out.println("RESULTADO --> " + resultadoPhp);
                    if (resultadoPhp) {
                        String[] resultados = workInfo.getOutputData().getStringArray("datosUsuario");
                        Integer index = 0;
                        String aux;
                        while (index < resultados.length) {
                            aux = resultados[index];

                            index++;
                        }
                        Log.i("Resultado", "onChanged: " + resultados[0]);
                    }
                }
            }
        });
        WorkManager.getInstance(ChooseRanking.this).enqueue(otwr);

        usernames=new String[0];
        elos=new int[0];
        setContentView(R.layout.activity_global_ranking);
        GlobalRankingAdapter adapter = new GlobalRankingAdapter(this, usernames, elos);
        list=findViewById(R.id.globalRanking);
        list.setAdapter(adapter);
    }
    public void onClickChess(View view) {
        usernames=new String[0];
        elos=new int[0];
        setContentView(R.layout.activity_global_ranking);
        GlobalRankingAdapter adapter = new GlobalRankingAdapter(this, usernames, elos);
        list=findViewById(R.id.globalRanking);
        list.setAdapter(adapter);
    }
    public void onBackPressed(){
        Intent i = new Intent(ChooseRanking.this, MainActivity.class);
        setResult(RESULT_OK, i);
        finish();
        startActivity(i);
    }
}
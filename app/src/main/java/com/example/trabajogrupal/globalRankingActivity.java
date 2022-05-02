package com.example.trabajogrupal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class globalRankingActivity extends AppCompatActivity {
    private String[] usernames;
    private int[] elos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global_ranking);
        globalRankingAdapter adaptador = new globalRankingAdapter(this, usernames, elos);
        //Se llama al adaptador de esta lista.
    }
    public void onBackPressed() {
        finish();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
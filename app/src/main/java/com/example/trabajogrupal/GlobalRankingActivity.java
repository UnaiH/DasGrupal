package com.example.trabajogrupal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

public class GlobalRankingActivity extends AppCompatActivity {
    private String[] usernames;
    private int[] elos;
    private ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        usernames=new String[0];
        elos=new int[0];
        setContentView(R.layout.activity_global_ranking);
        GlobalRankingAdapter adapter = new GlobalRankingAdapter(this, usernames, elos);
        list=findViewById(R.id.globalRanking);
        list.setAdapter(adapter);
    }
    public void onBackPressed() {
        finish();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
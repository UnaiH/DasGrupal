package com.example.trabajogrupal;

import static com.example.trabajogrupal.R.layout.activity_select_menu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SelectMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_select_menu);
    }
    public void onClickCheckers(View v)
    {
        finish();
        Intent i = new Intent(this, CheckersActivity.class);
        startActivity(i);
    }
    public void onClickChess(View v)
    {
        finish();
        Intent i = new Intent(this, ChessActivity.class);
        startActivity(i);
    }
    public void onClickPreferencies(View v){
        finish();
        Intent i = new Intent(this, Preferencies.class);
        startActivity(i);
    }

    public void onClickLogout(View view) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
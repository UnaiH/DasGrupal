package com.example.trabajogrupal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class NewGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new LanguagesWorker().setLangua(this);
        super.onCreate(savedInstanceState);

        ThemesWorker tem = new ThemesWorker();
        tem.setThemes(this);

        setContentView(R.layout.activity_new_game);
    }

    public void onClickCheckers(View v) {
        int gameID = 0;

        Intent i = new Intent(this, CheckersActivity.class);
        i.putExtra("idGame", gameID);
        startActivity(i);
    }

    public void onClickChess(View v) {
        int gameID = 0;

        Intent i = new Intent(this, ChessActivity.class);
        i.putExtra("idGame", gameID);
        startActivity(i);
    }
}
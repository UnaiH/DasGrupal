package com.example.trabajogrupal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class SelectGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new LanguagesWorker().setLangua(this);
        super.onCreate(savedInstanceState);

        ThemesWorker tem = new ThemesWorker();
        tem.setThemes(this);

        setContentView(R.layout.activity_select_game);
    }

    public void onClickNewGame(View v) {
        Intent i = new Intent(this, NewGameActivity.class);
        startActivity(i);
    }

    public void onClickGamesInCourse(View v) {
        Intent i = new Intent(this, GameListActivity.class);
        i.putExtra("finished", false);
        startActivity(i);
    }

    public void onClickRecord(View v) {
        Intent i = new Intent(this, GameListActivity.class);
        i.putExtra("finished", true);
        startActivity(i);
    }
}

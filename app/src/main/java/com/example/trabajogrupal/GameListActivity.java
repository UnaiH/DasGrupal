package com.example.trabajogrupal;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class GameListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        new LanguagesWorker().setLangua(this);
        super.onCreate(savedInstanceState);
        ThemesWorker tem=new ThemesWorker();
        tem.setThemes(this);
        setContentView(R.layout.activity_game_list);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            boolean finished = extras.getBoolean("finished");

            //Player currentUser = PlayerCatalogue.getMyPlayerCatalogue().returnPlayer(PlayerCatalogue.getMyPlayerCatalogue().getCurrentUser());
            Player currentUser =PlayerCatalogue.getMyPlayerCatalogue().getCurrentPlayer();
            ArrayList games;

            if (finished) {
                games = currentUser.getGamesFinished();
            }
            else {
                games = currentUser.getGamesInCourse();
            }

            ListView gameListView = (ListView) findViewById(R.id.gameList);
            GameListAdapter eladap = new GameListAdapter(getApplicationContext(), games);
            gameListView.setAdapter(eladap);

            if (!finished) {
                gameListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String gameType = ((TextView) view.findViewById(R.id.gameType)).getText().toString();
                        int gameID = Integer.parseInt(((TextView) view.findViewById(R.id.gameID)).getText().toString());
                        Intent i = null;
                        Log.i("workerPHP",gameType);
                        Log.i("workerPHP",getString(R.string.checkers));
                        if (gameType.equals("Checkers") || gameType.equals("Damas") || gameType.equals("Damak"))
                        {
                            i = new Intent(view.getContext(), CheckersActivity.class);
                        }
                        else if (gameType.equals("Chess") || gameType.equals("Ajedrez") || gameType.equals("Xake"))
                        {
                            i = new Intent(view.getContext(), ChessActivity.class);
                        }
                        i.putExtra("idGame", gameID);
                        startActivity(i);
                    }
                });
            }
        }
    }
}

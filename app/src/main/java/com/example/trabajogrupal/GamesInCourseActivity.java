package com.example.trabajogrupal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class GamesInCourseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        new LanguagesWorker().setLangua(this);
        super.onCreate(savedInstanceState);
        ThemesWorker tem=new ThemesWorker();
        tem.setThemes(this);
        setContentView(R.layout.activity_games_in_course);

        Player currentUser = PlayerCatalogue.getMyPlayerCatalogue().getCurrentUser();
        ArrayList<GameInCourse> gamesInCourse = currentUser.getGamesInCourse();

        ListView gamesInCourseView = (ListView) findViewById(R.id.gamesInCourse);
        GamesInCourseAdapter eladap = new GamesInCourseAdapter(getApplicationContext(), gamesInCourse);
        gamesInCourseView.setAdapter(eladap);

        gamesInCourseView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                String gameType =  ((TextView)view.findViewById(R.id.gameType)).getText().toString();
                int gameID = Integer.parseInt(((TextView)view.findViewById(R.id.gameID)).getText().toString());
                Intent i = null;
                if (gameType.equals("Checkers")) {
                    i = new Intent(view.getContext(), CheckersActivity.class);
                }
                else if (gameType.equals("Chess")) {
                    i = new Intent(view.getContext(), ChessActivity.class);
                }
                i.putExtra("idGame", gameID);
                setResult(RESULT_OK, i);
                finish();
                startActivity(i);
            }
        });
    }
}

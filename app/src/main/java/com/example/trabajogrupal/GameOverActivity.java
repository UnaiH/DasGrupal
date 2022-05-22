package com.example.trabajogrupal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        new LanguagesWorker().setLangua(this);
        super.onCreate(savedInstanceState);
        ThemesWorker tem=new ThemesWorker();
        tem.setThemes(this);
        setContentView(R.layout.activity_game_over);
        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            String winner= extras.getString("winner");
            String loser= extras.getString("loser");
            String gameType= extras.getString("gameType");
            int idGame = extras.getInt("idGame");
            actualizarELO(winner, loser, gameType);
            setWinner(winner, idGame);
        }
    }

    public void onClick(View v)
    {
        Intent i = new Intent(this, SelectMenuActivity.class);
        i.putExtra("email", PlayerCatalogue.getMyPlayerCatalogue().getCurrentUser());
        PlayerCatalogue catalogue = PlayerCatalogue.getMyPlayerCatalogue();
        catalogue.setCurrentUser(PlayerCatalogue.getMyPlayerCatalogue().getCurrentUser());
        startActivity(i);
        finish();
    }

    private void actualizarELO(String winner, String loser, String gameType)
    {
        int eloWinner;
        int eloLoser;
        if (gameType.equals("Checkers"))
        {
            eloWinner = PlayerCatalogue.getMyPlayerCatalogue().returnPlayer(winner).getEloCheckers();
            eloLoser = PlayerCatalogue.getMyPlayerCatalogue().returnPlayer(loser).getEloCheckers();
        }
        else
        {
            eloWinner = PlayerCatalogue.getMyPlayerCatalogue().returnPlayer(winner).getEloChess();
            eloLoser = PlayerCatalogue.getMyPlayerCatalogue().returnPlayer(loser).getEloChess();
        }

        int paso1 = eloLoser - eloWinner;
        double paso2 = (double) paso1/400;
        double paso3 = 1 + Math.pow(10,paso2);
        double paso4 = 1/paso3;
        int paso5 = (int)Math.round(20 * (1-paso4));
        int newEloWinner = eloWinner + paso5;
        int newEloLoser = eloLoser - paso5;

        TextView body=findViewById(R.id.TextViewGameOverBody);
        body.setText(winner + getResources().getString(R.string.gameOver1) + paso5 +"\n" + loser + getResources().getString(R.string.gameOver2) + paso5);


        updateElo(winner,newEloWinner,"elo" + gameType);
        updateElo(loser,newEloLoser,"elo" + gameType);

        if (gameType.equals("Checkers"))
        {
            PlayerCatalogue.getMyPlayerCatalogue().returnPlayer(winner).setEloCheckers(newEloWinner);
            PlayerCatalogue.getMyPlayerCatalogue().returnPlayer(loser).setEloCheckers(newEloLoser);
        }
        else
        {
            PlayerCatalogue.getMyPlayerCatalogue().returnPlayer(winner).setEloChess(newEloWinner);
            PlayerCatalogue.getMyPlayerCatalogue().returnPlayer(loser).setEloChess(newEloLoser);
        }

    }


    private void updateElo(String email, int newElo, String eloType)
    {
        Data.Builder data = new Data.Builder();

        data.putString("email", email);
        data.putInt("newElo", newElo);
        data.putString("eloType", eloType);

        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(WorkerUpdateElo.class)
                .setInputData(data.build())
                .build();
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(otwr.getId())
                .observe(this, new Observer<WorkInfo>()
                {
                    @Override
                    public void onChanged(WorkInfo workInfo)
                    {
                        if (workInfo != null && workInfo.getState().isFinished())
                        {
                            Log.i("workerPHP", "Elo changed");
                        }
                    }
                });
        WorkManager.getInstance(this).enqueue(otwr);
    }

    private void setWinner(String winner, int idGame)
    {
        Data.Builder data = new Data.Builder();

        data.putInt("idGame", idGame);
        data.putString("winner", winner);

        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(WorkerSetWinner.class)
                .setInputData(data.build())
                .build();
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(otwr.getId())
                .observe(this, new Observer<WorkInfo>()
                {
                    @Override
                    public void onChanged(WorkInfo workInfo)
                    {
                        if (workInfo != null && workInfo.getState().isFinished())
                        {
                            Log.i("workerPHP", "Elo changed");
                        }
                    }
                });
        WorkManager.getInstance(this).enqueue(otwr);
    }

}
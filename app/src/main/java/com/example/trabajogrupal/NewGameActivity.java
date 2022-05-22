package com.example.trabajogrupal;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NewGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new LanguagesWorker().setLangua(this);
        super.onCreate(savedInstanceState);

        ThemesWorker tem = new ThemesWorker();
        tem.setThemes(this);

        setContentView(R.layout.activity_new_game);
    }

    public void onClickCheckersOnline(View v) {
        createGame("Checkers");
    }

    public void onClickChessOnline(View v) {
        createGame("Chess");
    }

    public void onClickCheckersLocal(View v)
    {

    }

    public void onClickChessLocal(View v)
    {

    }
    public void createGame(String gameType) {
        getUsers();
        List<Player> players = PlayerCatalogue.getMyPlayerCatalogue().getUsers();
        if (players.size()>1) {
            Constraints restricciones = new Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build();
            OneTimeWorkRequest otwr =
                    new OneTimeWorkRequest.Builder(WorkerCountGames.class)
                            .setConstraints(restricciones)
                            .build();
            WorkManager.getInstance(this).getWorkInfoByIdLiveData(otwr.getId())
                    .observe(this, new Observer<WorkInfo>() {
                        @Override
                        public void onChanged(WorkInfo workInfo) {
                            if(workInfo != null && workInfo.getState().isFinished()){
                                int gameID = workInfo.getOutputData().getInt("resultado",0)+1;

                                createGame2(gameType, gameID);
                            }
                        }
                    });
            WorkManager.getInstance(this).enqueue(otwr);
        }
    }

    public void createGame2(String gameType, int gameID) {
        List<Player> players = PlayerCatalogue.getMyPlayerCatalogue().getUsers();
        Player currentPlayer = PlayerCatalogue.getMyPlayerCatalogue().getCurrentPlayer();
        int indCurrentPlayer = players.indexOf(currentPlayer);
        int random = 0;
        boolean find = false;
        while (!find) {
            random = new Random().nextInt(players.size());
            if (random!=indCurrentPlayer) {
                find = true;
            }
        }
        Player rival = players.get(random);

        Constraints restricciones = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();
        Data datos = new Data.Builder()
                .putInt("idGame",gameID)
                .putString("player1", currentPlayer.getEmail())
                .putString("player2", rival.getEmail())
                .putString("gameType", gameType)
                .putString("nextTurn", "White")
                .build();
        OneTimeWorkRequest otwr2 =
                new OneTimeWorkRequest.Builder(WorkerStartGame.class)
                        .setConstraints(restricciones)
                        .setInputData(datos)
                        .build();
        WorkManager.getInstance(getApplicationContext()).getWorkInfoByIdLiveData(otwr2.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        if(workInfo != null && workInfo.getState().isFinished()){
                            Game game = new Game(gameID, currentPlayer, rival, gameType, "White");
                            currentPlayer.addInCourse(game);
                            rival.addInCourse(game);

                            Intent i = null;
                            if (gameType.equals("Checkers")) {
                                i = new Intent(getApplicationContext(), CheckersActivity.class);
                            }
                            else if (gameType.equals("Chess")) {
                                i = new Intent(getApplicationContext(), ChessActivity.class);
                            }
                            i.putExtra("idGame", gameID);
                            startActivity(i);
                        }
                    }
                });
        WorkManager.getInstance(getApplicationContext()).enqueue(otwr2);
    }

    public void getUsers() {
        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(WorkerGetUsersForRanking.class).build();
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(otwr.getId()).observe(this, new Observer<WorkInfo>() {
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
                    }
                }
            }
        });
        WorkManager.getInstance(this).enqueue(otwr);
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
        getFotoPerfil(email);
    }

    public void getFotoPerfil(String user) {
        /*Obtiene la foto de la BBDD y la pone en el imageview*/
        Data datos = new Data.Builder().putString("user", user).build();
        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(WorkerGetImage.class).setInputData(datos).build();
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(otwr.getId()).observe(this, new Observer<WorkInfo>() {
            @Override
            public void onChanged(WorkInfo workInfo) {
                if (workInfo != null && workInfo.getState().isFinished()) {
                    Boolean resultadoPhp = workInfo.getOutputData().getBoolean("exito", false);
                    System.out.println("RESULTADO CARGAR IMAGEN --> " + resultadoPhp);
                    if (resultadoPhp) {

                        LocalDB myDB = new LocalDB(getApplicationContext(), "Chess", null, 1);
                        byte[] decodificado = myDB.getImage(user);
                        if (decodificado != null) {
                            Bitmap elBitmap = BitmapFactory.decodeByteArray(decodificado, 0, decodificado.length);
                            PlayerCatalogue catalogue = PlayerCatalogue.getMyPlayerCatalogue();
                            Player unPlayer = catalogue.getPlayer(user);
                            unPlayer.setImage(elBitmap);
                        }
                    }
                }
            }
        });
        WorkManager.getInstance(this).enqueue(otwr);
    }
}
package com.example.trabajogrupal;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class WorkerSelectGameList extends Worker
{
    public WorkerSelectGameList(@NonNull Context context, @NonNull WorkerParameters workerParams)
    {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork()
    {
        String currentPlayer = getInputData().getString("currentPlayer");
        boolean finished = getInputData().getBoolean("finished", false);
        String parametros = "currentPlayer="+currentPlayer+"&finished="+finished;

        String direccion = "http://ec2-52-56-170-196.eu-west-2.compute.amazonaws.com/prehecho001/WEB/GroupProyect/SelectGameList.php";
        HttpURLConnection urlConnection = null;
        try
        {
            URL destino = new URL(direccion);
            urlConnection = (HttpURLConnection) destino.openConnection();
            urlConnection.setConnectTimeout(5000);
            urlConnection.setReadTimeout(5000);

            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            PrintWriter out = new PrintWriter(urlConnection.getOutputStream());
            out.print(parametros);
            out.close();

            int statusCode = urlConnection.getResponseCode();
            if (statusCode == 200)
            {
                BufferedInputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                String line, result = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                inputStream.close();

                JSONArray jsonArray = new JSONArray(result);
                ArrayList<Game> lista = new ArrayList<Game>();

                for(int i = 0; i < jsonArray.length(); i++)
                {
                    JSONObject json = jsonArray.getJSONObject(i);
                    int idGame = json.getInt("idGame");
                    String emailPlayer1 = json.getString("player1");
                    String emailPlayer2 = json.getString("player2");
                    String gameType = json.getString("gameType");
                    String nextTurn = json.getString("nextTurn");
                    String winner = json.getString("winner");

                    Player player1 = PlayerCatalogue.getMyPlayerCatalogue().getPlayer(emailPlayer1);
                    Player player2 = PlayerCatalogue.getMyPlayerCatalogue().getPlayer(emailPlayer2);

                    Game game = null;
                    if (finished) {
                        game = new Game(idGame, player1, player2, gameType, null);
                        game.setWinner(winner);
                    }
                    else {
                        game = new Game(idGame, player1, player2, gameType, nextTurn);
                    }

                    lista.add(game);
                }

                if (finished) {
                    PlayerCatalogue.getMyPlayerCatalogue().getCurrentPlayer().setGamesFinished(lista);
                }
                else {
                    PlayerCatalogue.getMyPlayerCatalogue().getCurrentPlayer().setGamesInCourse(lista);
                }

                Data resultados = new Data.Builder()
                        .putString("resultado", result)
                        .build();
                return Result.success(resultados);
            }
            return Result.failure();
        }
        catch (MalformedURLException e) {e.printStackTrace();}
        catch (IOException e) {e.printStackTrace();} catch (JSONException e) {
            e.printStackTrace();
        }
        return Result.failure();
    }
}

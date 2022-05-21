package com.example.trabajogrupal;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WorkerStartGame extends Worker
{
    public WorkerStartGame(@NonNull Context context, @NonNull WorkerParameters workerParams)
    {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork()
    {
        int idGame = getInputData().getInt("idGame", -1);
        String player1 = getInputData().getString("player1");
        String player2 = getInputData().getString("player2");
        String gameType = getInputData().getString("gameType");
        String nextTurn = getInputData().getString("nextTurn");
        String parametros = "idGame="+idGame+"&player1="+player1+"&player2="+player2+"&gameType="+gameType+"&nextTurn="+nextTurn;

        String direccion = "http://ec2-52-56-170-196.eu-west-2.compute.amazonaws.com/prehecho001/WEB/GroupProyect/StartGame.php";
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

                Data resultados = new Data.Builder()
                        .putString("resultado", result)
                        .build();
                return Result.success(resultados);
            }
            return Result.failure();
        }
        catch (MalformedURLException e) {e.printStackTrace();}
        catch (IOException e) {e.printStackTrace();}
        return Result.failure();
    }
}

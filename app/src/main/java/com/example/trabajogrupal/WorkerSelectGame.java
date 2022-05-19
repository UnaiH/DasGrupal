package com.example.trabajogrupal;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

//worker que devuelve el token de un usuario
public class WorkerSelectGame extends Worker
{
    public WorkerSelectGame(@NonNull Context context, @NonNull WorkerParameters workerParams)
    {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork()
    {
        String direccion = "http://ec2-52-56-170-196.eu-west-2.compute.amazonaws.com/prehecho001/WEB/GroupProyect/SelectGame.php";
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

            int idGame = getInputData().getInt("idGame",-1);


            PrintWriter out = new PrintWriter(urlConnection.getOutputStream());
            String parametros="idGame="+idGame;
            out.print(parametros);
            out.close();

            int statusCode = urlConnection.getResponseCode();
            Log.i("workerPHP","statusCodeSelectionPieces: " + statusCode);
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
                String[] lista = new String[jsonArray.length()*5];
                for(int i = 0; i < jsonArray.length(); i++)
                {
                    String player1 = jsonArray.getJSONObject(i).getString("player1");
                    String player2 = jsonArray.getJSONObject(i).getString("player2");
                    String nextTurn = jsonArray.getJSONObject(i).getString("nextTurn");
                    lista[3*i] = player1;
                    lista[3*i+1] = player2;
                    lista[3*i+2] = nextTurn;
                }
                Data jason = new Data.Builder()
                        .putStringArray("lista",lista)
                        .build();
                return Result.success(jason);
            }
            return Result.failure();
        }
        catch (MalformedURLException e) {e.printStackTrace();}
        catch (IOException e) {e.printStackTrace();}
        catch (JSONException e) {
            e.printStackTrace();
        }
        return Result.failure();
    }
}
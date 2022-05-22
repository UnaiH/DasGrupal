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
public class WorkerSelectPieces extends Worker
{
    public WorkerSelectPieces(@NonNull Context context, @NonNull WorkerParameters workerParams)
    {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork()
    {
        String direccion = "http://ec2-52-56-170-196.eu-west-2.compute.amazonaws.com/prehecho001/WEB/GroupProyect/SelectPieces.php";
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
                String[] lista = new String[jsonArray.length()*3];
                for(int i = 0; i < jsonArray.length(); i++)
                {
                    String type = jsonArray.getJSONObject(i).getString("type");
                    int posX = jsonArray.getJSONObject(i).getInt("posX");
                    int posY = jsonArray.getJSONObject(i).getInt("posY");
                    lista[3*i] = type;
                    lista[3*i+1] = String.valueOf(posX);
                    lista[3*i+2] = String.valueOf(posY);
                }
                Log.i("workerPHP","piezasSeleccionadas del id: " +idGame + " / " + lista);
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
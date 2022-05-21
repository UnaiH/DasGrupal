package com.example.trabajogrupal;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.ListenableWorker;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

//worker que borra un usuario
public class WorkerUpdateElo extends Worker
{
    public WorkerUpdateElo(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public ListenableWorker.Result doWork() {
        String direccion = "http://ec2-52-56-170-196.eu-west-2.compute.amazonaws.com/prehecho001/WEB/GroupProyect/UpdateElo.php";
        HttpURLConnection urlConnection = null;
        try {
            URL destino = new URL(direccion);
            urlConnection = (HttpURLConnection) destino.openConnection();
            urlConnection.setConnectTimeout(5000);
            urlConnection.setReadTimeout(5000);
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            String email = getInputData().getString("email");
            int newElo = getInputData().getInt("newElo", -1);
            String eloType = getInputData().getString("eloType");

            Log.i("workerPHP","Update Elo: " + email + "-" + newElo + "-" + eloType);

            PrintWriter out = new PrintWriter(urlConnection.getOutputStream());
            String parametros = "email="+email+"&newElo="+newElo+"&eloType="+eloType;
            out.print(parametros);
            out.close();

            int statusCode = urlConnection.getResponseCode();
            Log.i("workerPHP", "statusCodeUpdateElo: " + statusCode);
            if (statusCode == 200) {
                return Result.success();
            }
            return Result.failure();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.failure();
    }
}
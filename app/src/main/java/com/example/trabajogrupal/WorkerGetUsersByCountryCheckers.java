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
import java.util.ArrayList;

public class WorkerGetUsersByCountryCheckers extends Worker {
    public WorkerGetUsersByCountryCheckers(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        String server = "http://ec2-52-56-170-196.eu-west-2.compute.amazonaws.com/prehecho001/WEB/GroupProyect/getUsersByCountryCheckers.php";
        HttpURLConnection urlConnection = null;
        try {
            URL destino = new URL(server);
            urlConnection = (HttpURLConnection) destino.openConnection();
            urlConnection.setConnectTimeout(5000);
            urlConnection.setReadTimeout(5000);

            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            PrintWriter out = new PrintWriter(urlConnection.getOutputStream());
            out.close();

            int statusCode = urlConnection.getResponseCode();
            Log.i("Tag", "StatusCode CountryCheckers: " + statusCode);
            if (statusCode == 200) {
                BufferedInputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                String line, result = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                String rdo = bufferedReader.readLine();
                System.out.println("RDO----> " + result);
                inputStream.close();
                JSONArray jsonArray = new JSONArray(result);
                ArrayList<String> datosUsuario = new ArrayList<>();

                JSONArray js = new JSONArray();
                for (int i = 0; i < jsonArray.length(); i++) {
                    datosUsuario.add(jsonArray.getJSONObject(i).getString("resultado"));
                    //js = jsonArray.getJSONObject(i).getJSONArray("resultado");
                }
                System.out.println("SELECT EJERCICIOS-> " + datosUsuario);
                Data datos;
                if (datosUsuario.contains("false")) {
                    datos = new Data.Builder().putBoolean("exito", false).build();
                } else {
                    String[] datosUsuarioArray = datosUsuario.toArray(new String[datosUsuario.size()]);

                    datos = new Data.Builder().putBoolean("exito", true).putStringArray("datosUsuario", datosUsuarioArray).build();
                }
                return Result.success(datos);
            } else {
                return Result.retry();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return Result.failure();
    }
}

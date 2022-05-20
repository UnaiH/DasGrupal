package com.example.trabajogrupal;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

public class DefineCountryWorker {
    private Double ActLog;
    private Double ActLat;
    private Location local;
    private FusedLocationProviderClient client;
    public DefineCountryWorker(){

    }
    public void localizacionBD(String user, Context context, Activity activity) {
        Geocoder geo = new Geocoder(context, Locale.getDefault());
        if(checkPlayServices(context,activity)) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
            else if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            }
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED||ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                final String[] pais = {""};
                client = LocationServices.getFusedLocationProviderClient(context);
                client.getLastLocation().addOnSuccessListener(activity, new OnSuccessListener<Location>(){
                    @Override
                    public void onSuccess(Location location) {
                        // se escribe la ultima posicion del movil (generalmente la actual).
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            List<Address> direccion;
                            try {
                                direccion=geo.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                                ActLat=location.getLatitude();
                                ActLog=location.getLongitude();
                                local=location;
                                if(!direccion.isEmpty()) {
                                    pais[0] =direccion.get(0).getCountryCode();
                                    Log.i("Direcciones", "onSuccess: "+ pais[0]);
                                    Data.Builder data = new Data.Builder();
                                    Log.i("Email", "onSuccess: "+user);
                                    data.putString("email", user);
                                    data.putString("country", pais[0]);

                                    OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(WorkerInsertCountry.class)
                                            .setInputData(data.build())
                                            .build();
                                    WorkManager.getInstance(context).getWorkInfoByIdLiveData(otwr.getId())
                                            .observe((LifecycleOwner) activity, new Observer<WorkInfo>() {
                                                @Override
                                                public void onChanged(WorkInfo workInfo) {
                                                }
                                            });
                                    WorkManager.getInstance(context).enqueue(otwr);

                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        }
    }

    public void getCountry(Context context, Activity activity, TextView paisTexto){
        Geocoder geo = new Geocoder(context, Locale.getDefault());
        if(checkPlayServices(context,activity)) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
            else if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            }
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED||ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                final String[] pais = {""};
                client = LocationServices.getFusedLocationProviderClient(context);
                client.getLastLocation().addOnSuccessListener(activity, new OnSuccessListener<Location>(){
                    @Override
                    public void onSuccess(Location location) {
                        // se escribe la ultima posicion del movil (generalmente la actual).
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            List<Address> direccion;
                            try {
                                direccion=geo.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                                ActLat=location.getLatitude();
                                ActLog=location.getLongitude();
                                local=location;
                                if(!direccion.isEmpty()) {
                                    pais[0] =direccion.get(0).getCountryCode();
                                    Log.i("Direcciones", "onSuccess: "+ pais[0]);
                                    Country count=Country.getMiCountry();
                                    count.setNombre(pais[0]);
                                    paisTexto.setText(R.string.codPais);
                                    paisTexto.setText(paisTexto.getText() + pais[0]);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        }
    }

    private boolean checkPlayServices(Context ctxt, Activity activ) {
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int code = api.isGooglePlayServicesAvailable(ctxt);
        if (code == ConnectionResult.SUCCESS) {
            return true;
        }
        else {
            if (api.isUserResolvableError(code)){
                api.getErrorDialog(activ, code, 58).show();
            }
            return false;
        }
    }
}

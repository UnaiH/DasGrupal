package com.example.trabajogrupal;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class ServicioFirebase extends FirebaseMessagingService {

    public void onMessageReceived(RemoteMessage remoteMessage) {
        NotificationManager elManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder elBuilder = new NotificationCompat.Builder(this, "IdCanal");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel elCanal = new NotificationChannel("IdCanal", "NombreCanal",
                    NotificationManager.IMPORTANCE_DEFAULT);

            elCanal.setDescription("Descripción del canal");
            elCanal.enableLights(true);
            elCanal.setLightColor(Color.RED);
            elCanal.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            elCanal.enableVibration(true);

            elManager.createNotificationChannel(elCanal);
        }

        elBuilder.setSmallIcon(R.drawable.chess_white_king)
                .setContentTitle("¡Te han retado a jugar una partida!")
                .setContentText("¡Es tu turno!")
                .setVibrate(new long[]{0, 1000, 500, 1000})
                .setAutoCancel(true);

        elManager.notify(1, elBuilder.build());
    }

}
package com.example.trabajogrupal;

import static com.example.trabajogrupal.R.layout.activity_select_menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.io.ByteArrayInputStream;

public class SelectMenuActivity extends AppCompatActivity {
    String user;
    LocalDB myDB;
    ImageButton btn_profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new LanguagesWorker().setLangua(this);
        super.onCreate(savedInstanceState);

        ThemesWorker tem=new ThemesWorker();
        tem.setThemes(this);

        setContentView(activity_select_menu);
        user = getIntent().getStringExtra("user");
        myDB = new LocalDB(this, "Chess", null, 1);
        btn_profile = findViewById(R.id.btn_Profile);
        cargarFotoPerfil(user);
    }

    public void onClickCheckers(View v) {
        Intent i = new Intent(this, CheckersActivity.class);
        i.putExtra("user", user);
        setResult(RESULT_OK, i);
        finish();
        startActivity(i);
    }

    public void onClickChess(View v) {
        Intent i = new Intent(this, ChessActivity.class);
        i.putExtra("user", user);
        setResult(RESULT_OK, i);
        finish();
        startActivity(i);
    }

    public void onClickPreferencies(View v) {
        Intent i = new Intent(this, PreferenciesActivity.class);
        i.putExtra("user", user);
        setResult(RESULT_OK, i);
        finish();
        startActivity(i);
    }

    public void onClickLogout(View view) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }

    public void onClickProfile(View view) {
        Intent i = new Intent(this, ProfileActivity.class);

        i.putExtra("user", user);
        startActivityForResult(i, 1);
    }

    public void cargarFotoPerfil(String user) {
        /*Obtiene la foto de la BBDD y la pone en el imageview*/
        Data datos = new Data.Builder().putString("user", user).build();
        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(WorkerGetImage.class).setInputData(datos).build();
        WorkManager.getInstance(SelectMenuActivity.this).getWorkInfoByIdLiveData(otwr.getId()).observe(SelectMenuActivity.this, new Observer<WorkInfo>() {
            @Override
            public void onChanged(WorkInfo workInfo) {
                if (workInfo != null && workInfo.getState().isFinished()) {
                    Boolean resultadoPhp = workInfo.getOutputData().getBoolean("exito", false);
                    System.out.println("RESULTADO INSERT IMAGEN --> " + resultadoPhp);
                    if (resultadoPhp) {
                        byte[] decodificado = myDB.getImage(user);
                        if(decodificado!=null){
                            Bitmap elBitmap = BitmapFactory.decodeByteArray(decodificado, 0, decodificado.length);
                            btn_profile.setImageBitmap(elBitmap);
                        }
                    }
                }
            }
        });
        WorkManager.getInstance(SelectMenuActivity.this).enqueue(otwr);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            byte[] decodificado = myDB.getImage(user);
            if(decodificado!=null){
                Bitmap elBitmap = BitmapFactory.decodeByteArray(decodificado, 0, decodificado.length);
                btn_profile.setImageBitmap(elBitmap);
            }
        }
    }

    public void onClickGamesInCourse(View v) {
        Intent i = new Intent(this, GamesInCourseActivity.class);
        i.putExtra("user", user);
        setResult(RESULT_OK, i);
        finish();
        startActivity(i);
    }
}
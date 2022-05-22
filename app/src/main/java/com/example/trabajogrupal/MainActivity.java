package com.example.trabajogrupal;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText user, pass;
    String userEmail, password;
    Button but;
    TextView text;
    LocalDB myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new LanguagesWorker().setLangua(this);
        super.onCreate(savedInstanceState);
        ThemesWorker tem = new ThemesWorker();
        tem.setThemes(this);
        myDB = new LocalDB(this, "Chess", null, 1);
        pedirpermisosLocalizar();
        getUsers();
        setContentView(R.layout.activity_main);

        /*
        but = findViewById(R.id.button3);
        tem.setButColorBlack(but, this);
        but = findViewById(R.id.button);
        tem.setButColorWhite(but, this);
        but = findViewById(R.id.button5);
        tem.setButColorBlack(but, this);
        text = findViewById(R.id.userSignIn);
        tem.setVTextColorWhite(text, this);
        text = findViewById(R.id.PasswordSignIn);
        tem.setVTextColorWhite(text, this);*/

    }

    @Override
    public void onClick(View view) {

    }

    public void onClickSignIn(View view) {
        user = findViewById(R.id.userSignIn);
        pass = findViewById(R.id.PasswordSignIn);
        userEmail = user.getText().toString();
        password = pass.getText().toString();
        /*
        Intent i = new Intent(getApplicationContext(), selectMenu.class);
        i.putExtra("usuario", user.getText().toString());
        setResult(RESULT_OK, i);
        finish();
        startActivity(i);
        */

        if (userEmail.trim().length() > 0 && password.trim().length() > 0) { //Se comprueba que no son campos vacíos ni espacios en blanco
            Data.Builder datos = new Data.Builder().putString("email", userEmail).putString("password", password);
            OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(WorkerSignIn.class).setInputData(datos.build()).build();
            WorkManager.getInstance(MainActivity.this).getWorkInfoByIdLiveData(otwr.getId()).observe(MainActivity.this, new Observer<WorkInfo>() {
                @Override
                public void onChanged(WorkInfo workInfo) {
                    if (workInfo != null && workInfo.getState().isFinished()) {
                        Boolean resultadoPhp = workInfo.getOutputData().getBoolean("exito", false);
                        System.out.println("RESULTADO LOGIN --> " + resultadoPhp);
                        if (resultadoPhp) {//se logueó correctamente
                            Intent i = new Intent(MainActivity.this, SelectMenuActivity.class);
                            PlayerCatalogue catalogue = PlayerCatalogue.getMyPlayerCatalogue();
                            catalogue.setCurrentUser(userEmail);
                            i.putExtra("user", userEmail);
                            startActivity(i);
                            finish();
                            Player currentUser = catalogue.getPlayer(userEmail);
                            catalogue.setCurrentPlayer(currentUser);
                        } else {
                            Toast.makeText(MainActivity.this, R.string.bademail, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
            WorkManager.getInstance(MainActivity.this).enqueue(otwr);
        } else {
            Toast.makeText(MainActivity.this, R.string.todosCamp, Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickSignUp(View view) {
        Intent i = new Intent(this, SignUpActivity.class);
        startActivityForResult(i, 1);
    }


    public void onClickRanking(View v) {
        Intent i = new Intent(this, ChooseRankingActivity.class);
        startActivity(i);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) { //Se registró correctamente y se le da por logueado automáticamente
            Intent i = new Intent(MainActivity.this, SelectMenuActivity.class);
            String user = getIntent().getStringExtra("email");
            i.putExtra("email", user);
            PlayerCatalogue catalogue = PlayerCatalogue.getMyPlayerCatalogue();
            catalogue.setCurrentUser(user);
            startActivity(i);
            finish();
        }
    }

    public void onClickPref(View v) {
        finish();
        Intent i = new Intent(this, PreferenciesActivity.class);
        startActivity(i);
    }

    public void pedirpermisosLocalizar() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }
    }

    public void getUsers() {
        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(WorkerGetUsersForRanking.class).build();
        WorkManager.getInstance(MainActivity.this).getWorkInfoByIdLiveData(otwr.getId()).observe(MainActivity.this, new Observer<WorkInfo>() {
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
        WorkManager.getInstance(MainActivity.this).enqueue(otwr);
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
        WorkManager.getInstance(MainActivity.this).getWorkInfoByIdLiveData(otwr.getId()).observe(MainActivity.this, new Observer<WorkInfo>() {
            @Override
            public void onChanged(WorkInfo workInfo) {
                if (workInfo != null && workInfo.getState().isFinished()) {
                    Boolean resultadoPhp = workInfo.getOutputData().getBoolean("exito", false);
                    System.out.println("RESULTADO CARGAR IMAGEN --> " + resultadoPhp);
                    if (resultadoPhp) {

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
        WorkManager.getInstance(MainActivity.this).enqueue(otwr);
    }

}
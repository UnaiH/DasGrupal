package com.example.trabajogrupal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.lifecycle.Observer;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText user, pass;
    String userEmail, password;
    Button but;
    TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new Languages().setLangua(this);
        super.onCreate(savedInstanceState);

        Themes tem=new Themes();
        tem.setThemes(this);

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
                            i.putExtra("user",userEmail);
                            startActivity(i);
                            finish();

                        } else {
                            Toast.makeText(MainActivity.this, "Email o contraseña incorrecta", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
            WorkManager.getInstance(MainActivity.this).enqueue(otwr);
        } else {
            Toast.makeText(MainActivity.this, "Por favor, rellena todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickSignUp(View view) {
        Intent i = new Intent(this, SignUpActivity.class);
        startActivityForResult(i, 1);
    }

    public void onClickCheckers(View v) {
        finish();
        Intent i = new Intent(this, CheckersActivity.class);
        startActivity(i);
    }

    public void onClickChess(View v) {
        finish();
        Intent i = new Intent(this, ChessActivity.class);
        startActivity(i);
    }


    public void onClickRanking(View v) {
        finish();
        Intent i = new Intent(this, GlobalRankingActivity.class);
        startActivity(i);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Intent i = new Intent(MainActivity.this, SelectMenuActivity.class);
            String user=getIntent().getStringExtra("email");
            i.putExtra("email",user);
            startActivity(i);
            finish();
        }
    }

    public void onClickPref(View v) {
        finish();
        Intent i = new Intent(this, Preferencies.class);
        startActivity(i);
    }
}
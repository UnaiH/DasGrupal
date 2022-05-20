package com.example.trabajogrupal;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    String email, pass, confirmPass, username;
    EditText emailText, passText, confirmPassText, usernameText;
    Context ctxt;
    Activity activ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new LanguagesWorker().setLangua(this);
        super.onCreate(savedInstanceState);
        ThemesWorker tem=new ThemesWorker();
        tem.setThemes(this);
        setContentView(R.layout.activity_sign_up);
        ctxt=this;
        activ=this;
    }

    @Override
    public void onClick(View view) {
    }

    public void onClickRegis(View view) {
        emailText = findViewById(R.id.signupEmail);
        passText = findViewById(R.id.signupPassword);
        confirmPassText = findViewById(R.id.confirmPassword);
        usernameText = findViewById(R.id.signupUsername);

        email = emailText.getText().toString();
        pass = passText.getText().toString();
        confirmPass = confirmPassText.getText().toString();
        username = usernameText.getText().toString();

        if (isValidEmail(email)) {
            if (isValidPassword(pass, confirmPass)) {
                Data.Builder data = new Data.Builder();
                data.putString("email", email);
                data.putString("password", pass);
                data.putString("user", username);
                OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(WorkerRegister.class)
                        .setInputData(data.build())
                        .build();
                WorkManager.getInstance(this).getWorkInfoByIdLiveData(otwr.getId())
                        .observe(this, new Observer<WorkInfo>() {
                            @Override
                            public void onChanged(WorkInfo workInfo) {
                                //Si se puede iniciar sesión porque devulve true se cambiará la actividad cerrando en la que se encuentra. Si la devolución es null o no es true se mostrará un toast en la interfaz actual.
                                if (workInfo != null && workInfo.getState().isFinished()) {
                                    Boolean resultadoPhp = workInfo.getOutputData().getBoolean("exito", false);
                                    Log.i("TAG", "onChanged: " + resultadoPhp);
                                    if (resultadoPhp) {
                                        new DefineCountryWorker().localizacionBD(email, ctxt,activ);
                                        Intent iBack = new Intent();
                                        setResult(RESULT_OK);
                                        iBack.putExtra("user",email);
                                        finish();
                                        getUsers();
                                    } else {
                                        Toast.makeText(SignUpActivity.this, R.string.otroemail, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
                WorkManager.getInstance(this).enqueue(otwr);
            } else {
                Toast.makeText(SignUpActivity.this, R.string.contrnoval, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(SignUpActivity.this, R.string.correonovalido, Toast.LENGTH_SHORT).show();

        }
    }

    public boolean isValidEmail(String email) {
        boolean valid = false;
        if (email.trim().length() > 0) {
            String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
            if (Pattern.compile(regexPattern).matcher(email).matches()) {
                valid = true;
            }
        }
        return valid;
    }

    public boolean isValidPassword(String pass1, String pass2) {
        boolean valid = false;
        //mínimo ocho caracteres, al menos una letra mayúscula, una letra minúscula, un número y un carácter especial. Ejemplo--> D@ja1920
        String patron = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,20}$";
        if (pass1.trim().length() > 0) {
            if (pass1.equals(pass2)) {
                if (Pattern.compile(patron).matcher(pass1).matches()) {
                    valid = true;
                }
            }
        }
        return valid;
    }
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
    public void getUsers(){
        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(WorkerGetUsersForRanking.class).build();
        WorkManager.getInstance(SignUpActivity.this).getWorkInfoByIdLiveData(otwr.getId()).observe(SignUpActivity.this, new Observer<WorkInfo>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onChanged(WorkInfo workInfo) {
                if (workInfo != null && workInfo.getState().isFinished()) {
                    Boolean resultadoPhp = workInfo.getOutputData().getBoolean("exito", false);
                    System.out.println("RESULTADO --> " + resultadoPhp);
                    if (resultadoPhp) {
                        String[] resultados = workInfo.getOutputData().getStringArray("datosUsuario");
                        PlayerCatalogue catalogue = PlayerCatalogue.getMyPlayerCatalogue();
                        for (int i = 0; i < resultados.length; i++) {
                            if (!resultados[i].equals("false")) {
                                Player unPlayer = getDatosJugador(resultados[i]);
                                if(unPlayer.getEmail().equals(email)){
                                    catalogue.setCurrentPlayer(unPlayer);
                                }
                            }
                        }



                    }
                }
            }
        });
        WorkManager.getInstance(SignUpActivity.this).enqueue(otwr);
    }
    public Player getDatosJugador(String s) {
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

        Player jugador = new Player(username,pais,email,eloCheckers,eloChess);

        return  jugador;
    }
}
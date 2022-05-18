package com.example.trabajogrupal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    String email, pass, confirmPass, username;
    EditText emailText, passText, confirmPassText, usernameText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new Languages().setLangua(this);
        super.onCreate(savedInstanceState);
        Themes tem=new Themes();
        tem.setThemes(this);
        setContentView(R.layout.activity_sign_up);
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
                                        Intent iBack = new Intent();
                                        setResult(RESULT_OK);
                                        iBack.putExtra("user",email);
                                        finish();

                                    } else {
                                        Toast.makeText(SignUpActivity.this, "El email ya está en uso, por favor pruebe con otro", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
                WorkManager.getInstance(this).enqueue(otwr);
            } else {
                Toast.makeText(SignUpActivity.this, "Contraseña incorrecta. Tu contraseña debe tener mínimo ocho caracteres, al menos una letra mayúscula, una letra minúscula, un número y un carácter especial.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(SignUpActivity.this, "Correo electrónico inválido", Toast.LENGTH_SHORT).show();

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
}
package com.example.trabajogrupal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    @Override
    public void onClick(View view) {
        finish();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
    public void onClickRegis(View view){
        EditText user=findViewById(R.id.userSignIn);
        EditText pass=findViewById(R.id.PasswordSignIn);
        Data.Builder data = new Data.Builder();
        data.putString("usuario",user.getText().toString());
        data.putString("contrasena",pass.getText().toString());
        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(PHPRegistr.class)
                .setInputData(data.build())
                .build();
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(otwr.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo)
                    {
                        //Si se puede iniciar sesión porque devulve true se cambiará la actividad cerrando en la que se encuentra. Si la devolución es null o no es true se mostrará un toast en la interfaz actual.
                        if(workInfo != null && workInfo.getState().isFinished())
                        {
                            String inicio = workInfo.getOutputData().getString("result");
                            Log.i("TAG", "onChanged: "+inicio);
                            if (inicio!=null) {
                                if (inicio.equals("true")) {
                                    Intent i = new Intent(getApplicationContext(), SignUpActivity.class);
                                    i.putExtra("usuario", user.getText().toString());
                                    setResult(RESULT_OK, i);
                                    finish();
                                    startActivity(i);
                                } else {
                                }
                            }
                            else {
                            }
                        }
                    }
                });
        WorkManager.getInstance(this).enqueue(otwr);
    }
}
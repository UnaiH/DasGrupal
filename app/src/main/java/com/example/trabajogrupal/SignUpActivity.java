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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    String email, pass, confirmPass, username;
    EditText emailText, passText, confirmPassText, usernameText;
    Context ctxt;
    Activity activ;
    byte[] fotoTransformada;
    LocalDB myDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new LanguagesWorker().setLangua(this);
        super.onCreate(savedInstanceState);
        ThemesWorker tem = new ThemesWorker();
        tem.setThemes(this);
        setContentView(R.layout.activity_sign_up);
        ctxt = this;
        activ = this;
        myDB = new LocalDB(this, "Chess", null, 1);
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
                                        new DefineCountryWorker().localizacionBD(email, ctxt, activ);
                                        Intent iBack = new Intent();
                                        setResult(RESULT_OK);
                                        iBack.putExtra("user", email);
                                        finish();
                                        PlayerCatalogue catalogue = PlayerCatalogue.getMyPlayerCatalogue();
                                        Player currentPlayer = catalogue.getPlayer(email);
                                        compressFoto();
                                        if (fotoTransformada != null) {
                                            if (myDB.getImage(email) != null) {
                                                myDB.updateImage(email, fotoTransformada);
                                            } else {
                                                myDB.insertImage(email, fotoTransformada);
                                            }
                                            insertarFotoPerfil(email, fotoTransformada);
                                        }
                                        catalogue.setCurrentPlayer(currentPlayer);
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
    public void compressFoto() {
        String uri = "@drawable/avatar1";
        int imageResource = getResources().getIdentifier(uri, null, getPackageName());
        Bitmap bitMapFoto = BitmapFactory.decodeResource(getResources(), imageResource);

        int anchoDestino = 528;
        int altoDestino = 591;
        int anchoImagen = bitMapFoto.getWidth();
        int altoImagen = bitMapFoto.getHeight();
        float ratioImagen = (float) anchoImagen / (float) altoImagen;
        float ratioDestino = (float) anchoDestino / (float) altoDestino;
        int anchoFinal = anchoDestino;
        int altoFinal = altoDestino;
        if (ratioDestino > ratioImagen) {
            anchoFinal = (int) ((float) altoDestino * ratioImagen);
        } else {
            altoFinal = (int) ((float) anchoDestino / ratioImagen);
        }
        Bitmap bitmapRedimensionado = Bitmap.createScaledBitmap(bitMapFoto, anchoFinal, altoFinal, true);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmapRedimensionado.compress(Bitmap.CompressFormat.PNG, 100, stream);

        fotoTransformada = stream.toByteArray();
    }
    public boolean isValidPassword(String pass1, String pass2) {
        boolean valid = false;
        //mínimo ocho caracteres, al menos una letra mayúscula, una letra minúscula, un número y un carácter especial. Ejemplo--> D@ja1920
        String patron = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%.,:;_*+&|?!€¿¡]).{8,20}$";
        if (pass1.trim().length() > 0) {
            if (pass1.equals(pass2)) {
                if (Pattern.compile(patron).matcher(pass1).matches()) {
                    valid = true;
                }
            }
        }
        return valid;
    }
    public void insertarFotoPerfil(String user, byte[] fotoTransformada) {
        /*Subir la foto a la BBDD remota */
        Uri.Builder builder = new Uri.Builder();
        builder.appendQueryParameter("user", user);
        String foto64 = Base64.encodeToString(fotoTransformada, Base64.DEFAULT);
        builder.appendQueryParameter("image", foto64);
        Data datos = new Data.Builder().putString("user", user).build();
        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(WorkerInsertImage.class).setInputData(datos).build();
        WorkManager.getInstance(SignUpActivity.this).getWorkInfoByIdLiveData(otwr.getId()).observe(SignUpActivity.this, new Observer<WorkInfo>() {
            @Override
            public void onChanged(WorkInfo workInfo) {
                if (workInfo != null && workInfo.getState().isFinished()) {
                    Boolean resultadoPhp = workInfo.getOutputData().getBoolean("exito", false);
                    System.out.println("RESULTADO INSERT IMAGEN --> " + resultadoPhp);
                    if (resultadoPhp) {
                        Intent i = getIntent();
                        startActivity(i);
                        finish();
                    }
                }
            }
        });
        WorkManager.getInstance(SignUpActivity.this).enqueue(otwr);

    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

}
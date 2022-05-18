package com.example.trabajogrupal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class ProfileActivity extends AppCompatActivity {
    ImageView image;
    TextView userTextView;
    Button btn_gallery, btn_camera, btn_select;
    LocalDB myDB;
    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new LanguagesWorker().setLangua(this);
        super.onCreate(savedInstanceState);

        ThemesWorker tem = new ThemesWorker();
        tem.setThemes(this);

        setContentView(R.layout.activity_profile);
        userTextView = findViewById(R.id.profile_username);
        user = getIntent().getStringExtra("user");
        userTextView.setText(user);
        image = findViewById(R.id.profileImage);
        btn_camera = findViewById(R.id.btn_profileCamera);
        btn_gallery = findViewById(R.id.btn_profileGallery);
        btn_select = findViewById(R.id.btn_profileSelect);
        myDB = new LocalDB(this, "Chess", null, 1);


        cargarFotoPerfil(user);


        btn_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(iGallery, 10);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == RESULT_OK) {
            Uri chosenImage = data.getData();
            myDB.clearImagen(user);
            InputStream inputStream = null;
            try {
                inputStream = getContentResolver().openInputStream(chosenImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Bitmap foto = BitmapFactory.decodeStream(inputStream);

            int anchoDestino = image.getWidth();
            int altoDestino = image.getHeight();
            int anchoImagen = foto.getWidth();
            int altoImagen = foto.getHeight();
            float ratioImagen = (float) anchoImagen / (float) altoImagen;
            float ratioDestino = (float) anchoDestino / (float) altoDestino;
            int anchoFinal = anchoDestino;
            int altoFinal = altoDestino;
            if (ratioDestino > ratioImagen) {
                anchoFinal = (int) ((float) altoDestino * ratioImagen);
            } else {
                altoFinal = (int) ((float) anchoDestino / ratioImagen);
            }
            Bitmap bitmapRedimensionado = Bitmap.createScaledBitmap(foto, anchoFinal, altoFinal, true);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmapRedimensionado.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] fotoTransformada = stream.toByteArray();
            myDB.insertImage(user, fotoTransformada);
            image.setImageBitmap(foto);

            /*Subir la foto a la BBDD remota */
            Uri.Builder builder = new Uri.Builder();
            builder.appendQueryParameter("user", user);
            String foto64 = Base64.encodeToString(fotoTransformada, Base64.DEFAULT);
            builder.appendQueryParameter("image", foto64);
            Data datos = new Data.Builder().putString("user", user).build();
            OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(WorkerInsertImage.class).setInputData(datos).build();
            WorkManager.getInstance(ProfileActivity.this).getWorkInfoByIdLiveData(otwr.getId()).observe(ProfileActivity.this, new Observer<WorkInfo>() {
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
            WorkManager.getInstance(ProfileActivity.this).enqueue(otwr);

            setResult(RESULT_OK);
            finish();
        }
    }

    public void cargarFotoPerfil(String user) {
        /*Obtiene la foto de la BBDD y la pone en el imageview*/
        Data datos = new Data.Builder().putString("user", user).build();
        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(WorkerGetImage.class).setInputData(datos).build();
        WorkManager.getInstance(ProfileActivity.this).getWorkInfoByIdLiveData(otwr.getId()).observe(ProfileActivity.this, new Observer<WorkInfo>() {
            @Override
            public void onChanged(WorkInfo workInfo) {
                if (workInfo != null && workInfo.getState().isFinished()) {
                    Boolean resultadoPhp = workInfo.getOutputData().getBoolean("exito", false);
                    System.out.println("RESULTADO INSERT IMAGEN --> " + resultadoPhp);
                    if (resultadoPhp) {

                        byte[] decodificado = myDB.getImage(user);
                        if(decodificado!=null) {
                            Bitmap elBitmap = BitmapFactory.decodeByteArray(decodificado, 0, decodificado.length);
                            image.setImageBitmap(elBitmap);
                        }
                    }
                }
            }
        });
        WorkManager.getInstance(ProfileActivity.this).enqueue(otwr);
    }
}
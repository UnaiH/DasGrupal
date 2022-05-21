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
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class ProfileActivity extends AppCompatActivity {
    ImageView image;
    TextView userTextView;
    Button btn_gallery, btn_camera, btn_save;
    Spinner dropdown;
    LocalDB myDB;
    String user;
    byte[] fotoTransformada;
    String[] itemsDropdown;
    Bitmap bitMapFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new LanguagesWorker().setLangua(this);
        super.onCreate(savedInstanceState);

        ThemesWorker tem = new ThemesWorker();
        tem.setThemes(this);
        myDB = new LocalDB(this, "Chess", null, 1);
        setContentView(R.layout.activity_profile);
        userTextView = findViewById(R.id.profile_username);
        PlayerCatalogue catalogue = PlayerCatalogue.getMyPlayerCatalogue();
        user = catalogue.getCurrentUser();
        userTextView.setText(user);
        image = findViewById(R.id.profileImage);
        btn_camera = findViewById(R.id.btn_profileCamera);
        btn_gallery = findViewById(R.id.btn_profileGallery);
        btn_save = findViewById(R.id.btn_profileGuardar);
        Log.i("PruebaFoto","Imagen-> "+catalogue.getCurrentUser()+" -> "+catalogue.getPlayer(user).getImage());
        /*Dropdown para seleccionar 4 avatares que ofrece la app*/
        dropdown = findViewById(R.id.spinnerSelect);
        itemsDropdown = new String[]{"Select","Avatar 1", "Avatar 2", "Avatar 3", "Avatar 4"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, itemsDropdown);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String avatar = itemsDropdown[i].toLowerCase();
                String uri = null;
                switch (avatar) {
                    case "avatar 1":
                        uri = "@drawable/avatar1";
                        break;
                    case "avatar 2":
                        uri = "@drawable/avatar2";
                        break;
                    case "avatar 3":
                        uri = "@drawable/avatar3";
                        break;
                    case "avatar 4":
                        uri = "@drawable/avatar4";
                        break;
                }
                if (uri != null) {
                    int imageResource = getResources().getIdentifier(uri, null, getPackageName());
                    bitMapFoto = BitmapFactory.decodeResource(getResources(), imageResource);
                    compressFoto();
                    Drawable res = getResources().getDrawable(imageResource);
                    image.setImageDrawable(res);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        cargarFotoPerfil(user);

        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent elIntentFoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(elIntentFoto, 777);
            }
        });

        btn_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(iGallery, 10);
            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (fotoTransformada != null) {
                    if (myDB.getImage(user) != null) {
                        myDB.updateImage(user, fotoTransformada);
                    } else {
                        myDB.insertImage(user, fotoTransformada);
                    }
                    insertarFotoPerfil(user, fotoTransformada);
                }

                setResult(RESULT_OK);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == RESULT_OK) {
            Uri chosenImage = data.getData();
            InputStream inputStream = null;
            try {
                inputStream = getContentResolver().openInputStream(chosenImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            bitMapFoto = BitmapFactory.decodeStream(inputStream);

            compressFoto();

            image.setImageBitmap(bitMapFoto);


        }
        if (requestCode == 777) { //Recogemos la miniatura, la almacenamos en la BBDD del servidor
            if (resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                bitMapFoto = (Bitmap) extras.get("data");
                compressFoto();
                image.setImageBitmap(bitMapFoto);

            }

        }
    }

    public void compressFoto() {
        int anchoDestino = image.getWidth();
        int altoDestino = image.getHeight();
        Log.i("compress","Width-> "+anchoDestino);
        Log.i("compress","Height-> "+altoDestino);
        Log.i("compress","Height-> "+altoDestino);
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

    public void insertarFotoPerfil(String user, byte[] fotoTransformada) {
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
                    System.out.println("RESULTADO CARGAR IMAGEN --> " + resultadoPhp);
                    if (resultadoPhp) {

                        byte[] decodificado = myDB.getImage(user);
                        if (decodificado != null) {
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
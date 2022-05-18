package com.example.trabajogrupal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
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
        new Languages().setLangua(this);
        super.onCreate(savedInstanceState);

        Themes tem=new Themes();
        tem.setThemes(this);

        setContentView(R.layout.activity_profile);
        userTextView = findViewById(R.id.profile_username);
        user = getIntent().getStringExtra("user");
        image = findViewById(R.id.profileImage);
        btn_camera = findViewById(R.id.btn_profileCamera);
        btn_gallery = findViewById(R.id.btn_profileGallery);
        btn_select = findViewById(R.id.btn_profileSelect);
        myDB = new LocalDB(this, "Chess", null, 1);


        byte[] currentImage = myDB.getImage(user);
        Log.i("TAG", "currentImage: " + currentImage);
        if (currentImage != null) {
            ByteArrayInputStream imageStream = new ByteArrayInputStream(currentImage);
            Bitmap theImage = BitmapFactory.decodeStream(imageStream);
            image.setImageBitmap(theImage);
        }


        btn_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(iGallery, 1);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
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
            Bitmap bitmapRedimensionado = Bitmap.createScaledBitmap(foto,anchoFinal,altoFinal,true);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmapRedimensionado.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] fotoTransformada = stream.toByteArray();
            myDB.insertImage(user, fotoTransformada);
            image.setImageBitmap(foto);

            setResult(RESULT_OK);
            finish();
        }
    }
}
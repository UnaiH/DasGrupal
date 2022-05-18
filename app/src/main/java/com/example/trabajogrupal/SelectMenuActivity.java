package com.example.trabajogrupal;

import static com.example.trabajogrupal.R.layout.activity_select_menu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
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
        new Languages().setLangua(this);
        super.onCreate(savedInstanceState);

        Themes tem=new Themes();
        tem.setThemes(this);

        setContentView(activity_select_menu);
        user = getIntent().getStringExtra("user");
        myDB = new LocalDB(this, "Chess", null, 1);
        btn_profile = findViewById(R.id.btn_Profile);
        cargarFotoPerfil();
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
        Intent i = new Intent(this, Preferencies.class);
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

        i.putExtra("user",user);
        startActivityForResult(i, 1);
    }
    public void cargarFotoPerfil(){
        byte[] currentImage = myDB.getImage(user);
        if (currentImage != null) {
            ByteArrayInputStream imageStream = new ByteArrayInputStream(currentImage);
            Bitmap theImage = BitmapFactory.decodeStream(imageStream);
            btn_profile.setImageBitmap(theImage);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            cargarFotoPerfil();
        }
    }
}
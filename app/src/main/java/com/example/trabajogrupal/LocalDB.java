package com.example.trabajogrupal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

public class LocalDB extends SQLiteOpenHelper {


    public LocalDB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE Image ('user' VARCHAR(255) PRIMARY KEY NOT NULL, 'image' blob)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public byte[] getImage(String user){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM Image WHERE user=?";
        Cursor c = db.rawQuery(query, new String[]{user});
        byte[] imagen = null;
        while (c.moveToNext()) {
            int i = c.getColumnIndex("image");
            imagen = c.getBlob(i);
        }
        c.close();
        return imagen;
    }
    public void updateImage(String user, byte[] foto){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("image", foto);
        db.update("Image", values, "user=?", new String[]{user});
        db.close();
        System.out.println("Update hecho");
    }
    public void clearImagen(String user){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Image","user=?",new String[]{String.valueOf(user)});
        db.close();
    }

    public void insertImage(String user,byte[] foto){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "INSERT INTO Image VALUES (?,?)";
        SQLiteStatement sqLiteStatement = db.compileStatement(sql);
        sqLiteStatement.clearBindings();
        sqLiteStatement.bindString(1,user);
        sqLiteStatement.bindBlob(2,foto);
        sqLiteStatement.executeInsert();
        db.close();
    }
}

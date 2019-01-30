package com.example.familia.basqllite1;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
//Esta clase es la base de datos SQLite, aqui creamos las tablas y atributos, relaciones etc.
public class SQLiteAdmin extends SQLiteOpenHelper{
    public SQLiteAdmin(Context context,  String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase dbarticles) {
        dbarticles.execSQL("create table articulos(codigo int not null primary key, descripcion text not null, precio real not null)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //Debemos sobreescribir los metods Oncreate y ONupdate


}

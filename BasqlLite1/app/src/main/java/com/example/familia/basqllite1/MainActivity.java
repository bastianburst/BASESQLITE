package com.example.familia.basqllite1;

import android.animation.TimeAnimator;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText codigo, descripcion, precio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        codigo = (EditText)findViewById(R.id.editcodigo);
        descripcion = (EditText)findViewById(R.id.editdescripcion);
        precio = (EditText)findViewById(R.id.editprecio);
        Toast.makeText(this,"Bienvenido Sr. Sebastián", Toast.LENGTH_SHORT).show();

    }
    //Método para registrar productos
    public void Registrar(View view){
        //Creamos un objeto de la clase SQLiteAdmin que creamos antes
        SQLiteAdmin admons = new SQLiteAdmin(this, "administracion", null, 1);
        //Creamos un objeto de la clase SQLiteDatabase
        //Ahora usamos getWitableDatabases para abrir la base de datos en modo lectura y escritura
        SQLiteDatabase BasedeDatos = admons.getWritableDatabase();

        String code = codigo.getText().toString();
        String descri = descripcion.getText().toString();
        String prize = precio.getText().toString();


        if(!code.isEmpty() && !descri.isEmpty() && !prize.isEmpty()){
            //la clase Contentvalues es para armar un objeto json
            ContentValues registro = new ContentValues();
            registro.put("codigo", code);
            registro.put("descripcion", descri);
            registro.put("precio", prize);

            Cursor fila = BasedeDatos.rawQuery("select codigo from articulos where codigo ="+ code, null);
            if(fila.moveToFirst()){
              Toast.makeText(this,"Este código de producto ya existe", Toast.LENGTH_SHORT).show();
            }else{
                //Hacemos el insert a la tabla articulos
                BasedeDatos.insert("articulos", null, registro);
                BasedeDatos.close();
                codigo.setText("");
                descripcion.setText("");
                precio.setText("");
                Toast.makeText(this, "Articulo registrado", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Debe llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    //Método para consultar articulos
    public void cons(View view){
        SQLiteAdmin admons = new SQLiteAdmin(this, "administracion", null, 1);
        SQLiteDatabase BasedeDatos = admons.getWritableDatabase();

        String code = codigo.getText().toString();
        if(!code.isEmpty()) {
            //En esta parte articulamos la consulta SQL con Cursor
            //esto es una consulta para un solo registro
            Cursor fila = BasedeDatos.rawQuery("select descripcion, precio from articulos where codigo = "+ code, null);
            if(fila.moveToFirst()){
                //Aqui asignamos los valores valiendonos de los indices del array
                descripcion.setText(fila.getString(0));
                precio.setText(fila.getString(1));
                BasedeDatos.close();
                Toast.makeText(this,"Registro encontrado", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Ese articulo no existe", Toast.LENGTH_SHORT).show();
            }
        } else{
            Toast.makeText(this,"Debes introducir el código para buscar", Toast.LENGTH_SHORT).show();

        }
    }

    //Método para eliminar un producto
    public void Eliminar(View view){
        SQLiteAdmin admons = new SQLiteAdmin(this,"administracion", null, 1);
        SQLiteDatabase BasedeDatos = admons.getWritableDatabase();
        String code = codigo.getText().toString();
        if(!code.isEmpty()) {
            //En esta parte articulamos la consulta SQL con Cursor
            //esto es una consulta para un solo registro
            int cantidad = BasedeDatos.delete("articulos", "codigo=" + code, null);
            BasedeDatos.close();
            codigo.setText("");
            descripcion.setText("");
            precio.setText("");
            if(cantidad == 1){
                Toast.makeText(this,"Registro eliminado", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Ese articulo no existe", Toast.LENGTH_SHORT).show();
            }
        } else{
            Toast.makeText(this,"Debes introducir el código para buscar", Toast.LENGTH_SHORT).show();

        }


    }

    //Método para actualizar un producto
    public void Modificar(View view){
        SQLiteAdmin admons = new SQLiteAdmin(this,"administracion", null,1);
        SQLiteDatabase BasedeDatos = admons.getWritableDatabase();

        String code = codigo.getText().toString();
        String descri = descripcion.getText().toString();
        String prize = precio.getText().toString();

        if(!code.isEmpty() && !descri.isEmpty() && !prize.isEmpty()){
            ContentValues registro = new ContentValues();
            registro.put("codigo", code);
            registro.put("descripcion", descri);
            registro.put("precio", prize);

            int cantidad = BasedeDatos.update("articulos", registro, "codigo=" + code, null);
            BasedeDatos.close();
            codigo.setText("");
            descripcion.setText("");
            precio.setText("");
            if(cantidad == 1){
                Toast.makeText(this,"Registro modificado", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Ese articulo no existe", Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(this, "Debe llenar todos  los campos", Toast.LENGTH_SHORT).show();
        }
    }

}

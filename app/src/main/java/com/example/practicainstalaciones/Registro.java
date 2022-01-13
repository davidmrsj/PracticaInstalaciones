package com.example.practicainstalaciones;

import static com.example.practicainstalaciones.MainActivity.db;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Registro extends AppCompatActivity {

    public static final String EXTRA_REGISTRO = "com.example.android.practicainstalaciones.extra.REGISTRO";
    EditText txtUsuario;
    EditText txtContraseña;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        txtUsuario = (EditText) findViewById(R.id.editTextRegistro);
        txtContraseña = (EditText) findViewById(R.id.editTextPassRegistro);

    }

    public void registrarse(View view){
        String usuario = txtUsuario.getText().toString();
        Cursor c = db.rawQuery("SELECT * FROM usuarios where usuario='"+usuario+"'", null);
        if(c.getCount()<1) {
            db.execSQL("INSERT INTO usuarios VALUES('" + usuario + "','" + txtContraseña.getText().toString() + "')");
            Intent intentRegistro = new Intent();
            intentRegistro.putExtra(EXTRA_REGISTRO, usuario);
            setResult(RESULT_OK, intentRegistro);
            finish();
        }else{
            Toast toast1 = Toast.makeText(this, "Este usuario ya está registrado", Toast.LENGTH_LONG);
            toast1.show();
            txtUsuario.setText("");
            txtContraseña.setText("");
        }

    }
}

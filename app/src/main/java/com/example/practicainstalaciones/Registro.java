package com.example.practicainstalaciones;

import static com.example.practicainstalaciones.MainActivity.db;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
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
        String usuario = txtUsuario.getText().toString().toLowerCase();
        String contra = txtContraseña.getText().toString();
        Log.e("Contraseña", contra);
        String regex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
        if(contra.matches(regex)){
            try{
                Cursor c = db.rawQuery("SELECT * FROM usuarios where usuario='"+usuario+"'", null);
                if(c.getCount()<1) {
                    db.execSQL("INSERT INTO usuarios VALUES('" + usuario + "','" + txtContraseña.getText().toString() + "')");
                    Intent intentRegistro = new Intent();
                    intentRegistro.putExtra(EXTRA_REGISTRO, usuario);
                    setResult(RESULT_OK, intentRegistro);
                    Toast.makeText(getApplicationContext(), "Se ha registrado correctamente", Toast.LENGTH_LONG).show();
                    finish();
                }else{
                    Toast toast1 = Toast.makeText(this, "Este usuario ya está registrado", Toast.LENGTH_LONG);
                    toast1.show();
                    txtUsuario.setText("");
                    txtContraseña.setText("");
                }
            }catch (Exception e){
                Log.e("Resgistro", e.getMessage());
            }
        }else{
            Toast.makeText(getApplicationContext(), "La contraseña debe tener almenos 8 caracteres, una mayuscula y almenos un numero", Toast.LENGTH_LONG).show();
        }

    }
}

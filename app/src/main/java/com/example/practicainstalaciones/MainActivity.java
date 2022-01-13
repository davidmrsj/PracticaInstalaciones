package com.example.practicainstalaciones;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static SQLiteDatabase db;
    private EditText txtusuario;
    private EditText txtContra;
    private EditText txtUsuarioLog;
    private EditText txtContraLog;
    private TextView textoInfor;

    ActivityResultLauncher secondActivity = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode()== Activity.RESULT_OK){
                        Intent data = result.getData();
                        String infoUsuario = null;

                        if (data!=null){
                            infoUsuario = data.getStringExtra(Registro.EXTRA_REGISTRO);
                        }
                        textoInfor.setText("Se ha registrado correctamente el usuario "+infoUsuario);
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtusuario = (EditText) findViewById(R.id.editTextRegistro);
        txtContra = (EditText) findViewById(R.id.editTextPassRegistro);
        txtUsuarioLog = (EditText) findViewById(R.id.editTextLogin);
        txtContraLog = (EditText) findViewById(R.id.editTextPassLogin);
        textoInfor = (TextView) findViewById(R.id.textoInfo);

        db = openOrCreateDatabase("Reservas", Context.MODE_PRIVATE, null);

        db.execSQL("Create table if not exists usuarios(usuario varchar,contraseña varchar, primary key(usuario))");
        db.execSQL("create table if not exists reservas(fechaInicio date primary key, fechaFinal date, usuario varchar, foreign key(usuario) references usuarios(usuario));");


    }

    public void accederRegistro(View view){
        Intent intent = new Intent(this, Registro.class);
        secondActivity.launch(intent);
    }

    public void accederLogin(View view){

    }
}
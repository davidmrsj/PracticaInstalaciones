package com.example.practicainstalaciones;

import static com.example.practicainstalaciones.MainActivity.editor;
import static com.example.practicainstalaciones.MainActivity.preferences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Reservas extends ClaseMenu {

    TextView userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservas);
        userName = findViewById(R.id.textView7);

        String user = preferences.getString("user", "");
        userName.setText(user);

    }

    @Override
    protected void onStart() {
        super.onStart();
        String user = preferences.getString("user", "");
        if(user==""){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
    }

    public void anadirReserva(View view){
        Intent intent = new Intent(getApplicationContext(), AnadirReserva.class);
        startActivity(intent);
    }

    public void consultaReserva(View view){
        Intent intent = new Intent(getApplicationContext(), ConsultaReserva.class);
        startActivity(intent);
    }

    public void eliminarReserva(View view){
        Intent intent = new Intent(getApplicationContext(), EliminarRegistro.class);
        startActivity(intent);
    }

    public void cerrarSesion(View view){
        userName.setText("");
        editor.clear();
        editor.commit();

        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }
}
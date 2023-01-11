package com.example.practicainstalaciones;

import static com.example.practicainstalaciones.MainActivity.db;
import static com.example.practicainstalaciones.MainActivity.preferences;
import static com.example.practicainstalaciones.R.id.listaHorasDisponibles;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HorasDisponibles extends ClaseMenu {

    ListView listaHoras;
    String[] horasOcupadas;
    String instalacion;
    String fecha;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horas_disponibles);
        listaHoras=(ListView) findViewById(listaHorasDisponibles);

        getDataIntent();
        setHorasOcupadas();
        mostrarHorasDisponibles();
    }

    @Override
    protected void onStart() {
        super.onStart();
        String user = preferences.getString("user", "");
        if(user==""){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }    }

    public void getDataIntent(){
        Bundle bundle = this.getIntent().getExtras();
        if(bundle!=null){
            instalacion = bundle.getString("Instalacion");
            fecha = bundle.getString("Fecha");
        }
    }

    public void setHorasOcupadas(){
        try {
            Cursor cursor = db.rawQuery("select horainicio, horafinal from reserva where instalacion='"+instalacion+"' and fechainicio= Date('"+fecha+"') order by horainicio asc;", null);
            horasOcupadas = new String[11];
            int contador=0;
            while(cursor.moveToNext()){
                for(int i=cursor.getInt(0); i<cursor.getInt(1); i++){

                        horasOcupadas[contador] = String.valueOf(i);
                        contador++;
                        Log.e("Horas ocupadas", "Horas que estan ocupadas" + String.valueOf(i));

                }
            }
        }catch(Exception e){
            Toast.makeText(getApplicationContext(), "Error al listar horas disponibles", Toast.LENGTH_LONG).show();
        }
    }

    public void mostrarHorasDisponibles(){
        ArrayAdapter<String> adapter;
        List<String> lista = new ArrayList<String>();

        int contador=0;
        for(int i = 9; i<20; i++){
            if(horaCogida(i)){
            }else{
                lista.add("Hora disponible de: "+i+" a "+(i+1));
            }
        }

        adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.lista_txt, lista);
        listaHoras.setAdapter(adapter);
    }

    public boolean horaCogida(int hora){
        for(int i=0; i<horasOcupadas.length;i++){
            if(horasOcupadas[i]!=null) {
                if (horasOcupadas[i].contains(String.valueOf(hora))) {
                    Log.e("Devolucion hora", "Hora coincide" + hora);
                    return true;
                }
            }
        }
        return false;
    }
}
package com.example.practicainstalaciones;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ConsultaReserva extends AppCompatActivity {

    ListView listaReserva;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_reserva);
        listaReserva = (ListView) findViewById(R.id.listaConsulta);
        listar();
    }

    public void listar(){
        ArrayAdapter<String> adapter;
        List<String> lista = new ArrayList<String>();

        Cursor cursor = MainActivity.db.rawQuery("Select * from reserva where usuario = '"+MainActivity.usuarioLogeado+"'", null);

        if(cursor.getCount()==0){
            lista.add("No hay registros");
        }else{
            while (cursor.moveToNext()){
                Log.e("Reservass", cursor.getString(0));
                lista.add("Nº registro: "+cursor.getString(0)+" Hora de reserva: "+cursor.getString(1));
            }
        }
        adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.lista_txt, lista);
        listaReserva.setAdapter(adapter);
    }
}
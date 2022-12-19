package com.example.practicainstalaciones;

import static com.example.practicainstalaciones.MainActivity.preferences;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ConsultaReserva extends AppCompatActivity implements AdapterView.OnItemClickListener {

    Reserva reserva;
    ArrayList<Reserva> lista;
    ListView listaReserva;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_reserva);
        listaReserva = (ListView) findViewById(R.id.listaConsulta);
        listar();
        listaReserva.setOnItemClickListener(this);
    }

    public void listar(){
        ArrayAdapter<Reserva> adapter;
        lista = new ArrayList<Reserva>();

        String usuario=preferences.getString("user", "");

        Cursor cursor = MainActivity.db.rawQuery("Select * from reserva where usuario = '"+usuario+"'", null);

        if(cursor.getCount()==0){
            lista.add(new Reserva("No hay registros", "", "", "", ""));
        }else{
            while (cursor.moveToNext()){
                Log.e("Reservass", cursor.getString(0));
                reserva = new Reserva(cursor.getString(3), cursor.getString(1), cursor.getString(2), usuario, cursor.getString(0));
                lista.add(reserva);
                //lista.add("Instalacion: "+cursor.getString(3)+" Hora de reserva: "+cursor.getString(0)+" horas:"+cursor.getString(1)+"-"+cursor.getString(2));
            }
        }
        adapter = new ArrayAdapter<Reserva>(getApplicationContext(), R.layout.lista_txt, lista);
        listaReserva.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
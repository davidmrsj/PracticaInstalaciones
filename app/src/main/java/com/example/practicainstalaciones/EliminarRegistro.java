package com.example.practicainstalaciones;

import static com.example.practicainstalaciones.MainActivity.preferences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.practicainstalaciones.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class EliminarRegistro extends ClaseMenu {

    Spinner registrosSpin;
    int idRes;
    String fechaRes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_registro);
        registrosSpin = (Spinner) findViewById(R.id.spinner);
        mostrarReg();
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

    public void mostrarReg(){
        List<Reserva> list = new ArrayList<Reserva>();
        ArrayAdapter<Reserva> adapter;
        String user = preferences.getString("user", "");
        try{
            Cursor cursor = MainActivity.db.rawQuery("Select * from reserva where usuario = '"+user+"'", null);
            while (cursor.moveToNext()){
                Reserva r = new Reserva(cursor.getString(3), cursor.getString(1), cursor.getString(2), user, cursor.getString(0));
                list.add(r);
            }
        }catch(Exception e){
            Log.e("Error spinner eliminar", e.getMessage());
        }

        adapter = new ArrayAdapter<Reserva>(getApplicationContext(), R.layout.lista_txt, list);
        registrosSpin.setAdapter(adapter);
    }
    public void eliminarReg(View view){
        Reserva res = (Reserva)registrosSpin.getSelectedItem();
        try{
            MainActivity.db.execSQL("Delete from reserva where instalacion='"+res.getInstalacion()+"' and fechaInicio='"+res.getFechaReserva()+"' and horaInicio="+res.getHoraInicio());
            Toast.makeText(getApplicationContext(), "Reserva cancelada", Toast.LENGTH_SHORT).show();

        }catch(Exception e){
            Toast.makeText(getApplicationContext(), "Error al cancelar reserva", Toast.LENGTH_SHORT).show();
            Log.e("Eliminar", e.getMessage());
        }
    }
}
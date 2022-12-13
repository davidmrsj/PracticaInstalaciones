package com.example.practicainstalaciones;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.practicainstalaciones.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class EliminarRegistro extends AppCompatActivity {

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

    public void mostrarReg(){
        List<Reserva> list = new ArrayList<Reserva>();
        ArrayAdapter<Reserva> adapter;
        Cursor c = MainActivity.db.rawQuery("Select * from reserva where usuario = '"+MainActivity.usuarioLogeado+"'", null);
        while (c.moveToNext()){
            Reserva r = new Reserva(c.getInt(0), c.getString(1));
            Toast.makeText(getApplicationContext(), r.getId()+r.getFechaReserva(), Toast.LENGTH_LONG).show();
            list.add(r);
        }
        adapter = new ArrayAdapter<Reserva>(getApplicationContext(), R.layout.lista_txt, list);
        registrosSpin.setAdapter(adapter);
    }
    public void eliminarReg(View view){
        Reserva res = (Reserva)registrosSpin.getSelectedItem();
        MainActivity.db.execSQL("Delete from reserva where id="+res.getId());
    }
}
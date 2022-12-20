package com.example.practicainstalaciones;

import static com.example.practicainstalaciones.MainActivity.db;
import static com.example.practicainstalaciones.MainActivity.preferences;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AnadirReserva extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Button btnFecha;
    Button btnHora;
    String fecha;
    String [] nameInstalaciones, tipoInstalaciones;
    int hora;
    int mes;
    int dia;
    int ano;
    int minuto;
    TextView txtfech;
    Calendar car;
    Button btnAnadir;
    Spinner spinner, spinnerInstalaciones, spinnerHoraInicio, spinnerTipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_reserva);

        btnFecha = (Button) findViewById(R.id.button7);
        spinner = (Spinner) findViewById(R.id.spinner3);
        spinnerInstalaciones = (Spinner) findViewById(R.id.spinner2);
        spinnerTipo = (Spinner) findViewById(R.id.spinner4);
        spinnerHoraInicio = (Spinner) findViewById(R.id.spinner5);

        Calendar calendario = Calendar.getInstance();
        dia = calendario.get(Calendar.DAY_OF_MONTH);
        mes = calendario.get(Calendar.MONTH);
        ano = calendario.get(Calendar.YEAR);
        hora = calendario.get(Calendar.HOUR_OF_DAY);
        minuto = calendario.get(Calendar.MINUTE);
        car = Calendar.getInstance();
        fecha=ano+"-"+mes+"-"+dia;

        spinnerTipo.setOnItemSelectedListener(this);
        spinnerHoraInicio.setOnItemSelectedListener(this);
        rellenarSpinnerNumeros();
        rellenarSpinnerNombreInstalaciones();
        rellenarSpinnerTipoInstalaciones();

    }

    public void recogerFecha(View view){
        Calendar cal = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                fecha = year+"-"+month+"-"+dayOfMonth;
                car.set(Calendar.YEAR, year);
                car.set(Calendar.MONTH, month);
                car.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                ano=year;
                mes=month;
                dia=dayOfMonth;
            }
        }, ano, mes, dia);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
        datePickerDialog.show();
    }

    public void setInstalacionElegidaMaps(){
        Bundle bundle = this.getIntent().getExtras();
        if(bundle!=null){
            String instalacion = bundle.getString("Nombre");

            for (int i =0; i<nameInstalaciones.length; i++) {
                if(nameInstalaciones[i].contains(instalacion)) {
                    spinnerInstalaciones.setSelection(i);
                }
            }
        }
    }

    public void anadirReserva(View view){

        String horaInicio =spinnerHoraInicio.getSelectedItem().toString();
        String horaFinal = String.valueOf(Integer.parseInt(spinnerHoraInicio.getSelectedItem().toString())+Integer.parseInt(spinner.getSelectedItem().toString()));
        String user = preferences.getString("user", "");
        Cursor c = db.rawQuery("SELECT * FROM reserva WHERE instalacion='"+spinnerInstalaciones.getSelectedItem().toString()+"' and fechaInicio=Date('"+fecha+"') and ("+horaInicio+" BETWEEN horaInicio and (horaFinal-1) or "+horaFinal+" BETWEEN (horaInicio+1) and horaFinal);", null);

        if(c.getCount()==0){
            if(Integer.parseInt(horaFinal)>20){
                Toast.makeText(getApplicationContext(), "No se pueden reservar horas a partir de las 20", Toast.LENGTH_LONG).show();
            }else{
            try {
                db.execSQL("INSERT INTO reserva(fechaInicio, horaInicio, horaFinal, instalacion, usuario) VALUES('"+fecha+"',"+horaInicio+", "+horaFinal+", '"+spinnerInstalaciones.getSelectedItem().toString()+"', '"+user+"')");
                Toast.makeText(getApplicationContext(), "Reserva correcta", Toast.LENGTH_LONG).show();
                setAlarm();
            }catch (Exception e){
                Log.e("Añadir reserva", e.getMessage());
                Toast.makeText(getApplicationContext(), "Error al realizar la reserva", Toast.LENGTH_LONG).show();
            }
            }
        }else{
            Toast.makeText(getApplicationContext(), "Esta hora esta reservada ya", Toast.LENGTH_LONG).show();
        }
    }

    public void mapaDeInstalaciones(View view){
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    public void horasDisponibles(View view){
        Intent intent = new Intent(this, HorasDisponibles.class);
        intent.putExtra("Instalacion", spinnerInstalaciones.getSelectedItem().toString());
        intent.putExtra("Fecha", fecha);
        startActivity(intent);
    }

    public void rellenarSpinnerNumeros(){
        //Adaptador rellenar horas alquiladas
        String [] elementos = {"1", "2", "3"};
        ArrayAdapter adp = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, elementos);
        spinner.setAdapter(adp);
        //Adaptador horas
        String [] horas = {"9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"};
        ArrayAdapter adpHoras = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, horas);
        spinnerHoraInicio.setAdapter(adpHoras);
    }

    public void rellenarSpinnerNombreInstalaciones(){
        //Adaptador nombre instalaciones
        try{
            int contador=0;
            Cursor cursor = db.rawQuery("SELECT * FROM instalaciones", null);
            nameInstalaciones = new String[cursor.getCount()];
            while(cursor.moveToNext()){
                nameInstalaciones[contador] = cursor.getString(0);
                contador++;
            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Error al conseguir datos de las instalaciones", Toast.LENGTH_LONG).show();
        }
        ArrayAdapter adpInstalaciones = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, nameInstalaciones);
        spinnerInstalaciones.setAdapter(adpInstalaciones);
        setInstalacionElegidaMaps();
    }

    public void rellenarSpinnerTipoInstalaciones(){
        //Adaptador tipo Instalaciones
        try{
            int contador=1;
            Cursor cursor = db.rawQuery("SELECT distinct(tipoinstalacion) FROM instalaciones", null);
            tipoInstalaciones = new String[cursor.getCount()+1];
            tipoInstalaciones[0]="Todos";
            while(cursor.moveToNext()){
                tipoInstalaciones[contador] = cursor.getString(0);
                contador++;
            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Error al conseguir datos de las instalaciones", Toast.LENGTH_LONG).show();
        }
        ArrayAdapter adpTipoInstalaciones = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, tipoInstalaciones);
        spinnerTipo.setAdapter(adpTipoInstalaciones);
    }

    private void setAlarm() {
//        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//
//        Intent intent = new Intent(this, NotificationService.class);
//        PendingIntent pendingIntent = PendingIntent.getService(this, (int) System.currentTimeMillis(), intent, 0);
//
//        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, NotificationService.class);
        intent.putExtra("Instalacion", spinnerInstalaciones.getSelectedItem().toString());
        intent.putExtra("Hora", spinnerHoraInicio.getSelectedItem().toString());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, (int)System.currentTimeMillis(), intent, PendingIntent.FLAG_IMMUTABLE);

        car.add(Calendar.HOUR_OF_DAY, Integer.parseInt(spinnerHoraInicio.getSelectedItem().toString()));
        Calendar fechaNotificacion = Calendar.getInstance();
        fechaNotificacion.setTimeInMillis(car.getTimeInMillis() - 15 * 60 * 1000);
        long triggerTime = fechaNotificacion.getTimeInMillis();

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent);
        Log.e("Alarma", "Da tiempo a la alarma");
    }

    /**
     * <p>Callback method to be invoked when an item in this view has been
     * selected. This callback is invoked only when the newly selected
     * position is different from the previously selected position or if
     * there was no selected item.</p>
     * <p>
     * Implementers can call getItemAtPosition(position) if they need to access the
     * data associated with the selected item.
     *
     * @param parent   The AdapterView where the selection happened
     * @param view     The view within the AdapterView that was clicked
     * @param position The position of the view in the adapter
     * @param id       The row id of the item that is selected
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getId()==R.id.spinner5){
            if(Integer.parseInt(parent.getSelectedItem().toString())>17){
                String [] elementos = {"1", "2", "3"};
                ArrayAdapter adp = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, elementos) {
                    @Override
                    public boolean isEnabled(int position) {
                        switch (Integer.parseInt(parent.getSelectedItem().toString())){
                            case 18: return position!=2;
                            case 19: return position!=1 && position!=2;
                            case 20: return position!=0 && position!=1 && position!=2;
                        }
                        return false;
                    }
                };
                spinner.setAdapter(adp);
            }
        }
        if(parent.getId()==R.id.spinner4){
            if(spinnerTipo.getSelectedItem().toString()!="Todos"){
                try{
                    int contador=0;
                    Cursor cursor = db.rawQuery("SELECT * FROM instalaciones where tipoinstalacion='"+parent.getSelectedItem().toString()+"'", null);
                    nameInstalaciones = new String[cursor.getCount()];
                    while(cursor.moveToNext()){
                        nameInstalaciones[contador] = cursor.getString(0);
                        contador++;
                    }
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), "Error al conseguir datos de las instalaciones", Toast.LENGTH_LONG).show();
                }
                ArrayAdapter adpInstalaciones = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, nameInstalaciones);
                spinnerInstalaciones.setAdapter(adpInstalaciones);
            }else{
                rellenarSpinnerNombreInstalaciones();
            }
            String auxInstalacionName = String.valueOf(spinnerInstalaciones.getSelectedItemId());
        }
    }

    /**
     * Callback method to be invoked when the selection disappears from this
     * view. The selection can disappear for instance when touch is activated
     * or when the adapter becomes empty.
     *
     * @param parent The AdapterView that now contains no selected item.
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
//    private String getDateTime() {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
//        Date date = new Date();
//        return dateFormat.format(date);
//    }
}
package com.example.practicainstalaciones;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Console;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static SQLiteDatabase db;
    private EditText txtUsuarioLog;
    private EditText txtContraLog;
    public static boolean usuarioLogeado=false;
    public static SharedPreferences preferences;
    public static SharedPreferences.Editor editor;

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
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtUsuarioLog = (EditText) findViewById(R.id.editTextLogin);
        txtContraLog = (EditText) findViewById(R.id.editTextPassLogin);

        preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        editor = preferences.edit();

        String user = preferences.getString("user", "");
        if(user!="") {
            Toast toast = Toast.makeText(getApplicationContext(), "Bienvenido "+user, Toast.LENGTH_LONG);
            toast.show();
        }

        if(user!=""){
            Intent intent = new Intent(getApplicationContext(), Reservas.class);
            startActivity(intent);
        }

        creandoBaseDatos();
        insertarInstalaciones();

    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onStart() {
        super.onStart();
        String user = preferences.getString("user", "");
        if (user != "") {
            Intent intent = new Intent(getApplicationContext(), Reservas.class);
            startActivity(intent);
        }
    }

    public void creandoBaseDatos(){
        try {
            db = openOrCreateDatabase("Reservas", Context.MODE_PRIVATE, null);
            db.execSQL("Create table if not exists usuarios(usuario varchar,contraseña varchar, primary key(usuario))");
            db.execSQL("create table if not exists instalaciones(nombre varchar(50) primary key, altitud float, latitud float, tipoinstalacion varchar(50));");
            db.execSQL("create table if not exists reserva(fechaInicio timestamp, horaInicio int, horaFinal int, instalacion varchar, usuario varchar, foreign key(instalacion) references instalaciones(nombre), primary key(fechaInicio, horaInicio, instalacion), foreign key(usuario) references usuarios(usuario));");

        }catch (Exception e){
            Log.e("CreateDatabase", String.valueOf(e));
        }
    }

    public void insertarInstalaciones(){
        Cursor c= db.rawQuery("SELECT * FROM instalaciones", null);
        if(c.getCount()==0){
            db.execSQL("insert into instalaciones(nombre, altitud, latitud, tipoinstalacion) values('IES Julian Marias', 41.632027873568504, -4.758640510497422,'Futbol sala')");
            db.execSQL("insert into instalaciones(nombre, altitud, latitud, tipoinstalacion) values('Escuela tenis parquesol', 41.6318701505074, -4.754449584859948,'Tenis')");
            db.execSQL("insert into instalaciones(nombre, altitud, latitud, tipoinstalacion) values('Centro Deportivo y Cultural Niara', 41.62024144804, -4.76193620331824,'Skate')");
            db.execSQL("insert into instalaciones(nombre, altitud, latitud, tipoinstalacion) values('UD SUR Campo de Fútbol', 41.62039797727426, -4.742355082215218,'Futbol')");
            db.execSQL("insert into instalaciones(nombre, altitud, latitud, tipoinstalacion) values('Campo de Fútbol Felicísimo de la Fuente', 41.629056185463426, -4.739607695997998,'Futbol')");
            db.execSQL("insert into instalaciones(nombre, altitud, latitud, tipoinstalacion) values('Polideportivo Pisuerga', 41.632288710053665, -4.745282821575812,'Futbol sala')");
            db.execSQL("insert into instalaciones(nombre, altitud, latitud, tipoinstalacion) values('Campos de Fútbol El Palero', 41.63396362725116, -4.745029420196106,'Futbol')");
            db.execSQL("insert into instalaciones(nombre, altitud, latitud, tipoinstalacion) values('Fútbol Plaza del Ejército', 41.632027873568504, -4.758640510497422,'Futbol sala')");
            db.execSQL("insert into instalaciones(nombre, altitud, latitud, tipoinstalacion) values('Piscina Municipal Benito Sanz de la Rica', 41.63086041024827, -4.739630005863591,'Natacion')");
            db.execSQL("insert into instalaciones(nombre, altitud, latitud, tipoinstalacion) values('Pabellón Multifuncional Infanta Juana', 41.64182897318338, -4.7570980684976885,'Futbol sala')");
            db.execSQL("insert into instalaciones(nombre, altitud, latitud, tipoinstalacion) values('Piscina Municipal Cubierta Parquesol', 41.66229047999919, -4.7810992055754005,'Natacion')");
            db.execSQL("insert into instalaciones(nombre, altitud, latitud, tipoinstalacion) values('INSOCCER Futbol Indoor Valladolid', 41.66507972797509, -4.772601967416086,'Futbol sala')");
            db.execSQL("insert into instalaciones(nombre, altitud, latitud, tipoinstalacion) values('Canchas Deportivas De Valparaíso', 41.62553807258394, -4.754405981298816,'Tenis')");
            db.execSQL("insert into instalaciones(nombre, altitud, latitud, tipoinstalacion) values('Polideportivo Miriam Blasco', 41.65789630447468, -4.713114063916973,'Futbol sala')");
            db.execSQL("insert into instalaciones(nombre, altitud, latitud, tipoinstalacion) values('Skatepark Las Moreras', 41.65228265748662, -4.733023665707266,'Skate')");
            db.execSQL("insert into instalaciones(nombre, altitud, latitud, tipoinstalacion) values('Frontón Complejo Deportivo San Pedro Regalado', 41.66815938243921, -4.704183352130252,'Tenis')");
            db.execSQL("insert into instalaciones(nombre, altitud, latitud, tipoinstalacion) values('Campo Betis', 41.64057549988051, -4.713408350943638,'Futbol')");
            db.execSQL("insert into instalaciones(nombre, altitud, latitud, tipoinstalacion) values('Unión Delicias', 41.63362787098726, -4.715927886873066,'Futbol')");
            db.execSQL("insert into instalaciones(nombre, altitud, latitud, tipoinstalacion) values('The Players', 41.618278711122834, -4.735366094969058,'Padel')");
            db.execSQL("insert into instalaciones(nombre, altitud, latitud, tipoinstalacion) values('VIP Padel', 41.63115236250467, -4.710946356479901,'Padel')");
            db.execSQL("insert into instalaciones(nombre, altitud, latitud, tipoinstalacion) values('Piscina Municipal Henar Alonso-Pimentel', 41.644249623800754, -4.710270385985553,'Natacion')");
            db.execSQL("insert into instalaciones(nombre, altitud, latitud, tipoinstalacion) values('Club Deportivo San Nicolás', 41.65946408472005, -4.70059533973802,'Padel')");
            db.execSQL("insert into instalaciones(nombre, altitud, latitud, tipoinstalacion) values('Cancha de baloncesto de Miguel Martín Luquero', 41.638075853436284, -4.741742323587489,'Baloncesto')");
            db.execSQL("insert into instalaciones(nombre, altitud, latitud, tipoinstalacion) values('Pistas de Baloncesto Covaresa', 41.648081874590126, -4.738473594188245,'Baloncesto')");
            db.execSQL("insert into instalaciones(nombre, altitud, latitud, tipoinstalacion) values('Pista de baloncesto Norias', 41.63431240218929, -4.73089661886488,'Baloncesto')");
        }

    }
    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    public void accederRegistro(View view){
        Intent intent = new Intent(this, Registro.class);
        secondActivity.launch(intent);
    }

    public void accederLogin(View view){
        //Obtenemos los datos del usuario y los comprobamos con la base de datos
        String usuario = txtUsuarioLog.getText().toString();
        String contra = txtContraLog.getText().toString();
        Cursor cursor = db.rawQuery("SELECT * FROM usuarios;", null);
        while(cursor.moveToNext()){
            String usur = cursor.getString(0);
            if(usur.equals(usuario)){
                Cursor cursor1 = db.rawQuery("SELECT * FROM usuarios where usuario='"+usuario+"'", null);
                while(cursor1.moveToNext()){
                    String contr = cursor1.getString(1);
                    if(contr.equals(contra)){
                        //Entrar a panel de control, poner en Oncreate que si logueado = true inicie siempre el activity de reservas
                        Toast.makeText(getApplicationContext(), "Inicio de sesion correcto", Toast.LENGTH_LONG).show();
                        usuarioLogeado=true;
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("user", usuario);
                        editor.commit();
                        Intent intent = new Intent(getApplicationContext(), Reservas.class);
                        startActivity(intent);

                    }else{
                        Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrecta", Toast.LENGTH_LONG).show();
                    }
                }
            }else{
                Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrecta", Toast.LENGTH_LONG).show();
            }
        }
    }
}
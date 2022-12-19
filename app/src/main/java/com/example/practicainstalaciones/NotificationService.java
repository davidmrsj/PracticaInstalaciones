package com.example.practicainstalaciones;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationService extends BroadcastReceiver {
    private static final String CHANNEL_ID = "355";
    private static final int NOTIFICATION_ID = 23;

    /**
     * Called by the system when the service is first created.  Do not call this method directly.
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("Alarma", "Entra en NotificacionService");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Nombre del canal";
            String description = "Descripción del canal";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        // Crea la notificación
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Título de la notificación")
                .setContentText("Texto de la notificación")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // Muestra la notificación
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
        Log.e("Alarma", "Muestra la notificacion");
    }

    /**
     * Return the communication channel to the service.  May return null if
     * clients can not bind to the service.  The returned
     * {@link IBinder} is usually for a complex interface
     * that has been <a href="{@docRoot}guide/components/aidl.html">described using
     * aidl</a>.
     *
     * <p><em>Note that unlike other application components, calls on to the
     * IBinder interface returned here may not happen on the main thread
     * of the process</em>.  More information about the main thread can be found in
     * <a href="{@docRoot}guide/topics/fundamentals/processes-and-threads.html">Processes and
     * Threads</a>.</p>
     *
     * @param intent The Intent that was used to bind to this service,
     *               as given to {@link Context#bindService
     *               Context.bindService}.  Note that any extras that were included with
     *               the Intent at that point will <em>not</em> be seen here.
     * @return Return an IBinder through which clients can call on to the
     * service.
     */
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        NotificationCompat.Builder crearNotificacion = new NotificationCompat.Builder(this, "Notificacion")
//                .setSmallIcon(android.R.drawable.ic_dialog_info)
//                .setContentTitle("Reserva")
//                .setContentText("Quedan 30 min para que llegue su reserva")
//                .setContentInfo("4")
//                .setTicker("¡Alerta!");
//
//        Intent notificacion = new Intent(getApplicationContext(), MainActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, notificacion, 0);
//        crearNotificacion.setContentIntent(pendingIntent);
//
//        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.notify(0, crearNotificacion.build());
//        return null;
//    }
}

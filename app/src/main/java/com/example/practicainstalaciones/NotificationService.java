package com.example.practicainstalaciones;

import static android.content.Intent.getIntent;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
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
                .setSmallIcon(R.drawable.common_google_signin_btn_text_light_normal)
                .setContentTitle("Quedan 15 minutos para tu reserva")
                .setContentText("Tu reserva esta apunto de empezar")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // Muestra la notificación
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
        Log.e("Alarma", "Muestra la notificacion");
    }

}

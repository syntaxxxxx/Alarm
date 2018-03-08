package imastudio.co.id.alarmmanager;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import java.util.Calendar;

public class AlarmReceiver extends BroadcastReceiver {

    // Variable Konstanta Digunakan All
    public static final String TYPE_ONE_TIME = "type_one_time";
    public static final String TYPE_REPEATING = "type_repeating";
    // Intent Key
    public static final String EXTRA_MESSAGE = "extra_message";
    public static final String EXTRA_TYPE = "extra_time";
    // Variable Dislplaying Notif To User
    private final int NOTIF_ID_ONETIME = 100;
    private final int NOTIF_ID_REPEATING = 101;

    public AlarmReceiver() {
    }

    //TODO Dieksekusi Ketika Sesuai Alarm Yang Ditentukan
    @Override
    public void onReceive(Context context, Intent intent) {
        String type = intent.getStringExtra(EXTRA_TYPE);
        String message = intent.getStringExtra(EXTRA_MESSAGE);
        String title = type.equalsIgnoreCase(TYPE_ONE_TIME) ? "One Time Alarm" : "Repeating Alarm";
        int notifId = type.equalsIgnoreCase(TYPE_ONE_TIME) ? NOTIF_ID_ONETIME : NOTIF_ID_ONETIME;

        showAlarmNotification(context, title, message, notifId);
    }

    //TODO Displaying Notif Alarm
    private void showAlarmNotification(Context context, String title, String message, int notifId) {
        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_alarm_black_24dp)
                .setContentTitle(title)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound);
        notificationManagerCompat.notify(notifId, builder.build());
    }

    public void setOneTimeAlarm(Context context, String type, String date, String time, String message) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        //TODO Run AlarmReceiver Bawa Data Alarm Message
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(EXTRA_MESSAGE, message);
        intent.putExtra(EXTRA_TYPE, type);
        String dateArray[] = date.split("-");
        String timeArray[] = time.split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, Integer.parseInt(dateArray[0]));
        calendar.set(Calendar.MONTH, Integer.parseInt(dateArray[1]) - 1);
        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateArray[2]));
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND, 0);

        int requestCode = NOTIF_ID_ONETIME;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

        Toast.makeText(context, "One Time Alarm Set Up", Toast.LENGTH_SHORT).show();
    }

    public void setRepeatingAlarm(Context context, String type, String time, String message) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(EXTRA_MESSAGE, message);
        intent.putExtra(EXTRA_TYPE, type);
        String timeArray[] = time.split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND, 0);
        int requestCode = NOTIF_ID_REPEATING;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        //TODO Use Method setInexactRepeating
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        Toast.makeText(context, "Repeating Alarm Set Up", Toast.LENGTH_SHORT).show();
    }

    public void cancelAlarm(Context context, String type) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        int requestCode = type.equalsIgnoreCase(TYPE_ONE_TIME) ? NOTIF_ID_ONETIME : NOTIF_ID_REPEATING;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        alarmManager.cancel(pendingIntent);
        Toast.makeText(context, "Repeating Alarm Dibatalkan", Toast.LENGTH_SHORT).show();
    }
}

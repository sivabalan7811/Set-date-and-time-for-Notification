package com.example.alarm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    Context context=MainActivity.this;
    private final static String default_notification_channel_id = "default" ;
    Button btnDate ;
    final Calendar myCalendar = Calendar. getInstance () ;

    int hour;
    int minutes;
    public void setDateNiw(){
        String strDateTimeBoj="14/03/2022 15:57";
        //first you need to use proper date formatter
        DateFormat df=new SimpleDateFormat("dd/MM/yyyy kk:mm");
        Date date= null;// converting String to date
        try {
            date = df.parse(strDateTimeBoj);
            System.out.println(df.format(date));
            Calendar cal = Calendar. getInstance();
            cal. setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet (DatePicker view , int year , int monthOfYear , int dayOfMonth) {

            Log.d("monthOfYear",""+monthOfYear);

            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.YEAR, year);

            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(MainActivity.this,
                    android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {



                            myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                            myCalendar.set(Calendar.MINUTE, minute);
                            myCalendar.set(Calendar.SECOND, 0);
                            trigged();


                      }
                    }, hour, minutes, false);
            mTimePicker.setTitle("Select Time");
            mTimePicker.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            mTimePicker.show();
        }
    } ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        hour = myCalendar.get(Calendar.HOUR_OF_DAY);
        minutes = myCalendar.get(Calendar.MINUTE);

    }

    private void updateLabel () {
        String myFormat = "dd/MM/yy" ; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat , Locale. getDefault ()) ;
        Date date = myCalendar .getTime() ;
        btnDate .setText(sdf.format(date)) ;
       // scheduleNotification(getNotification( btnDate .getText().toString()) , date.getTime()) ;
    }
    public void setDate (View view) {
        new DatePickerDialog(
                MainActivity. this, date ,
                myCalendar .get(Calendar. YEAR ) ,
                myCalendar .get(Calendar. MONTH ) ,
                myCalendar .get(Calendar. DAY_OF_MONTH )
        ).show() ;
    }

    public void trigged(){
       // Calendar calendar = Calendar.getInstance();


        Intent myIntent = new Intent(context, MyNotificationPublisher.class);
        myIntent.putExtra("ALARM1_ID","ALARM1_ID");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context, 1234, myIntent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
       // alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, myCalendar.getTimeInMillis(), pendingIntent);

    }
    public void trigged(int minute,int ALARM1_ID){
        // Calendar calendar = Calendar.getInstance();





        Intent myIntent = new Intent(context, MyNotificationPublisher.class);
        myIntent.putExtra("ALARM1_ID","ALARM1_ID");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context, ALARM1_ID, myIntent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        // alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, myCalendar.getTimeInMillis(), pendingIntent);

    }
    public static void basicNotification(Intent intent ,Context context) {
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_ONE_SHOT);
//        String CHANNEL_ID = "channel_name";// The id of the channel.
//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setContentTitle("title")
//                .setContentText("Be Ready for session")
//                .setAutoCancel(true)
//                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
//                .setContentIntent(pendingIntent);
//        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            CharSequence name = "Channel Name";// The user-visible name of the channel.
//            int importance = NotificationManager.IMPORTANCE_HIGH;
//            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
//            notificationManager.createNotificationChannel(mChannel);
//        }
//        notificationManager.notify(1, notificationBuilder.build()); // 0 is the request code, it should be unique id

    }



    @SuppressLint("SimpleDateFormat")
    public static String getTimeStamp() {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }

}
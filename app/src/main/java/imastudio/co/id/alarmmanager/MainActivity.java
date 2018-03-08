package imastudio.co.id.alarmmanager;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_one_time_alarm_date)
    Button btnOneTimeAlarmDate;
    @BindView(R.id.tv_one_time_alarm_date)
    TextView tvOneTimeAlarmDate;
    @BindView(R.id.btn_one_time_alarm_time)
    Button btnOneTimeAlarmTime;
    @BindView(R.id.tv_one_time_alarm_time)
    TextView tvOneTimeAlarmTime;
    @BindView(R.id.edt_one_time_alarm_message)
    EditText edtOneTimeAlarmMessage;
    @BindView(R.id.btn_set_one_alam_time)
    Button btnSetOneAlamTime;
    @BindView(R.id.btn_repeating_alarm_time)
    Button btnRepeatingAlarmTime;
    @BindView(R.id.edt_repeating_alarm_message)
    EditText edtRepeatingAlarmMessage;
    @BindView(R.id.btn_repeating_time_alarm)
    Button btnRepeatingTimeAlarm;
    @BindView(R.id.btn_cancel_repeating_alarm)
    Button btnCancelRepeatingAlarm;
    @BindView(R.id.tv_repeating_alarm)
    TextView tvRepeatingAlarm;

    private Calendar calOneTimeDate, calOneTimeTime, calRepeatingTimeTime;
    private AlarmReceiver alarmReceiver;
    private AlarmPreference alarmPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        calOneTimeDate = Calendar.getInstance();
        calOneTimeTime = Calendar.getInstance();
        calRepeatingTimeTime = Calendar.getInstance();

        alarmPreference = new AlarmPreference(this);
        alarmReceiver = new AlarmReceiver();

        if (!TextUtils.isEmpty(alarmPreference.getOneTimeDate())) {
            setOnTimeText();
        }
    }

    @OnClick({R.id.btn_one_time_alarm_date, R.id.btn_one_time_alarm_time, R.id.btn_set_one_alam_time, R.id.btn_repeating_alarm_time, R.id.btn_repeating_time_alarm, R.id.btn_cancel_repeating_alarm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //TODO Displaying Date Picker
            case R.id.btn_one_time_alarm_date:
                final Calendar currentDate = Calendar.getInstance();
                new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        calOneTimeDate.set(year, monthOfYear, dayOfMonth);
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        tvOneTimeAlarmDate.setText(dateFormat.format(calOneTimeDate.getTime()));
                    }
                }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();
                break;
            //TODO Displaying Time Picker
            case R.id.btn_one_time_alarm_time:
                final Calendar currentDate2 = Calendar.getInstance();
                new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calOneTimeTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calOneTimeTime.set(Calendar.MINUTE, minute);
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
                        tvOneTimeAlarmTime.setText(dateFormat.format(calOneTimeTime.getTime()));
                    }
                }, currentDate2.get(Calendar.HOUR_OF_DAY), currentDate2.get(Calendar.MINUTE), true).show();
                break;

            case R.id.btn_set_one_alam_time:
                @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String oneTimeDate = dateFormat.format(calOneTimeDate.getTime());
                @SuppressLint("SimpleDateFormat") SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                String oneTimeTime = timeFormat.format(calOneTimeTime.getTime());
                String oneTimeMessage = edtOneTimeAlarmMessage.getText().toString();

                //TODO Save Date, Time, Message Alarm
                alarmPreference.setOneTimeDate(oneTimeDate);
                alarmPreference.setOneTimeMessage(oneTimeMessage);
                alarmPreference.setOneTimeTime(oneTimeTime);

                alarmReceiver.setOneTimeAlarm(this, AlarmReceiver.TYPE_ONE_TIME,
                        alarmPreference.getOneTimeDate(),
                        alarmPreference.getOneTimeTime(),
                        alarmPreference.getOneTimeMessage());
                break;

            case R.id.btn_repeating_alarm_time:
                final Calendar currentDate3 = Calendar.getInstance();
                new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calRepeatingTimeTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calRepeatingTimeTime.set(Calendar.MINUTE, minute);
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
                        tvRepeatingAlarm.setText(dateFormat.format(calRepeatingTimeTime.getTime()));

                    }
                }, currentDate3.get(Calendar.HOUR_OF_DAY), currentDate3.get(Calendar.MINUTE), true).show();
                break;

            case R.id.btn_repeating_time_alarm:
                @SuppressLint("SimpleDateFormat") SimpleDateFormat timeFormat2 = new SimpleDateFormat("HH:mm");
                String repeatTimeTime = timeFormat2.format(calRepeatingTimeTime.getTime());
                String repeatingMessage = edtRepeatingAlarmMessage.getText().toString();

                alarmPreference.setRepeatingTime(repeatTimeTime);
                alarmPreference.setRepeatingMessage(repeatingMessage);

                alarmReceiver.setRepeatingAlarm(this, AlarmReceiver.TYPE_REPEATING,
                        alarmPreference.getRepeatingTime(), alarmPreference.getRepeatingMessage());
                break;

            case R.id.btn_cancel_repeating_alarm:
                alarmReceiver.cancelAlarm(this, AlarmReceiver.TYPE_REPEATING);
                break;
        }
    }

    //TODO Menampilkan Value Yang User Input Ke Object TextView
    private void setOnTimeText() {
        tvOneTimeAlarmTime.setText(alarmPreference.getOneTimeTime());
        tvOneTimeAlarmDate.setText(alarmPreference.getOneTimeDate());
        edtOneTimeAlarmMessage.setText(alarmPreference.getOneTimeMessage());
    }

    protected void setRepeatingText() {
        tvRepeatingAlarm.setText(alarmPreference.getRepeatingTime());
        edtRepeatingAlarmMessage.setText(alarmPreference.getRepeatingMessage());
    }
}

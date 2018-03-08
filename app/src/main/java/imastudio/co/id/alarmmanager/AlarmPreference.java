package imastudio.co.id.alarmmanager;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by macbook on 3/8/18.
 */

public class AlarmPreference {
    private final String PREF_NAME = "pref_name";
    private final String KEY_ONE_TIME_DATE = "one_time_date";
    private final String KEY_ONE_TIME_TIME = "one_time_time";
    private final String KEY_ONE_TIME_MESSAGE = "one_time_message";
    private final String KEY_REPEATING_TIME = "repeating_time";
    private final String KEY_REPEATING_MESSAGE = "repeating_message";

    //TODO Mekanisme Penyimpanan Date, Timer, Message
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor editor;

    public AlarmPreference(Context context) {
        mSharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = mSharedPreferences.edit();

    }

    public void setOneTimeDate(String date) {
        editor.putString(KEY_ONE_TIME_DATE, date);
        editor.commit();
    }

    public String getOneTimeDate() {
        return mSharedPreferences.getString(KEY_ONE_TIME_DATE, null);
    }

    public void setOneTimeTime(String time) {
        editor.putString(KEY_ONE_TIME_TIME, time);
        editor.commit();
    }

    public String getOneTimeTime() {
        return mSharedPreferences.getString(KEY_ONE_TIME_TIME, null);
    }

    public void setOneTimeMessage(String message) {
        editor.putString(KEY_ONE_TIME_MESSAGE, message);
        editor.commit();
    }

    public String getOneTimeMessage() {
        return mSharedPreferences.getString(KEY_ONE_TIME_MESSAGE, null);
    }

    public void setRepeatingTime(String time) {
        editor.putString(KEY_REPEATING_TIME, time);
        editor.commit();
    }

    public String getRepeatingTime() {
        return mSharedPreferences.getString(KEY_REPEATING_TIME, null);
    }

    public void setRepeatingMessage(String message) {
        editor.putString(KEY_REPEATING_MESSAGE, message);
        editor.commit();
    }

    public String getRepeatingMessage() {
        return mSharedPreferences.getString(KEY_REPEATING_MESSAGE, null);
    }

    public void clear() {
        editor.clear();
        editor.commit();
    }
}

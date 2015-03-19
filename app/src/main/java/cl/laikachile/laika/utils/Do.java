package cl.laikachile.laika.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.activeandroid.Model;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by Tito_Leiva on 19-01-15.
 */
public class Do {

    // Date Patterns
    public static final String DAY_FIRST = "dd-MM-yyyy";
    public static final String DAY_FIRST_NO_YEAR = "dd-MM";
    public static final String HOUR_MINUTE = "HH:mm";
    public static final String FIRST_TIME_OF_DAY = " 00:00:00";
    public static final String LAST_TIME_OF_DAY = " 23:59:59";


    /**
     *
     * @author Tito_Leiva
     * @param context
     * @param name
     */
    public static void changeActivity(Context context, Class<?> name){
        Intent intent = new Intent(context, name);
        context.startActivity(intent);
    }

    /**
     * @author Tito_Leiva
     * @param context
     * @param name
     * @param closed
     */
    public static void changeActivity(Context context, Class<?> name, Activity closed){
        Intent intent = new Intent(context, name);
        context.startActivity(intent);
        closed.finish();
    }

    public static void changeActivity(Context context, Class<?> name, int flag) {
        Intent intent = new Intent(context, name);
        intent.addFlags(flag);
        context.startActivity(intent);
    }

    public static void changeActivity(Context context, Class<?> name, Activity closed, int flag) {
        Intent intent = new Intent(context, name);
        intent.addFlags(flag);
        context.startActivity(intent);
        closed.finish();
    }

    public static void changeFragment(Fragment removed, Fragment added, int container, String tag) {

        Activity activity = removed.getActivity();

        activity.getFragmentManager().beginTransaction().remove(removed).commit();
        activity.getFragmentManager().beginTransaction().add(container, added, tag).commit();
    }

    public static void showToast(String message,Context context){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(String message,Context context, int lenght){
        Toast.makeText(context, message, lenght).show();
    }

    public static String getToken(Context context){

        return ""; //FIXME getToken
    }

    public static String getToStringDate(int day, int month, int year) {

        String yearText = Integer.toString(year);
        String monthText = month < 10 ? "0"+Integer.toString(month + 1) : Integer.toString(month + 1);;
        String dayText = day < 10 ? "0"+Integer.toString(day) : Integer.toString(day);

        String date = dayText + "-" + monthText + "-" + yearText;
        return date;
    }

    public static String getToStringTime(int hour, int minute) {

        String hourText = hour < 10 ? "0"+Integer.toString(hour) : Integer.toString(hour);
        String minuteText = minute < 10 ? "0"+Integer.toString(minute) : Integer.toString(minute);

        return hourText + ":" + minuteText;
    }

    public static String dateToString(Date date, String pattern){

        String newDate = new SimpleDateFormat(pattern).format(date);

        return newDate;
    }

    public static Date stringToDate(String date, String pattern){

        Date newDate = new Date();
        try {
            newDate = new SimpleDateFormat(pattern).parse(date);
        } catch (ParseException e) {

            e.printStackTrace();
        }

        return newDate;
    }

    public static String today() {

        Date date = new Date();

        return Do.dateToString(date, Do.DAY_FIRST);
    }

    public static String now() {

        Date date = new Date();

        return Do.dateToString(date, Do.HOUR_MINUTE);
    }

    public static boolean isLoggedIn(Activity activity){

        return true; //FIXME isLoggedIn
    }
    public static int[] nowDateInArray() {

        Calendar cal = Calendar.getInstance();

        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int[] date = {day, month, year};

        return date;
    }

    public static int[] nowTimeInArray() {

        Calendar cal = Calendar.getInstance();

        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int min = cal.get(Calendar.MINUTE);

        int[] time = {hour, min};

        return time;
    }

    //Method to remove "tildes" while replacing capital letters
    public static String removeToLowerSpecialCharacters(String string){
        return Do.removeSpecialCharacters(string.toLowerCase());
    }
    // Method to remove "tildes" and special characters
    public static String removeSpecialCharacters(String string){
        return Normalizer.normalize(string, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }

    /**
     * Method to compare two strings, independent from special characters and cases.
     * @author johnpeebles
     * @param string1
     * @param string2
     * @return
     */
    public static boolean smartCompareStrings(String string1, String string2){
        return removeToLowerSpecialCharacters(string1).contains(removeToLowerSpecialCharacters(string2));
    }

    public static Model findByCustomId(Class<? extends Model> klass,String custom_id,String id){
        int parsedId=Integer.parseInt(id);
        List<Model> query =  new Select().from(klass).where(custom_id+" = ?",parsedId).execute();
        Model objectToReturn = query.size() > 0 ? query.get(0) : null;
        return klass.cast(objectToReturn);
    }

    public static void deleteAll(Class<? extends Model> klass) {

        new Delete().from(klass).execute();
    }

    public static void addStringToSharedPreferences(Context context, String key, String value) {

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getRString(Context context, int resource) {

        return context.getResources().getString(resource);
    }

    public static int randomInteger(int min, int max) {

        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    public static boolean isNullOrEmpty(String string) {

        if (string == null || string.isEmpty()) {
            return true;
        } else return false;
    }

    public static void showAlertDialog(Context context, String title, String message) {

        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(message);

            // Setting OK Button
            alertDialog.setButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                }
            });

        // Showing Alert Message
        alertDialog.show();
    }
}

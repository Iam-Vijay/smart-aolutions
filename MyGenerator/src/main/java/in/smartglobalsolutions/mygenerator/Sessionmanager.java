package in.smartglobalsolutions.mygenerator;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class Sessionmanager {
    private static String TAG = Sessionmanager.class.getSimpleName();
    // Shared Preferences
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    // Shared pref mode
    int PRIVATE_MODE = 0;
    // Shared preferences file name

    private static final String PREF_NAME = "AndroidLogin";
    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";


    public Sessionmanager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    public void setLogin(boolean isLoggedIn) {
        editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);
        // commit changes
        editor.commit();
        Log.d(TAG, "User login session modified!");
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGEDIN, false);
    }


    public void SavePref_String(String key, String value) {

        editor.putString(key, value);

        editor.commit();
    }
public void saveuid(String key,String value){
        editor.putString(key, value);
        editor.commit();

}
    public void savetodaydate(String key,String value){
        editor.putString(key, value);
        editor.commit();

    }
    public void savecid(String key,String value){
        editor.putString(key, value);
        editor.commit();

    }
    public void savecompanyname(String key,String value){
        editor.putString(key, value);
        editor.commit();
        Log.d(TAG, "User login session modified!");
    }
    public void saveusername(String key,String value){
        editor.putString(key, value);
        editor.commit();
    }
    public void saveroleid(String key,String value){
        editor.putString(key, value);
        editor.commit();

    }
    public void savecustomerid(String key,String value){
        editor.putString(key, value);
        editor.commit();
    }
    public void savemobile(String key,String value){
        editor.putString(key, value);
        editor.commit();
    }
    public void saverootid(String key,String value){
        editor.putString(key, value);
        editor.commit();
    }
    public void savedefaultaccount(String key,String value){
        editor.putString(key, value);
        editor.commit();
    }
    public void save_ecc_acc(String key,String value){
        editor.putString(key, value);
        editor.commit();
    }
    public void saveaddress(String key,String value){
        editor.putString(key, value);
        editor.commit();
    }
    public void savearea(String key,String value){
        editor.putString(key, value);
        editor.commit();
    }
    public void saveecgroup(String key,String value){
        editor.putString(key, value);
        editor.commit();
    }
    public void maincheck(String key,String value){
        editor.putString(key, value);
        editor.commit();
    }
    public void savecode(String key,String value){
        editor.putString(key, value);
        editor.commit();
    }
    public void savetoken(String key,String value){
        editor.putString(key, value);
        editor.commit();
    }

//    public void saveactivationqr(String key,String value){
//        editor.putString(key, value);
//        editor.commit();
//    }public void savecardqr(String key,String value){
//        editor.putString(key, value);
//        editor.commit();
//    }
    public String getValue(String key) {

        return pref.getString(key, null);

    }
    public void clearvalue(){
        editor.clear();
        editor.commit();
    }
    public void totalserialnumber(String key,int value){
        editor.putInt(key, value);
        editor.commit();
    }
    public int getint(String key){
        return pref.getInt(key,0);
    }
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
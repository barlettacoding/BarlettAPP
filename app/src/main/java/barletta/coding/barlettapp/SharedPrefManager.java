package barletta.coding.barlettapp;

import android.content.Context;
import android.content.SharedPreferences;


/*In questa classe usiamo le sharedPreference per salvare le credenziali dell'utente
che ha fatto il login. Di regola si tengono salvate nel telefono. Nelle sharedPreferences dell'app
 */
public class SharedPrefManager {
    private static SharedPrefManager instance;

    private static Context ctx;

    private static final String SHARED_PREF_NAME = "mysharedpref";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_USER_MAIL = "useremail";
    private static final String KEY_USER_ID = "usereid";
    private static final String KEY_USER_TIPO = "usertipo";

    private SharedPrefManager(Context context) {
        ctx = context;

    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPrefManager(context);
        }
        return instance;
    }

    public boolean userLogin(int ID, String username, String email, int Tipo){
        SharedPreferences sharedPref = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        //L'editor fa tipo una mappa, dove mettiamo nelle variabili che abbiamo creato
        //I dati che passiamo al metodo, ricevuti dalla query sempre.
        editor.putInt(KEY_USER_ID, ID);
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_USER_MAIL, email);
        editor.putInt(KEY_USER_TIPO, Tipo);

        editor.apply();
        return true;
    }
    //Ci serve a vedere se l'utente è loggato
    public boolean isLogged(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        if(sharedPreferences.getString(KEY_USERNAME, null) != null){
            return true;
        }

        return false;
    }
    //Indovina un pò?
    public boolean logout(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }

    public String getUsername(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME,null);
    }

    public String getUserEmail(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_MAIL,null);
    }

    public int getTipo(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_USER_TIPO,0);
    }
}

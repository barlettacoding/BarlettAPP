package barletta.coding.barlettapp.javaClass;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import barletta.coding.barlettapp.UserDiaryList;


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
    private static final String KEY_LIST_DIARY = "keyDiary";
    ArrayList<diaryObject> LIST_DIARY = UserDiaryList.diaryList;

    private SharedPrefManager(Context context) {
        ctx = context;

    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPrefManager(context);
        }
        return instance;
    }

    public boolean userLogin(int ID, String username, String email, int Tipo) {
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

    public boolean saveListDiary(ArrayList<diaryObject> diaryList){
        SharedPreferences sharedPref = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(diaryList);
        editor.putString(KEY_LIST_DIARY,json);
        editor.commit();

        return true;
    }

    public ArrayList<diaryObject> loadList(){
        SharedPreferences sharedPref = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String response = sharedPref.getString(KEY_LIST_DIARY,"");
        ArrayList<diaryObject> listToLoad = gson.fromJson(response, new TypeToken<List<diaryObject>>(){}.getType());
        return listToLoad;
    }



    //Ci serve a vedere se l'utente è loggato
    public boolean isLogged() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        if (sharedPreferences.getString(KEY_USERNAME, null) != null) {
            return true;
        }

        return false;
    }

    //Indovina un pò?
    public boolean logout() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }

    public String getUsername() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null);
    }

    public String getUserEmail() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_MAIL, null);
    }

    public int getTipo() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_USER_TIPO, 0);
    }

    public int getId() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_USER_ID, 0);
    }
}

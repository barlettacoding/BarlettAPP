package barletta.coding.barlettapp;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import barletta.coding.barlettapp.javaClass.SharedPrefManager;
import barletta.coding.barlettapp.util.MySingleton;

public class LoginAndRegister extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private Button loginButton;
    private Button registerButton;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_and_register);

        /*Controlliamo se l'utente è loggato, se è loggato
        questa Activity si interrompe con il metodo finish() e
        passiamo subito all'activity dell'homePage.
         */
        if (SharedPrefManager.getInstance(this).isLogged()) {
            finish();
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            return;
        }
        inizializeComponent();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LoginAndRegister.this, RegisterActivity.class));

            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });
        broadCastCallFromRegister();
    }

    public void inizializeComponent() {

        this.username = findViewById(R.id.editTextUsername);
        //this.username.setCompoundDrawablesWithIntrinsicBounds(R.drawable.baseline_account_circle_black_18dp,0,0,0);
        this.password = findViewById(R.id.editTextPassword);
        this.loginButton = findViewById(R.id.buttonLogin);
        this.registerButton = findViewById(R.id.buttonRegister);
        this.progressDialog = new ProgressDialog(this);
        this.progressDialog.setMessage(getString(R.string.progressDialogLogin));


    }

    private void userLogin() {
        final String username = this.username.getText().toString().trim();
        final String password = this.password.getText().toString().trim();
        String urlLogin = "https://barlettacoding.altervista.org/login.php";

        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlLogin,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            //Modificare il messaggio di errore. Fare la stringa
                            if (!jsonObject.getBoolean("error")) {//Da qui ci muoviamo alla prossima activity
                                SharedPrefManager.getInstance(getApplicationContext()).userLogin(jsonObject.getInt("id"),
                                        jsonObject.getString("username"), jsonObject.getString("email"), jsonObject.getInt("tipo"));
                                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                finish();
                            } else {
                                //Modificare il messaggio di errore. fare la stringa
                                Toast.makeText(getApplicationContext(), R.string.loginError, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                        Toast.makeText(getApplicationContext(), getString(R.string.toastErrorRegistration), Toast.LENGTH_SHORT).show();
                    }
                }) {
            //Qui mettiamo i parametri che dobbiamo passare al php
            //Per fare il login passiamo al php l'username e la password
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };
        //Controlliamo se l'utente a messo i dati. Se non li ha messi, non lo facciamo neanche andare avanti
        if (username.isEmpty() || password.isEmpty()) {
            progressDialog.hide();
            if (username.isEmpty()) {
                this.username.setError(getString(R.string.missingUsername));
            }
            if (password.isEmpty()) {
                this.password.setError(getString(R.string.missingPassword));
            }
        }
        //Se li ha messi, chiamamo la requestQueue e gli passiamo la stringRequest che contiene i dati per il login
        else {
            MySingleton.getInstance(this).addToRequestQueue(stringRequest);
        }


    }

    public void broadCastCallFromRegister() {
        BroadcastReceiver broadcast_reciever = new BroadcastReceiver() {

            @Override
            public void onReceive(Context arg0, Intent intent) {
                String action = intent.getAction();
                if (action.equals("finish")) {
                    //finishing the activity
                    finish();
                }
            }
        };
        registerReceiver(broadcast_reciever, new IntentFilter("finish"));
    }

}

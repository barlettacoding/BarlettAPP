package barletta.coding.barlettapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class LoginAndRegister extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private Button loginButton;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_and_register);
        inizializeComponent();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LoginAndRegister.this,RegisterActivity.class));

            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginTest();
            }
        });

    }

    public void inizializeComponent(){

        this.username = findViewById(R.id.editTextUsername);
        this.password = findViewById(R.id.editTextPassword);
        this.loginButton = findViewById(R.id.buttonLogin);
        this.registerButton = findViewById(R.id.buttonRegister);

    }

    public void loginTest(){

        String urlRegister = "http://barlettacoding.altervista.org/login.php";

        final String username = this.username.getText().toString().trim();
        final String password = this.password.getText().toString().trim();



        //StringRequest serve per fare le richieste
        //SI passa come primo parametro che tipo di richiesta, secondo paramentro il link al php da usare
        //e poi i listener
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlRegister,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getApplicationContext(),getString(R.string.toastErrorRegistration), Toast.LENGTH_SHORT).show();
                    }
                }){
            //Qui mettiamo i parametri che dobbiamo passare al php
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username",username);
                params.put("password",password);


                return params;
            }
        };
        //La requestQ serve a far partire la richiesta. Si usa la stringRequest che abbiamo creato
        //RequestQueue requestQ = Volley.newRequestQueue(this);
        //requestQ.add(stringRequest);


            MySingleton.getInstance(this).addToRequestQueue(stringRequest);
        



    }

}

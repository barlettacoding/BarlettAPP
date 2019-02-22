package barletta.coding.barlettapp;

import android.app.ProgressDialog;
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
    private ProgressDialog progressDialog;

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
                userLogin();
            }
        });

    }

    public void inizializeComponent(){

        this.username = findViewById(R.id.editTextUsername);
        this.password = findViewById(R.id.editTextPassword);
        this.loginButton = findViewById(R.id.buttonLogin);
        this.registerButton = findViewById(R.id.buttonRegister);
        this.progressDialog = new ProgressDialog(this);
        this.progressDialog.setMessage(getString(R.string.progressDialogLogin));


    }

    private void userLogin(){
        final String username = this.username.getText().toString().trim();
        final String password = this.password.getText().toString().trim();
        String urlLogin = "http://barlettacoding.altervista.org/login.php";

        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlLogin,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            if(!jsonObject.getBoolean("error")){//Da qui ci muoviamo alla prossima activity
                                SharedPrefManager.getInstance(getApplicationContext()).userLogin(jsonObject.getInt("id"),
                                        jsonObject.getString("username"),jsonObject.getString("email"), jsonObject.getInt("tipo"));
                                Toast.makeText(getApplicationContext(),"Loggin effettuato",Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
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

        if(username.isEmpty() || password.isEmpty()){
            progressDialog.hide();
            if(username.isEmpty()){
                this.username.setError(getString(R.string.missingUsername));
            }
            if(password.isEmpty()){
                this.password.setError(getString(R.string.missingPassword));
            }
        }
        else{
            MySingleton.getInstance(this).addToRequestQueue(stringRequest);
        }


    }

}

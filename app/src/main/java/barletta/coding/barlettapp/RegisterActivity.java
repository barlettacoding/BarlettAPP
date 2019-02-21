package barletta.coding.barlettapp;

import android.app.ProgressDialog;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText usernameR;
    private EditText passwordR;
    private EditText emailR;
    private Button registerButtonR;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        inizializeComponent();

        registerButtonR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

    }


    private void registerUser(){

        String urlRegister = "http://barlettacoding.altervista.org/registrazione.php";

        final String username = usernameR.getText().toString().trim();
        final String password = passwordR.getText().toString().trim();
        final String email = emailR.getText().toString().trim();

        progressDialog.setMessage(getString(R.string.progressRegistration));
        progressDialog.show();
        //StringRequest serve per fare le richieste
        //SI passa come primo parametro che tipo di richiesta, secondo paramentro il link al php da usare
        //e poi i listener
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlRegister,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
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
                params.put("email", email);

                return params;
            }
        };
        //La requestQ serve a far partire la richiesta. Si usa la stringRequest che abbiamo creato
        //RequestQueue requestQ = Volley.newRequestQueue(this);
        //requestQ.add(stringRequest);

        if(username.isEmpty() || password.isEmpty() || email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            progressDialog.hide();
            Toast.makeText(getApplicationContext(),"Missing Field",Toast.LENGTH_SHORT).show();
        }
        else {
            MySingleton.getInstance(this).addToRequestQueue(stringRequest);
        }


    }



    public void inizializeComponent(){

        this.usernameR = findViewById(R.id.editTextUsernameR);
        this.passwordR = findViewById(R.id.editTextPasswordR);
        this.emailR = findViewById(R.id.editTextMailR);
        this.registerButtonR = findViewById(R.id.buttonRegisterR);
        progressDialog = new ProgressDialog(this);
    }

}

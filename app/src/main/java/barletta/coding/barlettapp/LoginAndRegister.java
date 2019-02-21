package barletta.coding.barlettapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

    }

    public void inizializeComponent(){

        this.username = findViewById(R.id.editTextUsername);
        this.password = findViewById(R.id.editTextPassword);
        this.loginButton = findViewById(R.id.buttonLogin);
        this.registerButton = findViewById(R.id.buttonRegister);

    }

}

package barletta.coding.barlettapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {
    //TUTTO DI PROVA. SERVE SOLO A CAPIRE COME PRENDERE I DATI
    private TextView usernameShow, emailShow, idshow;
    private Button logoutB;
    private int tipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Come prima cosa, vediamo se l'utente è loggato, se no, lo mandiamo al login
        if(!SharedPrefManager.getInstance(this).isLogged()){
            finish();
            startActivity(new Intent(this,LoginAndRegister.class));
        }

        usernameShow = findViewById(R.id.textViewUsername);
        emailShow = findViewById(R.id.textViewEmail);
        idshow = findViewById(R.id.textViewId);
        logoutB = findViewById(R.id.buttonLogOut);
        usernameShow.setText(SharedPrefManager.getInstance(this).getUsername());
        emailShow.setText(SharedPrefManager.getInstance(this).getUserEmail());
        tipo = SharedPrefManager.getInstance(this).getTipo();
        if(tipo == 1){
            idshow.setText("tipo 1");
        }
        if(tipo == 2){
            idshow.setText("tipo 2");
        }

        logoutB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPrefManager.getInstance(getApplicationContext()).logout();
                startActivity(new Intent(HomeActivity.this,LoginAndRegister.class));
                finish();
                return;
            }
        });

    }



}

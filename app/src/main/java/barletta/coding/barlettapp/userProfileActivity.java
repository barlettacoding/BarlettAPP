package barletta.coding.barlettapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class userProfileActivity extends AppCompatActivity {

    private TextView username, email;
    private Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        inizializeComponent();
        setUserCredential();
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPrefManager.getInstance(getApplicationContext()).logout();
                startActivity(new Intent(userProfileActivity.this,LoginAndRegister.class));
                finish();
                finishHomeActivity();
                return;
            }
        });

    }

    public void inizializeComponent(){

        username = findViewById(R.id.textViewProfileUsername);
        email = findViewById(R.id.textViewProfileEmail);
        logoutButton = findViewById(R.id.buttonProfileLogout);

    }

    public void setUserCredential(){

        username.setText("Username : " + SharedPrefManager.getInstance(getApplicationContext()).getUsername());
        email.setText("Email : " + SharedPrefManager.getInstance(getApplicationContext()).getUserEmail());

    }

    public void finishHomeActivity(){
        Intent intent = new Intent("finish");
        sendBroadcast(intent);
        finish();
    }

}

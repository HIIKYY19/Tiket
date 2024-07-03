package com.example.tiket;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {
    EditText username, password;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        username    = (EditText) findViewById(R.id.L_Username);
        password    = (EditText) findViewById(R.id.L_Password);
        login       = (Button) findViewById(R.id.L_Login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (username.getText().toString().equals("admin"))
                {
                    if (password.getText().toString().equals("12345"))
                    {
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Password Salah", Toast.LENGTH_SHORT).show();
                    }

                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Username Salah", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
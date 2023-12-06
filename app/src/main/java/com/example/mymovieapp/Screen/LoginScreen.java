package com.example.mymovieapp.Screen;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mymovieapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginScreen extends AppCompatActivity {
    private Button loginButton;
    private FirebaseAuth mAuth;
    private TextView tvRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
        mAuth = FirebaseAuth.getInstance();
        InitView();

        loginButton.setOnClickListener(v -> {
            String email = String.valueOf(((EditText) findViewById(R.id.Email)).getText());
            String password = String.valueOf(((EditText) findViewById(R.id.Password)).getText());

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(LoginScreen.this, "Login successfully.",
                                        Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginScreen.this, HomeScreen.class);
                                startActivity(intent);
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(LoginScreen.this, "Login failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        });

        tvRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginScreen.this, RegisterScreen.class);
            startActivity(intent);
        });
    }

    private void InitView() {
        loginButton = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);
    }
}
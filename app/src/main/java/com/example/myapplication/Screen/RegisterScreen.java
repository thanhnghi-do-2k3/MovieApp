package com.example.myapplication.Screen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterScreen extends AppCompatActivity {

    private Button regButton;
    private FirebaseAuth mAuth;
    private TextView tvLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_screen);
        mAuth = FirebaseAuth.getInstance();
        InitView();

        regButton.setOnClickListener(v -> {
            String email = String.valueOf(((EditText)findViewById(R.id.email)).getText());
            String password = String.valueOf(((EditText)findViewById(R.id.password)).getText());

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Email or password is empty", Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Toast.makeText(RegisterScreen.this, "Account Created", Toast.LENGTH_SHORT).show();
                                Log.d("RegisterScreen", "createUserWithEmail:success");
                                RegisterScreen.this.finish();
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(RegisterScreen.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        });

        tvLogin.setOnClickListener(v -> {
            RegisterScreen.this.finish();
        });
    }

    private void InitView() {
        regButton = findViewById(R.id.reg_button);
        tvLogin = findViewById(R.id.login_text);
    }
}
package com.example.hellobello;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignupActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseFirestore database;

    EditText emaiBox, passwordBox, nameBox;
    Button loginBtn, signupBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();

        emaiBox = findViewById(R.id.emailBox);
        passwordBox = findViewById(R.id.codeBox);
        nameBox = findViewById(R.id.nameBox);

        loginBtn = findViewById(R.id.joinBtn);
        signupBtn = findViewById(R.id.shareBtn);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, pass, name;
                email = emaiBox.getText().toString();
                pass = passwordBox.getText().toString();
                name = nameBox.getText().toString();

                //For Storing in Firestore Database
                User user = new User();
                user.setName(name);
                user.setEmail(email);
                user.setPass(pass);

                auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            database.collection("Users")
                                    .document().set(user);

                            Toast.makeText(SignupActivity.this, "Account has been Created", Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(SignupActivity.this,LoginActivity.class));

                        }
                        else{
                            Toast.makeText(SignupActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            }
        });


    }
}
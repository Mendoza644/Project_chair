package com.example.project_chair;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText mEditextName;
    private EditText mEditextEmail;
    private EditText mEditextPassword;
    private Button mButton;
    private String name = "";
    private String email = "";
    private String password = "";
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mEditextEmail = findViewById(R.id.editEmail);
        mEditextName = findViewById(R.id.editTexName);
        mEditextPassword = findViewById(R.id.editPassword);
        mButton = findViewById(R.id.btnregistro);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = mEditextName.getText().toString();
                email = mEditextEmail.getText().toString();
                password = mEditextPassword.getText().toString();

                if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty()) {

                    if (password.length() >= 6) {

                        registerUser();
                    } else {
                        Toast.makeText(MainActivity.this, "Debe Tener 6 Caracteres", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Toast.makeText(MainActivity.this, "Debe completar los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void registerUser() {
        try {


            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                                                            @Override
                                                                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                                                                if (task.isSuccessful()) {
                                                                                                    Map<String, Object> map = new HashMap<>();
                                                                                                    map.put("name", name);
                                                                                                    map.put("email", email);
                                                                                                    map.put("password", password);
                                                                                                    String id = mAuth.getCurrentUser().getUid();

                                                                                                    mDatabase.child("Users").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                        @Override
                                                                                                        public void onComplete(@NonNull Task<Void> task2) {
                                                                                                            if (task2.isSuccessful()) {
                                                                                                                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                                                                                                                finish();
                                                                                                            } else {
                                                                                                                Toast.makeText(MainActivity.this, "No se pudo crear los datos correctamente", Toast.LENGTH_SHORT).show();
                                                                                                            }
                                                                                                        }
                                                                                                    });


                                                                                                } else {
                                                                                                    Log.d( "debug ", task.getException().getMessage());
                                                                                                    Toast.makeText(MainActivity.this, "No se pudo registrar", Toast.LENGTH_SHORT).show();

                                                                                                }
                                                                                            }

                                                                                        }

            );


        } catch (Exception e) {
            Log.d("debug" ,e.getMessage());
        }
    }
}



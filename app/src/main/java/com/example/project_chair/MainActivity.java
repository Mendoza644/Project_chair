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
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    private EditText mEditextName;
    private EditText mEditextEmail;
    private EditText mEditextPassword;
    private EditText mEditextApellido;
    private EditText mEditextFecha_Nacimiento;
    private EditText mEditext_Telefono;
    private EditText mEditext_Cvv;
    private EditText mEditextPostal;
    private EditText mEditextDireccion;
    private EditText mEditextTarjeta;
    private EditText mEditextFecha_Vencimiento;
    private EditText mEditextPassword2;
    private Button mButton;
    private String name = "";
    private String email = "";
    private String password = "";
    private String apellido = "";
    private String direccion = "";
    private String postal = "";
    private String tarjeta = "";
    private String fecha_vencimiento = "";
    private String cvv = "";
    private String telefono = "";
    private String password2 = "";
    private String fecha_nacimiento = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mEditextEmail = findViewById(R.id.editEmail);
        mEditextName = findViewById(R.id.editTexName);
        mEditextPassword = findViewById(R.id.editPassword);
        mEditext_Cvv = findViewById(R.id.editTextCVV);
        mEditextApellido = findViewById(R.id.editTexApellido);
        mEditextFecha_Nacimiento = findViewById(R.id.editTextFecha);
        mEditext_Telefono = findViewById(R.id.editTextTelefono);
        mEditextTarjeta = findViewById(R.id.editTextTarjeta);
        mEditextFecha_Vencimiento = findViewById(R.id.editTextFechaVencimiento);
        mEditextDireccion = findViewById(R.id.editTextDireccion);
        mEditextPassword2 = findViewById(R.id.editPassword2);
        mEditextPostal = findViewById(R.id.editPostal);
        mButton = findViewById(R.id.btnregistro);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = mEditextName.getText().toString();
                apellido = mEditextApellido.getText().toString();
                email = mEditextEmail.getText().toString();
                password = mEditextPassword.getText().toString();
                password2 = mEditextPassword2.getText().toString();
                tarjeta = mEditextTarjeta.getText().toString();
                fecha_vencimiento = mEditextFecha_Vencimiento.getText().toString();
                cvv = mEditext_Cvv.getText().toString();
                telefono = mEditext_Telefono.getText().toString();
                postal = mEditextPostal.getText().toString();
                direccion = mEditextDireccion.getText().toString();
                fecha_nacimiento = mEditextFecha_Nacimiento.getText().toString();
                if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty()) {

                    if (password.length() >= 8) {

                        registerUser();
                    } else {
                        Toast.makeText(MainActivity.this, "Debe Tener 8 Caracteres", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Toast.makeText(MainActivity.this, "Debe completar los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void registerUser() {
        try {


            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                               @Override
                                               public void onComplete(@NonNull Task<AuthResult> task) {
                                                   if (task.isSuccessful()) {
                                                       Map<String, Object> map = new HashMap<>();
                                                       map.put("name", name);
                                                       map.put("email", email);
                                                       map.put("password", password);
                                                       String id = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

                                                       Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                                                       startActivity(intent);

                                                   } else {
                                                       Log.d("debug ", Objects.requireNonNull(Objects.requireNonNull(task.getException()).getMessage()));
                                                       Toast.makeText(MainActivity.this, "No se pudo registrar", Toast.LENGTH_SHORT).show();

                                                   }
                                               }

                                           }

                    );


        } catch (Exception e) {
            Log.d("debug", e.getMessage());
        }
    }
}
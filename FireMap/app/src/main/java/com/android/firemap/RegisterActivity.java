package com.android.firemap;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private EditText firstnameUsr;
    private EditText lastnameUsr;
    private EditText emailUsr;
    private EditText psswdUsr;
    private Button signupButton;
    private FirebaseAuth mAuth;
    private ProgressDialog progDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        signupButton = (Button) findViewById(R.id.buttonRegistrarse);
        mAuth = FirebaseAuth.getInstance();
        progDialog = new ProgressDialog(this);
        initiateEditText();
        
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

    }

    private void registerUser() {
        String firstname = firstnameUsr.getText().toString().trim();
        String lastname = lastnameUsr.getText().toString().trim();
        String email = emailUsr.getText().toString().trim();
        String password = psswdUsr.getText().toString().trim();

        if (isFirstNameValid(firstname) && isLastNameValid(lastname) &&
                isEmailValid(email) && isPasswordValid(password)) {
            progDialog.setMessage("Registrando usuario...");
            progDialog.show();
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this,
                    new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(getBaseContext(), R.string.regist_success_txt, Toast.LENGTH_LONG).show();
                        Intent i = new Intent(getBaseContext(), WelcomeActivity.class);
                        startActivity(i);
                    } else {
                        Toast.makeText(getBaseContext(), R.string.regist_failed_txt, Toast.LENGTH_LONG).show();
                        progDialog.dismiss();
                    }
                }
            });

        }

    }

    private boolean isFirstNameValid(String firstname) {
        return !firstname.isEmpty();
    }

    private boolean isLastNameValid(String lastname) {
        return !lastname.isEmpty();
    }

    private boolean isEmailValid(String email) {
        if (email == null) { return false;
        } else { return Patterns.EMAIL_ADDRESS.matcher(email).matches(); }
    }

    private boolean isPasswordValid(String password) { return password.length() > 8; }

    private void initiateEditText() {
        firstnameUsr = (EditText) findViewById(R.id.nombreET);
        lastnameUsr = (EditText) findViewById(R.id.apellidoET);
        emailUsr = (EditText) findViewById(R.id.correoET);
        psswdUsr = (EditText) findViewById(R.id.passwordET);
    }
}

package com.example.getalcohall;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    EditText email,pass;
    ProgressDialog pd;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        pd=new ProgressDialog(this);
        pd.setMessage("Please wait...");
        pd.setCancelable(false);
        email=findViewById(R.id.login_email_edittext);
        pass=findViewById(R.id.login_password_edittext);

        auth=FirebaseAuth.getInstance();
        findViewById(R.id.login_sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email.getText().toString().isEmpty()){
                    email.setError("Fill it");
                    return ;
                }
                if(pass.getText().toString().isEmpty()){
                    pass.setError("Fill it");
                    return ;
                }

                pd.show();



                auth.signInWithEmailAndPassword(email.getText().toString(),pass.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            startActivity(new Intent(Login.this, Home.class));
                            finish();
                        }else{
                            Toast.makeText(Login.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        pd.dismiss();
                    }
                });



            }
        });
        findViewById(R.id.login_signup_textview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Signup.class));
                finish();
            }
        });

    }
}
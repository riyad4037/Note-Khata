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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity {
    EditText name,uemail,pass,cpass;
    FirebaseAuth auth;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    ProgressDialog pd ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        name=findViewById(R.id.signup_full_name_edittext);
        uemail=findViewById(R.id.signup_email_edittext);
        pass=findViewById(R.id.signup_password_edittext);
        cpass=findViewById(R.id.signup_confirm_password);
        auth=FirebaseAuth.getInstance();
        pd=new ProgressDialog(Signup.this);
        pd.setMessage("Please wait...");
        pd.setCancelable(false);
        findViewById(R.id.signup_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.getText().toString().isEmpty()){
                    name.setError("Fill it");
                    return ;
                }
                if(uemail.getText().toString().isEmpty()){
                    uemail.setError("Fill it");
                    return ;
                }
                if(pass.getText().toString().isEmpty()){
                    pass.setError("Fill it");
                    return ;
                }
                if(cpass.getText().toString().isEmpty()){
                    cpass.setError("Fill it");
                    return ;
                }

                if(!pass.getText().toString().equals(cpass.getText().toString())){
                    pass.setError("Didn't match");
                    cpass.setError("Didn't match");
                    return;
                }
                pd.show();

                auth.createUserWithEmailAndPassword(uemail.getText().toString(),pass.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            auth = FirebaseAuth.getInstance();

                            FirebaseUser user = auth.getCurrentUser();

                            String uid = user.getUid();

                            String signupUserName = name.getText().toString();
                            String signupUserEmail = uemail.getText().toString();
                            String signupUserPassword = pass.getText().toString();

                            Signup_data_gatter_setter signupData = new Signup_data_gatter_setter(signupUserName,signupUserEmail,signupUserPassword);

                            databaseReference.child("UserInfo").child(uid).setValue(signupData).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    System.out.println("Successful");
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    System.out.println("Failed!");
                                }
                            });

                            startActivity(new Intent(Signup.this,Login.class));
                            finish();
                        }else{
                            Toast.makeText(Signup.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        pd.dismiss();
                    }
                });



            }
        });
        findViewById(R.id.signup_signin_textview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Signup.this,Login.class));
                finish();
            }
        });
    }
}
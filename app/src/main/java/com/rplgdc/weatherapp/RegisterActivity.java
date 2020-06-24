package com.rplgdc.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.firebase.ui.auth.data.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText edYourname, edUsername, edEmail, edPassword, edVpassword;
    private Button btnRegistrasi;
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private static final String USER = "user";
    private static final String TAG ="RegisterActivity";
    private DashboardActivity user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Casting Views
        edYourname = (EditText) findViewById(R.id.ed_yourname);
        edUsername = (EditText) findViewById(R.id.ed_username);
        edEmail = (EditText) findViewById(R.id.ed_email);
        edPassword = (EditText) findViewById(R.id.ed_password);
        edVpassword = (EditText) findViewById(R.id.ed_vpassword);
        btnRegistrasi = (Button) findViewById(R.id.btn_signup);

        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference(USER);
        mAuth = FirebaseAuth.getInstance();

        btnRegistrasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edEmail.getText().toString();
                String password = edPassword.getText().toString();
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                    Toast.makeText(getApplicationContext(), "Enter email and password",
                            Toast.LENGTH_SHORT).show();
//                    if (password.length()<6){
//                        Toast.makeText(RegisterActivity.this,"Password too short",
//                                Toast.LENGTH_SHORT).show();
//                    }
                    return;
                }
                String yourname = edYourname.getText().toString();
                String username = edUsername.getText().toString();
                user = new DashboardActivity (email,password,yourname,username);
                registerUser(email,password);
            }
        });
    }
    public void registerUser(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }
    public void updateUI (FirebaseUser currentUser){
        String keyId = mDatabase.push().getKey();
        mDatabase.child(keyId).setValue(user);
        Intent loginIntent = new Intent(this,LoginActivity.class);
        startActivity(loginIntent);

    }
}

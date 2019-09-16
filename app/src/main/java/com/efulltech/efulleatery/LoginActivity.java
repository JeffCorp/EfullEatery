//package com.efulltech.efulleatery;
//
//import android.content.Intent;
//import android.support.annotation.NonNull;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//
//public class LoginActivity extends AppCompatActivity {
//
//    private static final String TAG = "LoginActivity";
//
//    private FirebaseAuth mAuth;
//    private EditText emailField, passwordField;
//
//    private Button btnLogin;
//
//    private FirebaseAuth.AuthStateListener mAuthListener;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
//
//
//        // Initialize Firebase Auth
//        mAuth = FirebaseAuth.getInstance();
//
//        emailField = (EditText) findViewById(R.id.usernamen);
//        passwordField = (EditText) findViewById((R.id.passwordn));
//
//        btnLogin = (Button) findViewById(R.id.nlogin);
//
//        mAuthListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                if (firebaseAuth.getCurrentUser() != null){
//                    startActivity(new Intent(LoginActivity.this, TableActivity.class));
//                }
//            }
//        };
//
//        btnLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startSignIn();
//
//            }
//        });
//    }
//
//    protected void onStart(){
//        super.onStart();
//
//        mAuth.addAuthStateListener(mAuthListener);
//    }
//
//    private void startSignIn(){
//        String email = emailField.getText().toString();
//        String password = passwordField.getText().toString();
//
//        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
//            Toast.makeText(LoginActivity.this, "Please input necessary details", Toast.LENGTH_LONG).show();
//        }else {
//
//            mAuth.signInWithEmailAndPassword(email, password)
//                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            if (task.isSuccessful()) {
//                                // Sign in success, update UI with the signed-in user's information
//                                Log.d(TAG, "signInWithEmail:success");
//                                FirebaseUser user = mAuth.getCurrentUser();
//                            } else {
//                                // If sign in fails, display a message to the user.
//                                Log.w(TAG, "signInWithEmail:failure", task.getException());
//                                Toast.makeText(LoginActivity.this, "Authentication failed.",
//                                        Toast.LENGTH_SHORT).show();
//                            }
//
//                            // ...
//                        }
//
//                        private void updateUI(FirebaseUser user) {
//                        }
//                    });
//        }
//    }
//}

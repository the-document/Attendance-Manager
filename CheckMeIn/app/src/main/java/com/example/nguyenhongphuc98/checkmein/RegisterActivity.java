package com.example.nguyenhongphuc98.checkmein;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    EditText edtUsername;
    EditText edtPassword;
    EditText edtPasswordConfirmation;
    EditText edtEmail;
    Button btnRegister;
    TextView txtLinkToLogin;
    FirebaseAuth mAuth;
    private static final String TAG = "EmailPassword";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Ánh xạ.
        edtUsername = (EditText)findViewById(R.id.register_edtUsername);
        edtPassword = (EditText)findViewById(R.id.register_edtPassword);
        edtPasswordConfirmation = (EditText)findViewById(R.id.register_edtConfirmPassword);
        edtEmail = (EditText)findViewById(R.id.register_edtEmail);
        btnRegister = (Button)findViewById(R.id.register_btnRegister);
        txtLinkToLogin = (TextView)findViewById(R.id.register_linkToLogin);

        // [START Initialize_auth]
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // [END Initialize_auth]

        //Lấy dữ liệu từ lúc chúng ta login.
        Intent intent = getIntent();
        String inputtedUsername = intent.getStringExtra(LoginActivity.EXTRA_USERNAME);
        String inputtedPassword = intent.getStringExtra(LoginActivity.EXTRA_PASSWORD);

        //Update lên luôn để người dùng đỡ phải nhập lại nhiều lần.
        edtUsername.setText(inputtedUsername);
        edtPassword.setText(inputtedPassword);

        //Sự kiện.
            txtLinkToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLoginActivity();
            }
        });

            btnRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    registerProcess(edtUsername.getText().toString(), edtPassword.getText().toString());
                }
            });

    }
    protected void goToLoginActivity()
    {
        Intent intent = new Intent(this, LoginActivity.class);
        String userName = edtUsername.getText().toString();
        String passWord = edtPassword.getText().toString();
        intent.putExtra(LoginActivity.EXTRA_USERNAME, userName);
        intent.putExtra(LoginActivity.EXTRA_PASSWORD, passWord);
        startActivityForResult(intent, LoginActivity.REGISTER_NOT);
    }
    protected void registerProcess(String userName, String passWord)
    {
        Log.d(TAG, "createAccount:" + userName);
        if (!validateForm()) {
            return;
        }

        final Intent intent = new Intent(this, LoginActivity.class);
        //intent.putExtra(LoginActivity.EXTRA_USERNAME, userName);
        //intent.putExtra(LoginActivity.EXTRA_PASSWORD, passWord);

        // [START create_user_with_userName]
        mAuth.createUserWithEmailAndPassword(userName, passWord)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");

                            FirebaseUser user = mAuth.getCurrentUser();
                            //startActivityForResult(intent, LoginActivity.REGISTER_SUCCESS);
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // [START_EXCLUDE]
                        //hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END create_user_with_email]
    }
    private boolean validateForm() {
        boolean valid = true;

        String userName = edtUsername.getText().toString();
        if (TextUtils.isEmpty(userName)) {
            edtUsername.setError("Required.");
            valid = false;
        } else {
            edtUsername.setError(null);
        }

        String password = edtPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            edtPassword.setError("Required.");
            valid = false;
        } else {
            edtPassword.setError(null);
        }

        return valid;
    }
}

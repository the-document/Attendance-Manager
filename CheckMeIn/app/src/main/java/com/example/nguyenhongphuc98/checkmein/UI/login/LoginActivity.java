package com.example.nguyenhongphuc98.checkmein.UI.login;

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

import com.example.nguyenhongphuc98.checkmein.MainActivity;
import com.example.nguyenhongphuc98.checkmein.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements LoginPresenter.View {
    TextView txtLinkToRegister;
    TextView txtLinkToForgotPass;

    EditText edtUsername;
    EditText edtPassword;

    Button btnLogin;

    LoginPresenter loginPresenter;

    private FirebaseAuth mAuth;
    private static final String TAG = "EmailPassword";

    boolean isRegisterSuccessful = false;
    public static final String EXTRA_USERNAME = ".LOGIN.USERNAME";
    public static final String EXTRA_PASSWORD = ".LOGIN.PASSWORD";
    public static final int REGISTER_SUCCESS = 1;
    public static final int REGISTER_NOT = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginPresenter = new LoginPresenter(this);
        final Intent intent = new Intent(this, MainActivity.class);

        //Ánh xạ.
        txtLinkToRegister = (TextView)findViewById(R.id.txtLinkToRegister);
        txtLinkToForgotPass = (TextView)findViewById(R.id.txtLinkToForgotPass);
        edtUsername = (EditText)findViewById(R.id.login_edtUsername);
        edtPassword = (EditText)findViewById(R.id.login_edtPassword);
        btnLogin = (Button)findViewById(R.id.login_btnLogin);
        mAuth = FirebaseAuth.getInstance();

        //Sư kiện.
        txtLinkToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoToRegisterActivity(v);
            }
        });
        txtLinkToForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoToResetPasswordActivity(v);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edtUsername.getText().toString().isEmpty() && !edtPassword.getText().toString().isEmpty()) {
                    loginPresenter.setAccount(edtUsername.getText().toString(), edtPassword.getText().toString());
                    loginPresenter.LoginProcess(loginPresenter.getEmail() , loginPresenter.getPassword());
                    if (loginPresenter.CheckLoginStatus() || true) {
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(LoginActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(LoginActivity.this, "Please make sure to enter valid email and password!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }
    // [END on_start_check_user]

    private void ProcessLoginData(String email, String password) {
        final Intent intent = new Intent(this, MainActivity.class);

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            startActivity(intent);
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // [START_EXCLUDE]
                        /*if (!task.isSuccessful()) {
                            mStatusTextView.setText(R.string.auth_failed);
                        }
                        hideProgressDialog();*/
                        // [END_EXCLUDE]
                    }
                });
        // [END sign_in_with_email]
    }

    protected void GoToRegisterActivity(View view)
    {
        Intent intent = new Intent(this, RegisterActivity.class);
        EditText userName = (EditText)findViewById(R.id.login_edtUsername);
        EditText passWord = (EditText)findViewById(R.id.login_edtPassword);
        String messageUserName = userName.getText().toString();
        String messagePassword = passWord.getText().toString();
        intent.putExtra(EXTRA_USERNAME, messageUserName);
        intent.putExtra(EXTRA_PASSWORD, messagePassword);
        startActivity(intent);
    }
    protected void GoToResetPasswordActivity(View view)
    {
        Intent intent = new Intent(this, ResetPasswordActivity.class);
        startActivity(intent);
    }
    protected void SetupUsernamePassword(String userName, String passWord, boolean autoLogin)
    {
        edtUsername.setText(userName);
        edtPassword.setText(passWord);
        if (autoLogin)
            btnLogin.performClick();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK)
        {
            boolean wantAutoLogin = false;
            if (requestCode == REGISTER_SUCCESS)
            {
                wantAutoLogin = true;
            }
            else
            {
                wantAutoLogin = false;
            }
            SetupUsernamePassword(data.getStringExtra(EXTRA_USERNAME), data.getStringExtra((EXTRA_PASSWORD)), wantAutoLogin);
        }
        else
        {
            Toast toast = Toast.makeText(getApplicationContext(), "Error receive intent from register", Toast.LENGTH_LONG);
            toast.show();
        }
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

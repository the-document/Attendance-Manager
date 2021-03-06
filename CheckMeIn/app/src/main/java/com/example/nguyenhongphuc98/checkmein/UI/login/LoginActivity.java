package com.example.nguyenhongphuc98.checkmein.UI.login;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nguyenhongphuc98.checkmein.Data.DataCenter;
import com.example.nguyenhongphuc98.checkmein.Data.network.DataManager;
import com.example.nguyenhongphuc98.checkmein.MainActivity;
import com.example.nguyenhongphuc98.checkmein.R;
import com.example.nguyenhongphuc98.checkmein.UI.reset_password.ResetPasswordActivity;
import com.example.nguyenhongphuc98.checkmein.UI.sign_up.RegisterActivity;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements ILoginView {
    TextView txtLinkToRegister;
    TextView txtLinkToForgotPass;

    EditText edtUsername;
    EditText edtPassword;

    Button btnLogin;

    ProgressBar progressBar;

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

        loginPresenter = new LoginPresenter(this);

        getSupportActionBar().hide();

        Intent intent = new Intent(this, MainActivity.class);

//        if (loginPresenter.CheckLoginStatus()){
//            startActivity(intent);
//        }

        setContentView(R.layout.activity_login);

        //Ánh xạ.
        txtLinkToRegister = (TextView)findViewById(R.id.txtLinkToRegister);
        txtLinkToForgotPass = (TextView)findViewById(R.id.txtLinkToForgotPass);
        edtUsername = (EditText)findViewById(R.id.login_edtUsername);
        edtPassword = (EditText)findViewById(R.id.login_edtPassword);
        btnLogin = (Button)findViewById(R.id.login_btnLogin);
        progressBar=findViewById(R.id.progressbarLogin);
        mAuth = FirebaseAuth.getInstance();

        //Sư kiện.
        txtLinkToRegister.setOnClickListener(v -> GoToRegisterActivity(v));
        txtLinkToForgotPass.setOnClickListener(v -> GoToResetPasswordActivity(v));
        btnLogin.setOnClickListener(v -> {
            if (ValidateForm())
            {
                //async have to complete before check mail
                progressBar.setIndeterminate(true);
                progressBar.setVisibility(View.VISIBLE);
                loginPresenter.LoginProcess(edtUsername.getText().toString(), edtPassword.getText().toString());
                //auto callback when process done

            }

        });

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    protected void GoToRegisterActivity(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        EditText userName = (EditText)findViewById(R.id.login_edtUsername);
        EditText passWord = (EditText)findViewById(R.id.login_edtPassword);
        String messageUserName = userName.getText().toString();
        String messagePassword = passWord.getText().toString();
        intent.putExtra(EXTRA_USERNAME, messageUserName);
        intent.putExtra(EXTRA_PASSWORD, messagePassword);
        startActivity(intent);
    }

    protected void GoToResetPasswordActivity(View view) {
        startActivity(new Intent(this, ResetPasswordActivity.class));
    }

    @Override
    public boolean ValidateForm() {
        return loginPresenter.ValidateForm(edtUsername, edtPassword);
    }



    public void OnLoadPersonToDatacenterComplete() {
        Intent intent = new Intent(this, MainActivity.class);
        Log.e("Login","user: "+ DataCenter.UserID +" - "+ DataCenter.UserDisplayName);
        progressBar.setIndeterminate(false);
        progressBar.setVisibility(View.INVISIBLE);
        startActivity(intent);
    }
}
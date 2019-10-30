package com.example.nguyenhongphuc98.checkmein;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nguyenhongphuc98.checkmein.OCR.PreProcess;
import com.example.nguyenhongphuc98.checkmein.OCR.Tesseract;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

public class LoginActivity extends AppCompatActivity {
    TextView txtLinkToRegister;
    TextView txtLinkToForgotPass;

    EditText edtUsername;
    EditText edtPassword;

    Button btnLogin;


    boolean isRegisterSuccessful = false;
    public static final String EXTRA_USERNAME = ".LOGIN.USERNAME";
    public static final String EXTRA_PASSWORD = ".LOGIN.PASSWORD";
    public static final int REGISTER_SUCCESS = 1;
    public static final int REGISTER_NOT = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Ánh xạ.
        txtLinkToRegister = (TextView)findViewById(R.id.txtLinkToRegister);
        txtLinkToForgotPass = (TextView)findViewById(R.id.txtLinkToForgotPass);
        edtUsername = (EditText)findViewById(R.id.login_edtUsername);
        edtPassword = (EditText)findViewById(R.id.login_edtPassword);
        btnLogin = (Button)findViewById(R.id.login_btnLogin);

        if (!OpenCVLoader.initDebug()) {
            Toast toast = Toast.makeText(getApplicationContext(), "canload opencv !", Toast.LENGTH_SHORT);
        }

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
                Toast toast = Toast.makeText(getApplicationContext(), "Login button clicked !", Toast.LENGTH_LONG);
                toast.show();
                ProcessLoginData();
            }
        });
    }

    private void ProcessLoginData() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
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
}

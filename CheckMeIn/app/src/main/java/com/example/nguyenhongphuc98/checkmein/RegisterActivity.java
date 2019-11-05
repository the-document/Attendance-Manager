package com.example.nguyenhongphuc98.checkmein;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {
    EditText edtUsername;
    EditText edtPassword;
    EditText edtPasswordConfirmation;
    EditText edtEmail;
    Button btnRegister;
    TextView txtLinkToLogin;
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
                goToLoginActivity(v);
            }
        });

            btnRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    registerProcess(v);
                }
            });

    }
    protected void goToLoginActivity(View view)
    {
        Intent intent = new Intent(this, LoginActivity.class);
        String userName = edtUsername.getText().toString();
        String passWord = edtPassword.getText().toString();
        intent.putExtra(LoginActivity.EXTRA_USERNAME, userName);
        intent.putExtra(LoginActivity.EXTRA_PASSWORD, passWord);
        startActivityForResult(intent, LoginActivity.REGISTER_NOT);
    }
    protected void registerProcess(View view)
    {
        Intent intent = new Intent(this, LoginActivity.class);
        String userName = edtUsername.getText().toString();
        String passWord = edtPassword.getText().toString();
        intent.putExtra(LoginActivity.EXTRA_USERNAME, userName);
        intent.putExtra(LoginActivity.EXTRA_PASSWORD, passWord);
        startActivityForResult(intent, LoginActivity.REGISTER_SUCCESS);
    }
}

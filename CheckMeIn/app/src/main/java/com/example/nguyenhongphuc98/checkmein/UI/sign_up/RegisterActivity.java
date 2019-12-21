package com.example.nguyenhongphuc98.checkmein.UI.sign_up;

import android.content.Intent;
import android.os.Bundle;

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
import com.example.nguyenhongphuc98.checkmein.UI.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RegisterActivity extends AppCompatActivity implements IRegisterView {
    EditText edtUsername;
    EditText edtPassword;
    EditText edtPasswordConfirmation;
    EditText edtEmail;
    Button btnRegister;
    TextView txtLinkToLogin;

    RegisterPresenter registerPresenter;

    private static final String TAG = "EmailPassword";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerPresenter = new RegisterPresenter(this);

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
        edtEmail.setText(inputtedUsername);
        edtPassword.setText(inputtedPassword);

        //Sự kiện.
            txtLinkToLogin.setOnClickListener(v -> GoToLoginScreen());

            btnRegister.setOnClickListener(v -> registerProcess(edtEmail.getText().toString(), edtPassword.getText().toString()));

    }

    private void registerProcess(String email, String passWord) {
        if (!ValidateForm()) {
            return;
        }
        if (registerPresenter.SignUpProcess(email, passWord)) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date now = new Date();
            try {
                registerPresenter.writeNewPerson(edtUsername.getText().toString());
                registerPresenter.writeNewAccount(registerPresenter.getCurrentUser().getUid(), email, edtUsername.getText().toString(), passWord, formatter.format(now));

                Toast.makeText(RegisterActivity.this, "Create user success", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, LoginActivity.class));

                Log.d(TAG, "addAccountInfo:success");
            } catch (NumberFormatException nfe) {
                Log.w(TAG, "addAccountInfo:failure");
                Toast.makeText(RegisterActivity.this, "Invalid MSSV", Toast.LENGTH_SHORT).show();
            }
        }
        else
            Log.w(TAG, "addAccountInfo:failure");
    }


    public boolean ValidateForm() {
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

        String passwordConfirmation = edtPasswordConfirmation.getText().toString();
        if (!password.equals(passwordConfirmation)){
            edtPasswordConfirmation.setError("Password does not match.");
            valid = false;
        }

        return valid;
    }

    public void GoToLoginScreen() {
        Intent intent = new Intent(this, LoginActivity.class);
        String email = edtEmail.getText().toString();
        String passWord = edtPassword.getText().toString();
        intent.putExtra(LoginActivity.EXTRA_USERNAME, email);
        intent.putExtra(LoginActivity.EXTRA_PASSWORD, passWord);
        startActivityForResult(intent, LoginActivity.REGISTER_NOT);
    }
}

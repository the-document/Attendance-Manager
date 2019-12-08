package com.example.nguyenhongphuc98.checkmein.UI.reset_password;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nguyenhongphuc98.checkmein.R;
import com.example.nguyenhongphuc98.checkmein.UI.login.LoginActivity;
import com.example.nguyenhongphuc98.checkmein.UI.sign_up.RegisterActivity;

public class ResetPasswordActivity extends AppCompatActivity implements IResetPasswordView {
    ResetPasswordPresenter presenter;

    EditText edtEmail;
    Button btnResetPass;
    TextView txtLinkToLogin;

    private static final String TAG = "Reset password";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        presenter = new ResetPasswordPresenter(this);

        //Ánh xạ.
        edtEmail = (EditText)findViewById(R.id.rspass_edtEmail);
        btnResetPass = (Button)findViewById(R.id.rspass_btnResetPass);
        txtLinkToLogin = (TextView)findViewById(R.id.rspass_linkLogin);


        //Sự kiện.
        btnResetPass.setOnClickListener(v -> {
            if (presenter.sendMailResetPassword(edtEmail.getText().toString()).isSuccessful()){
                Toast. makeText(ResetPasswordActivity.this,"Invalid MSSV",Toast. LENGTH_SHORT).show();
                Log.d(TAG, "createUserWithEmail:success");
            }
            else {
                GoToLoginActivity();
                Log.w(TAG, "createUserWithEmail:failure");
            }
        });

        txtLinkToLogin.setOnClickListener(v -> GoToLoginActivity());
    }

    public void GoToLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}

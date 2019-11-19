package com.example.nguyenhongphuc98.checkmein.UI.login;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.nguyenhongphuc98.checkmein.R;
import com.example.nguyenhongphuc98.checkmein.UI.login.LoginActivity;

public class ResetPasswordActivity extends AppCompatActivity {

    EditText edtEmail;
    Button btnResetPass;
    TextView txtLinkToLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        //Ánh xạ.
        edtEmail = (EditText)findViewById(R.id.rspass_edtEmail);
        btnResetPass = (Button)findViewById(R.id.rspass_btnResetPass);
        txtLinkToLogin = (TextView)findViewById(R.id.rspass_linkLogin);


        //Sự kiện.
        txtLinkToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoToLoginActivity(v);
            }
        });
    }

    private void GoToLoginActivity(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}

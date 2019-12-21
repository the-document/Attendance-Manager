package com.example.nguyenhongphuc98.checkmein.UI.mail;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nguyenhongphuc98.checkmein.UI.mail.date_time_dialog.DatetimeDialogFragment;
import com.example.nguyenhongphuc98.checkmein.R;

public class SendEmailFragment extends Fragment {
    View btnClock;
    Button btnSendMail;
    EditText edtSubject;
    EditText edtMessage;
    SendEmailPresenter presenter;

    String listRecipients = "tihtk.98@gmail.com, 16520713@gm.uit.edu.vn";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new SendEmailPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_send_email, container, false);

        edtSubject = (EditText) view.findViewById(R.id.etSubject);
        edtSubject.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                edtSubject.setText("");
                return false;
            }
        });

        edtMessage = (EditText) view.findViewById(R.id.etMessage);
        edtMessage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                edtMessage.setText("");
                return false;
            }
        });

        btnClock = (View) view.findViewById(R.id.btnClock);
        btnClock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatetimeDialogFragment dtDialog = new DatetimeDialogFragment();
                dtDialog.show(getFragmentManager(), "dialog");
            }
        });

        btnSendMail = (Button) view.findViewById(R.id.btnSend);
        btnSendMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  presenter.sendMaiProcess(
                        listRecipients.split(","),
                        edtSubject.getText().toString(),
                        edtMessage.getText().toString());
                try {
                    startActivity(Intent.createChooser(intent, "Choose application for sending mail"));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getActivity(), "There are no email clients", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}

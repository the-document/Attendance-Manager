package com.example.nguyenhongphuc98.checkmein.UI.event_list_join.extend_dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.nguyenhongphuc98.checkmein.Data.db.model.Attendance;
import com.example.nguyenhongphuc98.checkmein.R;
import com.example.nguyenhongphuc98.checkmein.UI.mail.SendEmailFragment;

import java.io.FileReader;
import java.util.List;

public class ExtendDialogFragment extends DialogFragment {
    SendEmailFragment sendEmailFragment;
    ExtendDialogPresenter presenter;
    List<Attendance> lsAttendance;

    public ExtendDialogFragment(List<Attendance> lsAttendance) {
        sendEmailFragment = new SendEmailFragment(lsAttendance);
        presenter = new ExtendDialogPresenter(this);
        this.lsAttendance = lsAttendance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_extend_popup, container, false);
        final TextView tvSendAttendance = view.findViewById(R.id.tvAttendance);
        tvSendAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransition=getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransition.replace(R.id.fragment_container,sendEmailFragment);
                fragmentTransition.commit();
            }
        });

        final TextView tvImport = view.findViewById(R.id.tvImport);
        tvImport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImportDialogFragment dtDialog = new ImportDialogFragment();
                dtDialog.show(getFragmentManager(), "dialog");
            }
        });

        EditText edtMssv = view.findViewById(R.id.edtMssv);
        edtMssv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtMssv.setText("");
            }
        });

        ImageView imgMssv = view.findViewById(R.id.imgEditMssv);
        imgMssv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mssv = edtMssv.getText().toString();
                if (!mssv.isEmpty() && mssv.length() == 8){
                    if (CheckMssv(mssv))
                        presenter.SaveAttendance(edtMssv.getText().toString());
                    else
                        Toast.makeText(getContext(), "Mssv đã tồn tại", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getContext(), "Mssv không hợp lệ", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    public Boolean CheckMssv(String mssv) {
        for (int i=0; i<lsAttendance.size(); i++){
            if (lsAttendance.get(i).getUser_key() == mssv)
                return false;
        }
        return true;
    }
}

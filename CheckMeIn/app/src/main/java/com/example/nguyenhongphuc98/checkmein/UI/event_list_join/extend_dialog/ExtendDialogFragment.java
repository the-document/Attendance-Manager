package com.example.nguyenhongphuc98.checkmein.UI.event_list_join.extend_dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.nguyenhongphuc98.checkmein.R;
import com.example.nguyenhongphuc98.checkmein.UI.mail.SendEmailFragment;

import java.io.FileReader;

public class ExtendDialogFragment extends DialogFragment {
    SendEmailFragment sendEmailFragment;

    public ExtendDialogFragment() {
        sendEmailFragment = new SendEmailFragment();
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

        final TextView tvSendAbsence = view.findViewById(R.id.tvAbsence);

        final TextView tvSendAll = view.findViewById(R.id.tvAll);

        final TextView tvImport = view.findViewById(R.id.tvImport);
        tvImport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImportDialogFragment dtDialog = new ImportDialogFragment();
                dtDialog.show(getFragmentManager(), "dialog");
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
}

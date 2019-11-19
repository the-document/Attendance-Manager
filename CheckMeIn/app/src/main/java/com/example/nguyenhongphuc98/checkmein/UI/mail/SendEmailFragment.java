package com.example.nguyenhongphuc98.checkmein.UI.mail;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nguyenhongphuc98.checkmein.DatetimeDialogFragment;
import com.example.nguyenhongphuc98.checkmein.R;

public class SendEmailFragment extends Fragment {
    View btnClock;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_send_email, container, false);
        btnClock = (View) view.findViewById(R.id.btnClock);
        btnClock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatetimeDialogFragment dtDialog = new DatetimeDialogFragment();
                dtDialog.show(getFragmentManager(), "dialog");
            }
        });
        return view;
    }
}

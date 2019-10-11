package com.example.nguyenhongphuc98.checkmein;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
                /*final Dialog dtDialogue = new Dialog(getActivity(), android.R.style.Theme_Black_NoTitleBar);
                dtDialogue.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(100, 0, 0, 0)));
                dtDialogue.setContentView(R.layout.datetime_popup);
                dtDialogue.setCancelable(true);
                dtDialogue.show();*/
                DatetimeDialogFragment dtDialog = new DatetimeDialogFragment();
                dtDialog.show(getFragmentManager(), "dialog");
            }
        });
        return view;
    }
}

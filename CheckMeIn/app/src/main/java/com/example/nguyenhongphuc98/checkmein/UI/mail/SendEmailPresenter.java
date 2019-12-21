package com.example.nguyenhongphuc98.checkmein.UI.mail;

import android.content.Intent;

public class SendEmailPresenter {
    public Intent sendMaiProcess(String recipients[], String subject, String message) {
        Intent intent = new Intent(Intent.ACTION_SEND);

        intent.putExtra(Intent.EXTRA_EMAIL , recipients);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT , message);

        intent.setType("message/rfc822");
        return intent;
    }
}
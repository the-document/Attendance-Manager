package com.example.nguyenhongphuc98.checkmein.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import android.widget.ArrayAdapter;

import com.example.nguyenhongphuc98.checkmein.model.Answer;

import java.util.List;

public class AnswerListCustomAdapter extends ArrayAdapter<Answer> {
    public AnswerListCustomAdapter(@NonNull Context context, int resource, @NonNull List<Answer> objects) {
        super(context, resource, objects);
    }
}

package com.example.nguyenhongphuc98.checkmein.Adapter;

import android.content.Context;

import com.example.nguyenhongphuc98.checkmein.Data.db.model.Question;

import java.util.ArrayList;

import androidx.annotation.NonNull;

public class QuestionListCustomAdapterForParticipant extends QuestionListCustomAdapter {
    public QuestionListCustomAdapterForParticipant(@NonNull Context context, int resource, @NonNull ArrayList<Question> objects, boolean isAnswerClickable) {
        super(context, resource, objects, isAnswerClickable);
    }

}

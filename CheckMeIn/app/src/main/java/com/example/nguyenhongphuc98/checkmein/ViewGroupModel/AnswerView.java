package com.example.nguyenhongphuc98.checkmein.ViewGroupModel;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.nguyenhongphuc98.checkmein.NewQuestionDialogFragment;
import com.example.nguyenhongphuc98.checkmein.R;

import java.util.ArrayList;

public class AnswerView extends LinearLayout {

    Context mContext;
    EditText edtAnswerText;
    Button btnRemoveAnswer;

    Integer answerIndex;

    public AnswerView(Context context) {
        super(context);
        init(context, null);
    }

    public AnswerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public AnswerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public AnswerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs)
    {
        this.mContext = context;
        setOrientation(LinearLayout.HORIZONTAL);
        inflate(mContext, R.layout.view_group_answer_view, this);
        //Ánh xạ.
        AssignValues();
    }

    private void AssignValues()
    {
        edtAnswerText = (EditText)findViewById(R.id.view_group_answer_view_edt_answer_text);
        btnRemoveAnswer = (Button)findViewById(R.id.view_group_answer_view_button_remove_answer);
    }

    public void setOnClickRemoveAnswerListener(OnClickListener listener)
    {
        //Thử code kia xem sao.
        btnRemoveAnswer.setOnClickListener(listener);

//        btnRemoveAnswer.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                arrAnswerView.remove(answerIndex);
//                ViewGroup parentView = (ViewGroup)v.getParent();
//                parentView.removeView(v);
//            }
//        });

    }

    public void setAnswerText(String text)
    {
        edtAnswerText.setText(text);
    }

    public String getAnswerText()
    {
        return edtAnswerText.getText().toString();
    }

    public void setAnswerIndex(Integer index)
    {
        answerIndex = index;
        setAnswerHint("Câu trả lời thứ " + (index + 1) );
    }

    public void setAnswerHint(String text)
    {
        edtAnswerText.setHint(text);
    }

    public Integer getAnswerIndex()
    {
        return answerIndex;
    }
}

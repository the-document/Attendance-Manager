package com.example.nguyenhongphuc98.checkmein.UI.event_ques.new_question_dialog;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;

import com.example.nguyenhongphuc98.checkmein.R;
import com.example.nguyenhongphuc98.checkmein.ViewGroupModel.AnswerView;

import java.util.ArrayList;

public class NewQuestionDialogFragment extends DialogFragment implements NewQuestionDialogContract.NewQuestionDialogView {

    private final Integer maxNumOfInitAnswers = 4;

    private ConstraintLayout mLayout;
    private ScrollView mScrollView;

    private EditText mEdtQuestion;

    private NewQuestionDialogContract.NewQuestionDialogPresenter presenter;

    //Mảng lưu trữ tất cả các AnswerView.
    //Mặc định khi mới bấm vào thêm câu hỏi thì sẽ được cài sẵn 4 trường AnswerView.
    private ArrayList<AnswerView> answerViewArrayList = new ArrayList<AnswerView>();
    //AnswerView width là dp.
    private final int answerViewWidth = 320;

    private Button mButtonAddAnswer;
    private Button mButtonFinish;

    public NewQuestionDialogFragment()
    {

    }

    public static NewQuestionDialogFragment newInstance()
    {
        NewQuestionDialogFragment fragment = new NewQuestionDialogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_question_and_answers, container);
    }

    @Override
    public void onResume() {
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        super.onResume();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Ánh xạ.
        AssignAllControl(view);

        //Xử lý presenter.
        presenter = new NewQuestionDialogPresenter();
        presenter.setView(this);

        //Số câu trả lời ban đầu.
        for (int i=0;i<maxNumOfInitAnswers;++i)
        {
            AddOneMoreAnswer();
        }
        //Thêm tất cả listener có thể có.
        AssignListeners();

    }

    private void AssignListeners()
    {
        //Thêm listener bắt sự kiện khi người dùng muốn thêm câu trả lời cho câu hỏi.
        mButtonAddAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddOneMoreAnswer();
            }
        });

        //Thêm listener bắt sự kiện khi người dùng hoàn thành việc thêm câu hỏi và cần lưu lại.
        mButtonFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.finishAddingQuestion();
            }
        });
    }

    private void AddOneMoreAnswer()
    {
        final AnswerView answerView = new AnswerView(getContext());
        answerView.setId(View.generateViewId());
        answerView.setAnswerIndex(answerViewArrayList.size());

        //answerView.setAnswerViewHolderList(answerViewArrayList);

        //Set listener để nó tự xoá mình ra khỏi view khi bị ấn delete.
        answerView.setOnClickRemoveAnswerListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RemoveAnswerViewAt(answerView.getAnswerIndex());
            }
        });

        //Chỉnh thuộc tính.
        answerView.setLayoutParams(new ViewGroup.LayoutParams((answerViewWidth * (int)getContext().getResources().getDisplayMetrics().density), ViewGroup.LayoutParams.WRAP_CONTENT));

        //Thêm vào view chính của chúng ta (chú ý phải thêm vào view trước mới được phép set constraint.
        mLayout.addView(answerView);

        //Set constraint.
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(mLayout);
        Integer topID = -1;
        if (answerViewArrayList.size() > 0)
            topID = answerViewArrayList.get(answerViewArrayList.size()-1).getId();
        else
            topID = R.id.fragment_add_question_and_answers_edt_question;
        //Chỉnh constraint cho chính mình nè.
        try
        {
            constraintSet.connect(answerView.getId(),ConstraintSet.TOP, topID, ConstraintSet.BOTTOM);
            constraintSet.connect(answerView.getId(), ConstraintSet.LEFT, R.id.fragment_add_question_and_answers_layout, ConstraintSet.LEFT);
            constraintSet.connect(answerView.getId(), ConstraintSet.RIGHT, R.id.fragment_add_question_and_answers_layout, ConstraintSet.RIGHT);
            //Chỉnh constraint cho thằng dưới mình nữa nè.
            constraintSet.connect(R.id.fragment_add_question_and_answers_btn_add_answer, ConstraintSet.TOP, answerView.getId(), ConstraintSet.BOTTOM);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        constraintSet.applyTo(mLayout);

        // Cuối cùng là thêm AnswerView vào ArrayList để lưu trữ.
        answerViewArrayList.add(answerView);
    }

    private void RemoveAnswerViewAt(Integer index)
    {
        //Sau khi xoá một AnswerView ra khỏi Array thì ta phải Rearrange tất cả các Answer View còn lại.
        AnswerView ansViewToRemove = answerViewArrayList.get(index);

        int indexAsInt = index;

        answerViewArrayList.remove(indexAsInt);
        mLayout.removeView(ansViewToRemove);

        RearrangeAnswerView();
    }

    private void RearrangeAnswerView()
    {
        //Đầu tiên là gán ID cho đúng.
        for (int i=0;i<answerViewArrayList.size();++i)
        {
            answerViewArrayList.get(i).setAnswerIndex(i);
        }

        //Sau đó ta phải set lại constraint cho tất cả câu trả lời.
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(mLayout);
        //Set constrain cho câu trả lời đầu tiên.
        if (answerViewArrayList.size() > 0)
        {
            AnswerView firstAnsView = answerViewArrayList.get(0);
            constraintSet.connect(firstAnsView.getId(), ConstraintSet.TOP, R.id.fragment_add_question_and_answers_edt_question, ConstraintSet.BOTTOM);
        }
        //Set constraint cho tất cả các câu trả lời còn lại.
        for (int i=1;i<answerViewArrayList.size();++i)
        {
            AnswerView topAnsView = answerViewArrayList.get(i-1);
            AnswerView thisAnsView = answerViewArrayList.get(i);
            constraintSet.connect(thisAnsView.getId(), ConstraintSet.TOP, topAnsView.getId(), ConstraintSet.BOTTOM);
        }

        //Sau đó phải set constraint lại cho nút thêm câu hỏi nữa chứ.
        Integer topID;
        if (answerViewArrayList.size() > 0)
            topID = answerViewArrayList.get(answerViewArrayList.size()-1).getId();
        else
            topID = R.id.fragment_add_question_and_answers_edt_question;
        constraintSet.connect(R.id.fragment_add_question_and_answers_btn_add_answer, ConstraintSet.TOP, topID, ConstraintSet.BOTTOM);
        constraintSet.applyTo(mLayout);
    }

    private void AssignAllControl(View view)
    {
        mLayout = view.findViewById(R.id.fragment_add_question_and_answers_layout);
        mScrollView = view.findViewById(R.id.fragment_add_question_and_answers_scroll_view);
        mEdtQuestion = view.findViewById(R.id.fragment_add_question_and_answers_edt_question);
        mButtonAddAnswer = view.findViewById(R.id.fragment_add_question_and_answers_btn_add_answer);
        mButtonFinish = view.findViewById(R.id.fragment_add_question_and_answers_btn_accept);
    }
}

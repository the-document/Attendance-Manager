package com.example.nguyenhongphuc98.checkmein.UI.participant.question_list_fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.nguyenhongphuc98.checkmein.Data.DataCenter;
import com.example.nguyenhongphuc98.checkmein.Data.db.model.Question;
import com.example.nguyenhongphuc98.checkmein.OthersActivity.LoadingDialog;
import com.example.nguyenhongphuc98.checkmein.R;
import com.example.nguyenhongphuc98.checkmein.Adapter.QuestionListCustomAdapter;

import java.util.ArrayList;

public class QuestionListParticipantViewFragment extends Fragment {
    ListView lv_question_list;
    AppCompatButton acbFinish;
    QuestionListParticipantViewContract.QuestionListParticipantViewPresenter presenter;
    LoadingDialog loadingDialog;
    LoadingDialog secondLoadingDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_of_question_participant_view, container, false);
        lv_question_list = (ListView)view.findViewById(R.id.lv_question_list_participant_view);
        acbFinish = (AppCompatButton)view.findViewById(R.id.fab_finish);
        loadingDialog = new LoadingDialog(this.getActivity());
        secondLoadingDialog = new LoadingDialog(this.getActivity());
        //Tạo mới presenter luôn.
        presenter = new QuestionListParticipantViewPresenter(this);

        presenter.LoadQuestionList();

        return view;
    }
}

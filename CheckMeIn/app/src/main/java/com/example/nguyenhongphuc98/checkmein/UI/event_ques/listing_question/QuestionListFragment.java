package com.example.nguyenhongphuc98.checkmein.UI.event_ques.listing_question;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.nguyenhongphuc98.checkmein.Adapter.QuestionListCustomAdapter;
import com.example.nguyenhongphuc98.checkmein.Loading;
import com.example.nguyenhongphuc98.checkmein.OthersActivity.LoadingDialog;
import com.example.nguyenhongphuc98.checkmein.R;
import com.example.nguyenhongphuc98.checkmein.UI.event_ques.new_question_dialog.NewQuestionDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import com.example.nguyenhongphuc98.checkmein.R;
import com.example.nguyenhongphuc98.checkmein.UI.event_ques.new_question_dialog.NewQuestionDialogFragment;
import com.example.nguyenhongphuc98.checkmein.Adapter.QuestionListCustomAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

public class QuestionListFragment extends Fragment implements QuestionListContract.QuestionListView {

    ListView lv_question_list;
    FloatingActionButton fab_add_question;
    QuestionListContract.QuestionListPresenter presenter;

    private BroadcastReceiver mMessageRecv = new BroadcastReceiver() {
        //Receiver này sẽ nhận thông báo cập nhật lại các câu hỏi khi có thay đổi.
        @Override
        public void onReceive(Context context, Intent intent) {
            presenter.loadQuestions();
        }
    };

    @Override
    public void onDestroy() {
        LocalBroadcastManager.getInstance(this.getContext()).unregisterReceiver(mMessageRecv);
        super.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //Test thử hiển thị danh sách câu hỏi.
        View view = inflater.inflate(R.layout.fragment_list_of_question, container, false);

        //Ánh xạ.
        AssignValues(view);

        //Cài đặt presenter.
        presenter = new QuestionListPresenter();
        presenter.setView(this);
        //Xong load câu hỏi lên luôn.
        presenter.loadQuestions();

        //Xử lý sự kiện cho nút thêm.
        fab_add_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onAddNewQuestionClicked();
            }
        });
        lv_question_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                presenter.questionClicked(position);
            }
        });

        //Cả LocalBroadcastManager nữa chứ (để nhận biết cần thiết thay đổi khi người dùng thêm/sửa/xoá câu hỏi).
        LocalBroadcastManager.getInstance(this.getContext()).registerReceiver
                (mMessageRecv, new IntentFilter("QUESTION_LIST_UPDATED"));

//        LoadingDialog loadingDialog = new LoadingDialog(this.getActivity());
//        loadingDialog.showDialog();

        return view;
    }

    private void AssignValues(View view)
    {
        //Ánh xạ.
        lv_question_list = (ListView)view.findViewById(R.id.lv_question_list);
        fab_add_question = (FloatingActionButton)view.findViewById(R.id.fab_add_question);
    }

    @Override
    public void setQuestionListAdapter(QuestionListCustomAdapter adapter) {
        lv_question_list.setAdapter(adapter);
    }
}

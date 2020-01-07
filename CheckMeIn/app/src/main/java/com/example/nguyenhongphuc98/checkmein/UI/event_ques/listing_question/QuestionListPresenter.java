package com.example.nguyenhongphuc98.checkmein.UI.event_ques.listing_question;

import android.annotation.SuppressLint;

import com.example.nguyenhongphuc98.checkmein.Adapter.QuestionListCustomAdapter;
import com.example.nguyenhongphuc98.checkmein.Data.DataCenter;
import com.example.nguyenhongphuc98.checkmein.Data.db.model.Question;
import com.example.nguyenhongphuc98.checkmein.Data.network.DataManager;
import com.example.nguyenhongphuc98.checkmein.R;
import com.example.nguyenhongphuc98.checkmein.UI.event_ques.new_question_dialog.NewQuestionDialogContract;
import com.example.nguyenhongphuc98.checkmein.UI.event_ques.new_question_dialog.NewQuestionDialogFragment;
import com.example.nguyenhongphuc98.checkmein.Data.db.model.Question;
import com.example.nguyenhongphuc98.checkmein.Data.network.DataManager;
import com.example.nguyenhongphuc98.checkmein.R;
import com.example.nguyenhongphuc98.checkmein.Adapter.QuestionListCustomAdapter;
import com.shreyaspatil.MaterialDialog.MaterialDialog;
import com.shreyaspatil.MaterialDialog.interfaces.DialogInterface;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import androidx.fragment.app.FragmentManager;

public class QuestionListPresenter implements QuestionListContract.QuestionListPresenter {

    QuestionListFragment view;
    ArrayList<Question> questionsList;

    //Thời điểm bắt đầu diễn ra Event và thời điểm kết thúc Event.
    //Lưu dưới dạng phút trôi qua từ 00:00 của ngày tổ chức Event.
    //Ở đây hiện tại chỉ đang hỗ trợ Event diễn ra trong 1 ngày duy nhất.
    long eventStart;
    long eventEnd;
    String eventDate;
    long epochEventDate;

    @Override
    public void setView(QuestionListFragment view) {
        this.view = view;
    }

    @SuppressLint("RestrictedAPI")
    @Override
    public void onAddNewQuestionClicked(){
        if (isEventStarted()){
            final MaterialDialog mdialog = new MaterialDialog.Builder(view.getActivity())
                    .setTitle("Event đã bắt đầu")
                    .setMessage("Không được sửa Event đã bắt đầu !")
                    .setCancelable(true)
                    .setAnimation(R.raw.red_lock)
                    .setPositiveButton("Đóng", R.drawable.ic_done_black_24dp, new MaterialDialog.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                            dialogInterface.dismiss();
                        }
                    })
                    .build();
            mdialog.show();
            return;
        }
        FragmentManager fm = view.getChildFragmentManager();
        NewQuestionDialogFragment questionDialogFragment = NewQuestionDialogFragment.newInstance();
        questionDialogFragment.show(fm, "fragment_add_question_and_answers");
    }

    @Override
    public void loadQuestions() {
        //Đây chỉ là thêm vào để test presenter mà thôi.
        questionsList = new ArrayList<>();
        QuestionListCustomAdapter qaCustomAdapter = new QuestionListCustomAdapter(view.getContext(), R.layout.custom_question_row_layout, questionsList, false);
        view.setQuestionListAdapter(qaCustomAdapter);
        //Lấy thời gian bắt đầu và kết thúc của Event.
        String[] splittedBeginTime = DataCenter.Event.getBegin_time().split(" ");
        String[] splittedEndTime = DataCenter.Event.getEnd_time().split(" ");
        int hour = Integer.parseInt(splittedBeginTime[0]);
        int minute = Integer.parseInt(splittedBeginTime[2]);
        eventStart = (hour * 60 + minute)*60*1000;

        hour = Integer.parseInt(splittedEndTime[0]);
        minute = Integer.parseInt(splittedEndTime[2]);
        eventEnd = (hour * 60 + minute) * 60 * 1000;

        eventDate = DataCenter.Event.getEvent_day() + "T" + "00:00:00";

        Date date;
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy'T'hh:mm:ss");
        try{
            date = format.parse(eventDate);
            epochEventDate = date.getTime();
        }catch(ParseException pre){
            pre.printStackTrace();
            epochEventDate = 0;
        }

        DataManager.Instance().LoadQuestions(qaCustomAdapter, questionsList, DataCenter.EventID);
    }

    private boolean isEventFinished(){
        long currentEpoch = Calendar.getInstance().getTime().getTime();
        if (currentEpoch - epochEventDate > eventEnd)
            return true;
        return false;
    }

    private boolean isEventStarted(){
        long currentEpoch = Calendar.getInstance().getTime().getTime();
        if (currentEpoch - epochEventDate > eventStart)
            return true;
        return false;
    }

    @SuppressLint("RestrictedApi")
    public void questionClicked(int position){
        if (questionsList == null)
            return;

        //Kiểm tra xem Event đã bắt đầu chưa.
        //Nếu đã bắt đầu thì không cho chỉnh sửa.
        if (isEventStarted()){
            final MaterialDialog mdialog = new MaterialDialog.Builder(view.getActivity())
                    .setTitle("Event đã bắt đầu")
                    .setMessage("Không được sửa Event đã bắt đầu !")
                    .setCancelable(true)
                    .setAnimation(R.raw.red_lock)
                    .setPositiveButton("Đóng", R.drawable.ic_done_black_24dp, new MaterialDialog.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                            dialogInterface.dismiss();
                        }
                    })
                    .build();
            mdialog.show();
            return;
        }

        Question question = questionsList.get(position);
        FragmentManager fm = view.getChildFragmentManager();
        NewQuestionDialogFragment dlFragment = NewQuestionDialogFragment.newInstance();

        dlFragment.setQuestion(question);
        dlFragment.show(fm, "fragment_edit_question_and_answers");
    }
}

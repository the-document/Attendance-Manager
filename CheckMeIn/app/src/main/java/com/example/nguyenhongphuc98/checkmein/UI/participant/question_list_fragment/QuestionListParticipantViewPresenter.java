package com.example.nguyenhongphuc98.checkmein.UI.participant.question_list_fragment;

import android.annotation.SuppressLint;
import android.os.SystemClock;
import android.view.View;

import com.example.nguyenhongphuc98.checkmein.Data.DataCenter;
import com.example.nguyenhongphuc98.checkmein.Data.db.model.Answer;
import com.example.nguyenhongphuc98.checkmein.Data.db.model.ParticipantAnswerByQuestion;
import com.example.nguyenhongphuc98.checkmein.Data.db.model.ParticipantAnswerDetails;
import com.example.nguyenhongphuc98.checkmein.Data.db.model.ParticipantAnswerDetailsDAL;
import com.example.nguyenhongphuc98.checkmein.Data.db.model.Question;
import com.example.nguyenhongphuc98.checkmein.Data.network.DataManager;
import com.example.nguyenhongphuc98.checkmein.R;
import com.google.android.material.button.MaterialButton;
import com.shreyaspatil.MaterialDialog.AbstractDialog;
import com.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog;
import com.shreyaspatil.MaterialDialog.MaterialDialog;
import com.shreyaspatil.MaterialDialog.interfaces.DialogInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class QuestionListParticipantViewPresenter implements QuestionListParticipantViewContract.QuestionListParticipantViewPresenter {
    QuestionListParticipantViewFragment view;
    ArrayList<Question> questionsList;
    List<ParticipantAnswerByQuestion> answerByQuestionList;
    com.example.nguyenhongphuc98.checkmein.Adapter.QuestionListCustomAdapter qaCustomAdapter;

    long startTime;
    long endTime;

    //Biến này dùng để thể hiện tình trạng trả lời câu hỏi của người dùng.
    boolean isAnswerSubmitted = false;

    public QuestionListParticipantViewPresenter(QuestionListParticipantViewFragment fragmentView){
        this.view = fragmentView;
        startTime = SystemClock.elapsedRealtime();
    }

    public void OnUserResultLoaded(ParticipantAnswerDetails answerDetails){
        //Load kết quả của người dùng.
        setAnswerSubmitted(true, answerDetails);
        view.loadingDialog.hideDialog();
    }

    public void OnUserAnswerLoaded(){

        //Trước hết phải xem người dùng có từng trả lời câu hỏi này chưa.
        //Nếu chưa thì ta không xét tiếp bên dưới (tức là bỏ qua công đoạn load câu trả lời của người dùng).
        if (answerByQuestionList == null || answerByQuestionList.size() == 0){
            view.loadingDialog.hideDialog();
            setAnswerSubmitted(false, null);
            return;
        }

        //Gán vào những câu hỏi câu trả lời của người dùng.

        //Đầu tiên ta duyệt qua các câu trả lời của người dùng để tạo thành 1 HashMap lấy thông tin cho nhanh.
        //Mỗi câu hỏi sẽ có câu trả lời của người dùng.
        //Ta sẽ tạo ra 1 hashmap chứa câu trả lời của người dùng.
        HashMap<String, HashMap<String, Boolean>> userAnswerHashMap = new HashMap<>();

        for (ParticipantAnswerByQuestion data : answerByQuestionList){
            String questionKey = data.getQuestionKey();

            List<String> answersKey = data.getAnswersKey();

            //Nếu câu hỏi không có câu trả lời nào cả thì thôi khỏi thêm vào.
            if (answersKey == null || answersKey.size() == 0)
                continue;

            HashMap<String, Boolean> answerVerification = new HashMap<>();

            for (String aKey : answersKey){
                answerVerification.put(aKey, true);
            }
            userAnswerHashMap.put(questionKey, answerVerification);
        }

        for (Question question : questionsList){
            for (Answer answer : question.getmAnswers()){
                //ContainsKey tức là người dùng có trả lời câu hỏi này.
                if (userAnswerHashMap.containsKey(question.getId())){
                    HashMap<String, Boolean> userAnswers = userAnswerHashMap.get(question.getId());
                    //Nếu người dùng có chọn câu này thì ta chỉnh choosen = true cho câu trả lời này.
                    if (userAnswers.containsKey(answer.getKey()) && userAnswers.get(answer.getKey())){
                        answer.setIs_choosen(true);
                    }
                }
            }
        }
        qaCustomAdapter.finishAnsweringAndShowCorrection();
    }

    @Override
    public void LoadQuestionList() {

        view.loadingDialog.showDialog();
        questionsList = new ArrayList<>();
        qaCustomAdapter = new com.example.nguyenhongphuc98.checkmein.Adapter.QuestionListCustomAdapter(view.getActivity(), R.layout.custom_question_row_layout, questionsList, true);
        view.lv_question_list.setAdapter(qaCustomAdapter);
        DataManager.Instance().LoadQuestionWithoutAnswerHighlight(qaCustomAdapter, questionsList, DataCenter.EventID);

        //Load câu trả lời trước đó của người dùng.
        answerByQuestionList = new ArrayList<>();
        DataManager.Instance().LoadUserAnswer(this, answerByQuestionList, DataCenter.UserID, DataCenter.EventID);

        //Load thành tích đạt được của người dùng.
        DataManager.Instance().LoadUserResult(this, DataCenter.UserID, DataCenter.EventID);
    }

    private ParticipantAnswerDetailsDAL calculateUserScore(){
        ParticipantAnswerDetailsDAL dal = new ParticipantAnswerDetailsDAL();
        int numCorrect = 0;
        for (Question question : questionsList){
            if (question.isQuestionAnsweredCorrectly())
                ++numCorrect;
        }

        endTime = SystemClock.elapsedRealtime();
        long elapsedTime = endTime - startTime;

        dal.setNum_correct(numCorrect);
        dal.setTime_elapsed(elapsedTime);
        dal.setTotal_question(questionsList.size());
        dal.setUser_name(DataCenter.UserDisplayName);
        return dal;
    }

    private List<ParticipantAnswerByQuestion> getQuestionAnswerPair(){
        List<ParticipantAnswerByQuestion> result = new ArrayList<>();
        for (Question question : questionsList){
            ParticipantAnswerByQuestion data = new ParticipantAnswerByQuestion();
            //Chỉnh Key của câu hỏi vào.
            data.setQuestionKey(question.getId());
            //Chỉnh xem người dùng chọn các câu trả lời nào.
            List<String> userAnswersKey = new ArrayList<>();
            for (Answer answer : question.getmAnswers()){
                if (answer.isIs_choosen()){
                    userAnswersKey.add(answer.getKey());
                }
            }
            data.setAnswersKey(userAnswersKey);
            result.add(data);
        }
        return result;
    }

    public boolean isAnswerSubmitted() {
        return isAnswerSubmitted;
    }

    @SuppressLint("RestrictedApi")
    public void setAnswerSubmitted(boolean answerSubmitted, ParticipantAnswerDetails result) {
        if (answerSubmitted && result != null){
            StringBuilder builder = new StringBuilder();
            builder.append("Xếp hạng : ").append(result.getsRanking()).append("\n\n");
            builder.append(result.getsRatioOfCorrectAnswers()).append("\n\n");
            builder.append(result.getsTimeElapsed());
            String message = builder.toString();
            view.acbFinish.setText("XEM KẾT QUẢ");
            view.acbFinish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MaterialDialog mdialog = new MaterialDialog.Builder(view.getActivity())
                            .setTitle("Kết quả")
                            .setMessage(message)
                            .setCancelable(true)
                            .setAnimation(R.raw.star_success)
                            .setPositiveButton("Đóng", R.drawable.ic_done_black_24dp, new MaterialDialog.OnClickListener(){
                                @Override
                                public void onClick(DialogInterface dialogInterface, int which) {
                                    dialogInterface.dismiss();
                                }
                            })
                            .build();
                    mdialog.show();
                }
            });
        }
        else{
            view.acbFinish.setText("HOÀN THÀNH");
            view.acbFinish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    qaCustomAdapter.finishAnsweringAndShowCorrection();
                    ParticipantAnswerDetailsDAL userScore = calculateUserScore();
                    List<ParticipantAnswerByQuestion> userAnswer = getQuestionAnswerPair();

                    DataManager.Instance().SaveUserAnswer(userAnswer, DataCenter.UserID, DataCenter.EventID);
                    DataManager.Instance().SaveUserAnswerResult(userScore, DataCenter.UserID,DataCenter.EventID);
                }
            });
        }
        isAnswerSubmitted = answerSubmitted;
    }
}

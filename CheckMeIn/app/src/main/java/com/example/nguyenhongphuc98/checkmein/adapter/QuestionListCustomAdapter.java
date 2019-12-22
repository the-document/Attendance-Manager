package com.example.nguyenhongphuc98.checkmein.Adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.nguyenhongphuc98.checkmein.Data.db.model.Answer;
import com.example.nguyenhongphuc98.checkmein.R;
import com.example.nguyenhongphuc98.checkmein.Data.db.model.Question;
import com.example.nguyenhongphuc98.checkmein.Utils.Utils;

import java.util.ArrayList;

public class QuestionListCustomAdapter extends ArrayAdapter<Question> {

    //Có thể cài đặt margin của từng dòng ở đây.
    final int rowMarginTop = 8;
    final int rowMarginLeft = 8;
    final int rowMarginRight = 8;
    final int rowMarginBottom = 0;

    private Context context;
    private int resource;
    private ArrayList<Question> questionArray;

    public QuestionListCustomAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Question> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.questionArray = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //Lấy danh sách câu trả lời.
        ArrayList<Answer> answerList = questionArray.get(position).getmAnswers();
        //Lấy nội dung câu hỏi.
        String question = questionArray.get(position).getContent();

        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.custom_question_row_layout, null);
            viewHolder = new ViewHolder();

            viewHolder.txtQuestion = (TextView)convertView.findViewById(R.id.custom_question_row_question);

            //Thêm câu trả lời vào đây.
            viewHolder.answerRows = new ArrayList<>();
            viewHolder.answerKeys = new ArrayList<>();
            viewHolder.answerContents = new ArrayList<>();

            viewHolder.answerListLayout = (LinearLayout)convertView.findViewById(R.id.custom_question_row_layout_answers_layout);

            if (answerList != null){
                for (int i=0;i<answerList.size();++i){
                    View answerRow = inflater.inflate(R.layout.custom_answer_row_layout,null);
                    viewHolder.answerRows.add(answerRow);
                    viewHolder.answerKeys.add(answerRow.findViewById(R.id.custom_answer_row_layout_answer_key));
                    viewHolder.answerContents.add(answerRow.findViewById(R.id.custom_answer_row_layout_answer_content));
                }
            }

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        //Gán câu hỏi.
        viewHolder.txtQuestion.setText(question);

        //Không có câu trả lời thì thôiii.
        if (answerList == null)
            return convertView;

        //Xét xem số câu trả lời hiển thị đang dư hay thiếu.
        //Đang thiếu nè, do câu trả lời nhiều hơi số dòng hiện tại.
        if (answerList.size() > viewHolder.answerRows.size()){
            //Thiếu thì ta thêm vào thôi. Ez.
            for (int i = viewHolder.answerRows.size(); i < answerList.size(); ++i){
                View answerRow = inflater.inflate(R.layout.custom_answer_row_layout,null);
                viewHolder.answerRows.add(answerRow);
                viewHolder.answerKeys.add(answerRow.findViewById(R.id.custom_answer_row_layout_answer_key));
                viewHolder.answerContents.add(answerRow.findViewById(R.id.custom_answer_row_layout_answer_content));
            }
        }

        //Đang dư nè, do số câu trả lời hiện tại đang ít hơn số dòng hiển thị.
        else if (answerList.size() < viewHolder.answerRows.size()){
            //Dư thì ta xoá bớt các dòng cuối đi thôi.
            for (int i = viewHolder.answerRows.size(); i > answerList.size(); --i){
                //Xoá ở cả view đi luôn chứ để làm gì :V.
                viewHolder.answerListLayout.removeView(viewHolder.answerRows.get(i-1));

                viewHolder.answerRows.remove(i-1);
                viewHolder.answerKeys.remove(i-1);
                viewHolder.answerContents.remove(i-1);
            }
        }

        //Trong trường hợp bằng thì thôi, không thêm không bớt nữa mà chỉ chỉnh lại nội dung.

        for (int i=answerList.size()-1;i>=0;--i){

            int charNum = 65 + i;
            char thisOrder = (char)charNum;
            String sAnswerKey = String.valueOf(thisOrder);

            Answer answer = answerList.get(i);

            View answerRow = viewHolder.answerRows.get(i);
            TextView answerKey = viewHolder.answerKeys.get(i);
            TextView answerContent = viewHolder.answerContents.get(i);

            //Chỉnh key của câu trả lời từ key.
            answerKey.setText(answer.getKey() + ".");

            //Chỉnh key của câu trả lời từ thứ tự hiển thị.
//            answerKeys.setText(sAnswerKey + ".");

            answerContent.setText(answer.getContent());

            //Nếu như câu trả lời là câu trả lời đúng thì ta tô màu lên cho nó.
            if (answer.isIs_correct()){
                answerRow.setBackgroundResource(R.drawable.custom_container_question_row_with_green_background);
                answerKey.setTextColor(Color.WHITE);
                answerContent.setTextColor(Color.WHITE);
            }
            else{
                answerRow.setBackgroundResource(R.drawable.custom_container_question_row);
                answerKey.setTextColor(Color.BLACK);
                answerContent.setTextColor(Color.BLACK);
            }

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(Utils.fromDPtoPX(rowMarginLeft),Utils.fromDPtoPX(rowMarginTop),Utils.fromDPtoPX(rowMarginRight),rowMarginBottom);

            //Nếu câu hỏi chưa được thêm vào màn hình thì ta thêm vào thôi.
            if (answerRow.getParent() == null)
                viewHolder.answerListLayout.addView(answerRow, 0, params);
        }

        return convertView;
    }

    public class ViewHolder{
        TextView txtQuestion;
        LinearLayout answerListLayout;
        ArrayList<View> answerRows;
        ArrayList<TextView> answerKeys;
        ArrayList<TextView> answerContents;
    }
}

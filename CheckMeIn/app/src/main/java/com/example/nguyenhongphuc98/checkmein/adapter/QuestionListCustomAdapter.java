package com.example.nguyenhongphuc98.checkmein.Adapter;

import android.app.ActionBar;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.nguyenhongphuc98.checkmein.Data.db.model.Answer;
import com.example.nguyenhongphuc98.checkmein.R;
import com.example.nguyenhongphuc98.checkmein.Data.db.model.Question;
import com.example.nguyenhongphuc98.checkmein.Utils.Utils;

import java.util.ArrayList;

public class QuestionListCustomAdapter extends ArrayAdapter<Question> {

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
            viewHolder.answerRow = new ArrayList<>();
            viewHolder.answerKey = new ArrayList<>();
            viewHolder.answerContent = new ArrayList<>();

            viewHolder.answerListLayout = (LinearLayout)convertView.findViewById(R.id.custom_question_row_layout_answers_layout);

            for (int i=0;i<answerList.size();++i){
                View answerRow = inflater.inflate(R.layout.custom_answer_row_layout,null);
                viewHolder.answerRow.add(answerRow);
                viewHolder.answerKey.add(answerRow.findViewById(R.id.custom_answer_row_layout_answer_key));
                viewHolder.answerContent.add(answerRow.findViewById(R.id.custom_answer_row_layout_answer_content));
            }

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        //Gán câu hỏi.
        viewHolder.txtQuestion.setText(question);

        for (int i=0;i<answerList.size();++i){
            Answer answer = answerList.get(i);

            View answerRow = viewHolder.answerRow.get(i);
            TextView answerKey = viewHolder.answerKey.get(i);
            TextView answerContent = viewHolder.answerContent.get(i);

            answerKey.setText(answer.getKey() + ".");
            answerContent.setText(answer.getContent());

            //Nếu như câu trả lời là câu trả lời đúng thì ta tô màu lên cho nó.
            if (answer.isIs_correct()){
                answerRow.setBackgroundResource(R.drawable.custom_container_question_row_with_green_background);
                answerKey.setTextColor(Color.WHITE);
                answerContent.setTextColor(Color.WHITE);
            }

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(Utils.fromDPtoPX(8),Utils.fromDPtoPX(8),Utils.fromDPtoPX(8),0);
            viewHolder.answerListLayout.addView(answerRow, 0, params);
        }

        return convertView;
    }

    public class ViewHolder{
        TextView txtQuestion;
        LinearLayout answerListLayout;
        ArrayList<View> answerRow;
        ArrayList<TextView> answerKey;
        ArrayList<TextView> answerContent;
    }
}

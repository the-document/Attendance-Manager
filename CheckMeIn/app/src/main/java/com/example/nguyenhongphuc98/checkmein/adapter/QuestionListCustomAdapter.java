package com.example.nguyenhongphuc98.checkmein.Adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.nguyenhongphuc98.checkmein.Data.db.model.Answer;
import com.example.nguyenhongphuc98.checkmein.R;
import com.example.nguyenhongphuc98.checkmein.Data.db.model.Question;

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

        if (convertView == null)
        {
//            convertView = LayoutInflater.from(context).inflate(R.layout.custom_question_row_layout,parent,true);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_question_row_layout, null);
            viewHolder = new ViewHolder();
            //LayoutInflater mLayoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewHolder.txtQuestion = (TextView)convertView.findViewById(R.id.custom_question_row_question);
            viewHolder.txtAnswer1 = (TextView)convertView.findViewById(R.id.custom_question_row_answer_1);
            viewHolder.txtAnswer2 = (TextView)convertView.findViewById(R.id.custom_question_row_answer_2);
            viewHolder.txtAnswer3 = (TextView)convertView.findViewById(R.id.custom_question_row_answer_3);
            viewHolder.txtAnswer4 = (TextView)convertView.findViewById(R.id.custom_question_row_answer_4);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        ArrayList<Answer> answerList = questionArray.get(position).getmAnswers();
        String question = questionArray.get(position).getContent();

        viewHolder.txtQuestion.setText(question);
        viewHolder.txtAnswer1.setText(answerList.get(0).getContent());
        viewHolder.txtAnswer2.setText(answerList.get(1).getContent());
        viewHolder.txtAnswer3.setText(answerList.get(2).getContent());
        viewHolder.txtAnswer4.setText(answerList.get(3).getContent());

        return convertView;
    }

    public class ViewHolder{
        TextView txtQuestion, txtAnswer1, txtAnswer2, txtAnswer3, txtAnswer4;
    }
}

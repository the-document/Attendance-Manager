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

import java.util.List;

public class AnswerListCustomAdapter extends ArrayAdapter<Answer> {

    private Context context;
    private int resource;
    private List<Answer> answers;

    public AnswerListCustomAdapter(@NonNull Context context, int resource, @NonNull List<Answer> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.answers = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.custom_answer_row_layout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.layout = (LinearLayout)convertView.findViewById(R.id.custom_answer_row_layout);
            viewHolder.txtViewAnswerKey = (TextView)convertView.findViewById(R.id.custom_answer_row_layout_answer_key);
            viewHolder.txtViewAnswerContent = (TextView)convertView.findViewById(R.id.custom_answer_row_layout_answer_content);

            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        Answer answer = answers.get(position);
        //Nếu câu trả lời chính xác thì ta chỉnh lại màu nền thành xanh lá cây, và chữ thành trắng.
        if (answer.isIs_correct()){
            viewHolder.layout.setBackgroundResource(R.drawable.rounded_rectangle_grey_green_solid);
            viewHolder.txtViewAnswerContent.setTextColor(Color.WHITE);
            viewHolder.txtViewAnswerKey.setTextColor(Color.WHITE);
        }
        viewHolder.txtViewAnswerKey.setText(answer.getKey() + ".");
        viewHolder.txtViewAnswerContent.setText(answer.getContent());
        return convertView;
    }
    public class ViewHolder{
        LinearLayout layout;
        TextView txtViewAnswerKey, txtViewAnswerContent;
    }
}

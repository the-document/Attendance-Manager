package com.example.nguyenhongphuc98.checkmein.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.nguyenhongphuc98.checkmein.R;
import com.example.nguyenhongphuc98.checkmein.model.ParticipantAnswerDetails;

import java.util.List;

public class ParticipantAnswerDetailsCustomAdapter extends ArrayAdapter<ParticipantAnswerDetails> {
    private Context context;
    private int resources;
    private List<ParticipantAnswerDetails> participantAnswerDetailsList;
    public ParticipantAnswerDetailsCustomAdapter(@NonNull Context context, int resource, @NonNull List<ParticipantAnswerDetails> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resources = resource;
        this.participantAnswerDetailsList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_participant_answer_row_layout, null);
            viewHolder = new ViewHolder();
            viewHolder.txtViewFullName = (TextView)convertView.findViewById(R.id.txtView_custom_participant_answer_row_layout_full_name);
            viewHolder.txtViewCorrectAnswer = (TextView)convertView.findViewById(R.id.txtView_custom_participant_answer_row_layout_correctAnswer);
            viewHolder.txtViewRanking = (TextView)convertView.findViewById(R.id.txtView_custom_participant_answer_row_layout_ranking);
            viewHolder.txtViewTimeElapsed = (TextView)convertView.findViewById(R.id.txtView_custom_participant_answer_row_layout_completed_time);

            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ParticipantAnswerDetails details = this.participantAnswerDetailsList.get(position);
        viewHolder.txtViewFullName.setText(details.getFullName());
        viewHolder.txtViewCorrectAnswer.setText(details.getsRatioOfCorrectAnswers());
        viewHolder.txtViewRanking.setText(details.getsRanking());
        viewHolder.txtViewTimeElapsed.setText(details.getsTimeElapsed());

        return convertView;
    }

    public class ViewHolder{
        TextView txtViewFullName, txtViewRanking, txtViewCorrectAnswer, txtViewTimeElapsed;
    }
}

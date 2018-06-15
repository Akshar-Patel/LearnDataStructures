package com.example.akshar.learndatastructures;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class QuestionAnswerFragment extends Fragment {
    public static final String ARG_QUESTION = "arg_question";
    public static final String ARG_ANSWER = "arg_answer";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.fragment_question_answer, container, false);
        Bundle args = getArguments();
        TextView questionTextView = rootView.findViewById(R.id.text_view_question);
        questionTextView.setText(args.getString(ARG_QUESTION));
        TextView answerTextView = rootView.findViewById(R.id.text_view_answer);
        answerTextView.setText(args.getString(ARG_ANSWER));
        return rootView;
    }
}

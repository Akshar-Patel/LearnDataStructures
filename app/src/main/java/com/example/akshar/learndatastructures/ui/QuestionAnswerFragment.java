package com.example.akshar.learndatastructures.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.example.akshar.learndatastructures.R;

public class QuestionAnswerFragment extends Fragment {
    public static final String ARG_QUESTION = "arg_question";
    public static final String ARG_ANSWER = "arg_answer";

    private Button mShowAnswerButton;
    private Switch mShowAnswerSwitch;
    private TextView mAnswerTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.fragment_question_answer, container, false);
        Bundle args = getArguments();
        TextView questionTextView = rootView.findViewById(R.id.text_view_question);
        questionTextView.setText(args.getString(ARG_QUESTION));
        mAnswerTextView = rootView.findViewById(R.id.text_view_answer);
        mAnswerTextView.setText(args.getString(ARG_ANSWER));

        RelativeLayout relativeLayout = rootView.findViewById(R.id.adLoc);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://courses.learncodeonline.in/"));
                startActivity(browserIntent);
            }
        });
        mShowAnswerButton = rootView.findViewById(R.id.button_show_answer);
        mShowAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShowAnswerButton.setVisibility(View.INVISIBLE);
                mAnswerTextView.setVisibility(View.VISIBLE);
            }
        });
        if (getActivity() != null) {
            mShowAnswerSwitch = getActivity().findViewById(R.id.switch_show_answer);
        }

        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean visible) {
        super.setUserVisibleHint(visible);
        if (visible && isResumed()) {
            setVisibilityShowAnswerButton();
            setSwitchCheckListener();

        }
    }

    private void setSwitchCheckListener() {
        mShowAnswerSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d("changed:", "check");
                if (isChecked) {
                    mShowAnswerButton.setVisibility(View.INVISIBLE);
                    mAnswerTextView.setVisibility(View.VISIBLE);
                } else {
                    mShowAnswerButton.setVisibility(View.VISIBLE);
                    mAnswerTextView.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void setVisibilityShowAnswerButton() {
        if (mShowAnswerSwitch.isChecked()) {
            mShowAnswerButton.setVisibility(View.INVISIBLE);
            mAnswerTextView.setVisibility(View.VISIBLE);
        } else {
            mShowAnswerButton.setVisibility(View.VISIBLE);
            mAnswerTextView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setVisibilityShowAnswerButton();
    }
}

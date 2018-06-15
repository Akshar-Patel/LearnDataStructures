package com.example.akshar.learndatastructures.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.example.akshar.learndatastructures.R;
import com.example.akshar.learndatastructures.api.APIHelper;
import com.example.akshar.learndatastructures.model.QuestionAnswer;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<QuestionAnswer>> {

    private static ArrayList<QuestionAnswer> sQuestionAnswerArrayList;
    private ImageButton mPrevButton;
    private ImageButton mNextButton;
    private QuestionAnswerPagerAdapter mQuestionAnswerPagerAdapter;
    private ViewPager mViewPager;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);

        getSupportLoaderManager().initLoader(0, null, this);

        mQuestionAnswerPagerAdapter = new QuestionAnswerPagerAdapter(getSupportFragmentManager());
        mViewPager = findViewById(R.id.pager);

        mPrevButton = findViewById(R.id.button_prev);
        mNextButton = findViewById(R.id.button_next);

        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
            }
        });
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
            }
        });
    }

    @NonNull
    @Override
    public Loader<List<QuestionAnswer>> onCreateLoader(int id, @Nullable Bundle args) {
        return new QuestionAnswerLoader(this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<QuestionAnswer>> loader, List<QuestionAnswer> data) {
        sQuestionAnswerArrayList = (ArrayList<QuestionAnswer>) data;
        mQuestionAnswerPagerAdapter.setData(data);
        mViewPager.setAdapter(mQuestionAnswerPagerAdapter);
        mViewPager.addOnPageChangeListener(new QuestionAnswerOnPageChangeListener(mPrevButton, mNextButton));
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<QuestionAnswer>> loader) {

    }

    private static class QuestionAnswerLoader extends AsyncTaskLoader<List<QuestionAnswer>> {
        QuestionAnswerLoader(MainActivity mainActivity) {
            super(mainActivity);
        }

        @Override
        protected void onStartLoading() {
            forceLoad();
        }

        @Nullable
        @Override
        public List<QuestionAnswer> loadInBackground() {
            return APIHelper.fetchQuestionAnswerList(getContext());
        }
    }

    private static class QuestionAnswerPagerAdapter extends FragmentStatePagerAdapter {

        ArrayList<QuestionAnswer> questionAnswerArrayList;

        QuestionAnswerPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        void setData(List data) {
            questionAnswerArrayList = (ArrayList<QuestionAnswer>) data;

        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = new QuestionAnswerFragment();
            Bundle args = new Bundle();
            args.putString(QuestionAnswerFragment.ARG_QUESTION, questionAnswerArrayList.get(position).getQuestion());
            args.putString(QuestionAnswerFragment.ARG_ANSWER, questionAnswerArrayList.get(position).getAnswer());
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            return questionAnswerArrayList.size();
        }
    }

    private static class QuestionAnswerOnPageChangeListener implements ViewPager.OnPageChangeListener {

        ImageButton prevButton, nextButton;

        QuestionAnswerOnPageChangeListener(ImageButton prev, ImageButton next) {
            prevButton = prev;
            nextButton = next;
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            if (position == 0) {
                if (prevButton != null) {
                    prevButton.setVisibility(View.INVISIBLE);
                }
            } else if (position == sQuestionAnswerArrayList.size() - 1) {
                if (nextButton != null) {
                    nextButton.setVisibility(View.INVISIBLE);
                }
            } else {
                if (prevButton != null && nextButton != null) {
                    prevButton.setVisibility(View.VISIBLE);
                    nextButton.setVisibility(View.VISIBLE);
                }
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}

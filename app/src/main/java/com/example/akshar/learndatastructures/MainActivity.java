package com.example.akshar.learndatastructures;

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
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.example.akshar.learndatastructures.api.APIHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<QuestionAnswer>> {

    private static final String TAG = AppCompatActivity.class.getSimpleName();
    static ImageButton prevButton;
    static ImageButton nextButton;
    private static ArrayList<QuestionAnswer> questionAnswerArrayList;
    QuestionAnswerPagerAdapter questionAnswerPagerAdapter;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportLoaderManager().initLoader(0, null, this);

        questionAnswerPagerAdapter = new QuestionAnswerPagerAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.pager);

        prevButton = findViewById(R.id.button_prev);
        nextButton = findViewById(R.id.button_next);

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
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
        questionAnswerArrayList = (ArrayList<QuestionAnswer>) data;
        Log.d(TAG, data.get(0).getQuestion());
        questionAnswerPagerAdapter.setData(data);
        viewPager.setAdapter(questionAnswerPagerAdapter);
        viewPager.addOnPageChangeListener(new QuestionAnswerOnPageChangeListener());

    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<QuestionAnswer>> loader) {

    }

    private static class QuestionAnswerLoader extends AsyncTaskLoader<List<QuestionAnswer>> {
        public QuestionAnswerLoader(MainActivity mainActivity) {
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

        public QuestionAnswerPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void setData(List data) {
            questionAnswerArrayList = (ArrayList<QuestionAnswer>) data;

        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = new QuestionAnswerFragment();
            Bundle args = new Bundle();
            // Our object is just an integer :-P
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
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            Log.d(TAG, "correct postion" + position);
            if (position == 0) {
                if (prevButton != null) {
                    prevButton.setVisibility(View.INVISIBLE);
                }
            } else if (position == questionAnswerArrayList.size() - 1) {
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

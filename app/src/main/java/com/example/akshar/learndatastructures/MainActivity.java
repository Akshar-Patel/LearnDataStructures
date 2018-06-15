package com.example.akshar.learndatastructures;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<QuestionAnswer>> {

    private static final String TAG = AppCompatActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportLoaderManager().getLoader(0) != null) {
            getSupportLoaderManager().initLoader(0, null, this);
        }
    }

    @NonNull
    @Override
    public Loader<List<QuestionAnswer>> onCreateLoader(int id, @Nullable Bundle args) {
        return new QuestionAnswerLoader(this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<QuestionAnswer>> loader, List<QuestionAnswer> data) {

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
}

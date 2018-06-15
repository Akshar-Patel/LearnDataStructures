package com.example.akshar.learndatastructures;

import android.content.Context;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class APIHelper {
    private static final OkHttpClient mClient = new OkHttpClient();
    private static final Moshi mMoshi = new Moshi.Builder().build();
    private static final JsonAdapter<List<QuestionAnswer>> sListJsonAdapter = mMoshi.adapter(
            Types.newParameterizedType(List.class, QuestionAnswer.class));

    static List<QuestionAnswer> fetchQuestionAnswerList(Context context) {
        Request request = new Request.Builder()
                .url(BuildConfig.DATA_URL)
                .build();
        Response response;
        try {
            response = mClient.newCall(request).execute();
            if (!response.isSuccessful()) {
                throw new IOException(context.getString(R.string.io_exception_message) + response);
            }
            if (response.body() != null) {

                JSONArray jsonArray = null;
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    jsonArray = jsonObject.getJSONArray("questions");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return sListJsonAdapter.fromJson(jsonArray.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}

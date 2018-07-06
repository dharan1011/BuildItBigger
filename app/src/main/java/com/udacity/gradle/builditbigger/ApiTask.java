package com.udacity.gradle.builditbigger;

import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;

public class ApiTask extends AsyncTask<Void, Void, String> {
    private static final String TAG = ApiTask.class.getSimpleName();
    private static MyApi myApiService = null;
    private ApiTaskCallback mApiTaskCallback;

    public void setApiTaskCallback(ApiTaskCallback apiTaskCallback) {
        mApiTaskCallback = apiTaskCallback;
    }

    @Override
    protected String doInBackground(Void... voids) {
        if (mApiTaskCallback != null)
            mApiTaskCallback.showLoading(true);
        if (myApiService == null) {
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/");
            myApiService = builder.build();
        }

        try {
            return myApiService.tellJoke().execute().getData();
        } catch (IOException e) {
            Log.w(TAG, "doInBackground: " + e.getLocalizedMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (mApiTaskCallback != null)
            mApiTaskCallback.showLoading(false);
        mApiTaskCallback.onResponse(s);
    }

    interface ApiTaskCallback {
        void showLoading(boolean flag);

        void onResponse(String joke);
    }
}

package com.udacity.gradle.builditbigger;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import me.dharanaditya.jokeviewer.JokeActivity;


public class MainActivity extends AppCompatActivity implements ApiTask.ApiTaskCallback {

    public static final String TAG = MainActivity.class.getSimpleName();
    private ProgressBar mProgressBar;
    private ApiTask mApiTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProgressBar = findViewById(R.id.progress_bar);
        mApiTask = new ApiTask();
        mApiTask.setApiTaskCallback(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view) {
        mApiTask.execute();
    }

    @Override
    public void showLoading(boolean flag) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProgressBar.setVisibility(flag ? View.VISIBLE : View.INVISIBLE);
            }
        });
    }

    @Override
    public void onResponse(String joke) {
        if (joke != null) {
            Intent showJoke = new Intent(this, JokeActivity.class);
            showJoke.putExtra(Intent.EXTRA_TEXT, joke);
            startActivity(showJoke);
        } else {
            Toast.makeText(this, R.string.api_failure_message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mApiTask != null) {
            mApiTask.cancel(true);
        }
    }
}

package com.udacity.gradle.builditbigger;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;

import static junit.framework.TestCase.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class NonEmptyStringTest {
    private static final String TAG = NonEmptyStringTest.class.getSimpleName();
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void checkResponseString() throws InterruptedException {
        final CountDownLatch signal = new CountDownLatch(1);
        ApiTask task = new ApiTask();
        task.setApiTaskCallback(new ApiTask.ApiTaskCallback() {
            @Override
            public void showLoading(boolean flag) {

            }

            @Override
            public void onResponse(String joke) {
                Log.d(TAG, "onResponse() called with: joke = [" + joke + "]");
                assertNotNull(joke);
                signal.countDown();
            }
        });
        task.execute();
        signal.await();
    }
}

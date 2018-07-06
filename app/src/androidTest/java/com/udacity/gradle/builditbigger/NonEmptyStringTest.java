package com.udacity.gradle.builditbigger;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.TestCase.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class NonEmptyStringTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void checkResponseString(){
        ApiTask task = new ApiTask();
        task.setApiTaskCallback(new ApiTask.ApiTaskCallback() {
            @Override
            public void showLoading(boolean flag) {

            }

            @Override
            public void onResponse(String joke) {
                assertNotNull(joke);
            }
        });
        task.execute();
    }
}

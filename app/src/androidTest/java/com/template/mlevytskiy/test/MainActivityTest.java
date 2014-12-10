package com.template.mlevytskiy.test;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Instrumentation;
import android.os.Build;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;

import com.template.mlevytskiy.ui.MainActivity;

/**
 * Created by Макс on 01.05.2014.
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    @UiThreadTest
    public void testRecreateActivity() throws InterruptedException {
        Activity activity = this.getActivity();

//        activity.finish();
//        setActivity(null);
//        activity = getActivity();

        Instrumentation instrumentation = getInstrumentation();
        instrumentation.callActivityOnRestart(activity);
        instrumentation.callActivityOnStart(activity);
        instrumentation.callActivityOnResume(activity);

        instrumentation.getUiAutomation().wait(5000);
    }
}


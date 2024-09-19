package com.example.vehidocs.activities;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import com.example.vehidocs.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MenuActivityTest {
    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void isLogin() {
        onView(withId(R.id.login_btn)).perform(click());
    }

    @After
    public void tearDown() throws Exception {
    }
}

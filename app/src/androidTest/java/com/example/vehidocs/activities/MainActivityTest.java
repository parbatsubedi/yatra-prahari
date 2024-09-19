package com.example.vehidocs.activities;

import androidx.test.core.app.ActivityScenario;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class MainActivityTest {

    @Test
    public void isSplashActivityLaunched(){ActivityScenario.launch(MainActivity.class);
    }
}
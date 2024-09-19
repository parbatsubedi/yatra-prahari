package com.example.vehidocs.activities;

import androidx.test.core.app.ActivityScenario;

import org.junit.Test;

public class ActivityIsolationTest {
    @Test
    public void isSplashedActivityLaunched() {
        ActivityScenario.launch(MainActivity.class);
    }

    @Test
    public void isLoginActivityLaunched() {
        ActivityScenario.launch(LoginUserActivity.class);
        ActivityScenario.launch(LogInOTPConfirmation.class);
    }

    @Test
    public void isRegisterActivityLaunched() {
        ActivityScenario.launch(RequestActivity.class);
    }

    @Test
    public void isMenuActivityLaunched() {
        ActivityScenario.launch(MainPageActivity.class);
    }
}

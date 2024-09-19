package com.example.vehidocs;

import com.google.common.truth.Truth;

import org.junit.Test;

public class SignUpActivity {

    String emailPattern = "[a-zA-z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Test
    public void invalidEmailFailsTest() {
        String email = "abc@";
        boolean matches = email.matches(emailPattern);
        Truth.assertThat(matches).isFalse();
    }

    @Test
    public void validEmailFailsTest() {
        String email = "abc@gmail.com";
        boolean matches = email.matches(emailPattern);
        Truth.assertThat(matches).isTrue();
    }

    @Test
    public void invalidMobileNumberTest(){
        String mobileNo = "980123456";
        boolean length = mobileNo.length() == 10;
        Truth.assertThat(length).isFalse();
    }

    @Test
    public void validMobileNumberTest(){
        String mobileNo = "9801234567";
        boolean length = mobileNo.length() == 10;
        Truth.assertThat(length).isTrue();
    }


}

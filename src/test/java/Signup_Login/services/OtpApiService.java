package Signup_Login.services;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class OtpApiService {

    private static final String BASE_URL = "http://192.168.1.103:8000";

    public static String fetchOtp(String email, String authType) {

        String requestBody = "{ \"email\": \"" + email + "\", \"auth_type\": \"" + authType + "\" }";

        Response response = RestAssured.given().contentType("application/json").body(requestBody).post(BASE_URL + "/api/v1/auth/send-otp/");

        return response.jsonPath().getString("data.OTP");
    }
}

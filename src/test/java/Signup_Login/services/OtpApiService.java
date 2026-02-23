package Signup_Login.services;

import io.qameta.allure.Allure;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import zutilities.Logs;

import static zutilities.StartupCode.test;

public class OtpApiService {

    private static final String BASE_URL = "http://192.168.1.103:8000";

    public static String fetchOtp(String email, String authType) {

        String endpoint = "/api/v1/auth/send-otp/";

        String requestBody = "{ \"email\": \"" + email + "\", \"auth_type\": \"" + authType + "\" }";

        String stepName = "OTP API → Fetch OTP for → " + email;

        long start = System.currentTimeMillis();

        try {

            Logs.info(test, "🚀 " + stepName);
            Allure.step(stepName);
            Allure.addAttachment("Request JSON", "application/json", requestBody);
            Response response =
                    RestAssured.given()
                            .contentType("application/json")
                            .body(requestBody)
                            .post(BASE_URL + endpoint);

            long duration = System.currentTimeMillis() - start;

            String responseBody = response.getBody().asPrettyString();
            Allure.addAttachment("Response JSON", "application/json", responseBody);
            test.info("📤 Request JSON:<pre>" + requestBody + "</pre>");
            test.info("📥 Response JSON:<pre>" + responseBody + "</pre>");

            Logs.info(test,
                    "✅ API Status: " + response.getStatusCode()
                            + " (" + duration + " ms)");

            return response.jsonPath().getString("data.OTP");
        } catch (Exception e) {
            Logs.fail(null, test, "❌ OTP API FAILED → " + e.getMessage());
            throw e;
        }
    }
}
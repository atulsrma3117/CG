package Signup_Login.services;

import io.qameta.allure.Allure;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import zutilities.Logs;


public class OtpApiService {

    private static final String BASE_URL = "http://3.84.224.180/backend";

    public static String fetchOtp(String email, String authType) {

        String endpoint = "/api/v1/auth/send-otp/";

        String requestBody = "{ \"email\": \"" + email + "\", \"auth_type\": \"" + authType + "\" }";

        String stepName = "OTP API → Fetch OTP for → " + email;

        long start = System.currentTimeMillis();

        try {

            Logs.info("🚀 " + stepName);
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
            Logs.info( "📤 Request JSON:<pre>" + requestBody + "</pre>");
            Logs.info( "📥 Response JSON:<pre>" + responseBody + "</pre>");

            Logs.info(
                    "✅ API Status: " + response.getStatusCode()
                            + " (" + duration + " ms)");

            return response.jsonPath().getString("data.OTP");
        } catch (Exception e) {
            Logs.info("❌ OTP API FAILED → " + e.getMessage());
            throw e;
        }
    }
}
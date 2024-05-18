import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.testng.AssertJUnit.assertEquals;

public class myDemo {
    static String cookieValue;
    @BeforeTest
    public static void setUp() {

        // Define the base URI of the API
        RestAssured.baseURI = "https://staging.nafith.sa";

        // Send a GET request to the API endpoint
        Response response = RestAssured
                .given()
                .when()
                .get("/accounts/login/company/?next=%2Faccounts%2Flogout%2F");

        // Extract the cookie named "csrftoken" from the response
         cookieValue = response.getCookie("csrftoken");

        System.out.println("Extracted Cookie: " + cookieValue);

    }


    @Test
    public void login() {

        //Test the login api
        Response loginResponse =
                given()
                        .contentType("multipart/form-data")
                        .multiPart("csrfmiddlewaretoken", cookieValue )
                        .multiPart("email", "mohammad.j%2B46895%40sitech.me")
                        .multiPart("password", "Test%40123456")
                        .when()
                        .post("/accounts/login/company")
                        .then().log().all().extract().response();

        assertEquals(loginResponse.getStatusCode(), 301);
    }

    @Test
    public void otp() {

        Response GenerateOtpResponse = given()
                .contentType("multipart/form-data")
                .multiPart("csrfmiddlewaretoken",  cookieValue )
                .multiPart("csrfmiddlewaretoken", cookieValue)
                .multiPart("otp_digit", "111111")
                .when()
                .post("/accounts/otp-verification/Rx1JfoEtQXP5bXAJvYt6ue2bmBsCg6IVmEFkIdPGAhnrGXkFkVpzEAR6gxUkqFOG/?action=company_login")
                .then().log().all().extract().response();

        Response otpResponse = given()
                .contentType("multipart/form-data")
                .multiPart("csrfmiddlewaretoken",  cookieValue )
                .multiPart("csrfmiddlewaretoken", cookieValue)
                .multiPart("otp_digit", "111111")
                .when()
                .post("/accounts/otp-verification/Rx1JfoEtQXP5bXAJvYt6ue2bmBsCg6IVmEFkIdPGAhnrGXkFkVpzEAR6gxUkqFOG/?action=company_login")
                .then().log().all().extract().response();

        assertEquals(otpResponse.getStatusCode(), 301);

    }
}
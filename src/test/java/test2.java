import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class test2 {

    @Test
    public void testLogin() {
        // Set base URL for the API
        RestAssured.baseURI = "https://staging.nafith.sa";

        // Define the request payload
        String payload = "csrfmiddlewaretoken=O4YHm8PLCcz3FFIV2aMtpFpda2C7YEw3VJxXYy854uKLyhwD3V0GgOtzyzbK2RWv&email=mohammad.j%2B46895%40sitech.me&password=Test%40123456";

        // Send POST request to login
        Response response = given()
                .contentType(ContentType.URLENC)
                .body(payload)
                .post("/accounts/login/company/");

        // Assert the response status code
        assertEquals(200, response.getStatusCode(), "Login failed!");

        // Additional assertions if needed
        // For example, you can assert certain content in the response body
        // assertEquals("ExpectedContent", response.getBody().asString(), "Incorrect response content");
    }
}
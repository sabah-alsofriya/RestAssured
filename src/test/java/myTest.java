import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.testng.AssertJUnit.assertEquals;

public class myTest {
    @BeforeTest
    public void setup(){
        // Set the base URL for the API
        baseURI = "https://staging.nafith.sa/accounts/login/company/";
    }

    @Test
    public void LoginTest(){

        // Step 1: Obtain CSRF token by making an initial GET request
        Response getResponse = given()
                .when()
                .baseUri("https://staging.nafith.sa/accounts/login/company/")
                .get() // Adjust the endpoint if necessary
                .then()
                .statusCode(200) // Verify the GET request was successful
                .extract()
                .response();

        // Extract the CSRF token from the cookies
        String csrfToken = getResponse.cookie("csrftoken");
        if (csrfToken == null) {
            throw new RuntimeException("CSRF token not found in cookies");
        }
        // Print the CSRF token for debugging purposes
        System.out.println("CSRF Token: " + csrfToken);

        // Step 2: Create JSON payload for login
        Response postResponse  = (Response) RestAssured
                .given()
                .baseUri("https://staging.nafith.sa")
                .contentType("multipart/form-data")
                .multiPart("csrfmiddlewaretoken", csrfToken)
                .multiPart("email", "mohammad.j%2B46895%40sitech.me")
                .multiPart("password", "Test%40123456")
                .when()
                .post("/accounts/login/company")
                .then().log().all();

        // Print the response for debugging
        System.out.println("Response: " + postResponse.asString());
        assertEquals(postResponse.getStatusCode(), 302);


    }
}

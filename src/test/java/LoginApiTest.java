import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.testng.annotations.*;

import javax.swing.text.*;
import java.util.Map;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.testng.AssertJUnit.assertEquals;

public class LoginApiTest {
    @BeforeClass
    public void setUp() {
        // Set the base URL for the API
        //RestAssured.baseURI = "https://staging.nafith.sa/";
        //https://staging.nafith.sa/accounts/login/company/?next=%2Fdashboard%2Fsanad%2F
    }

    @Test
    public void testValidLogin() {// Define the request payload for login


//Response responseGet =get("accounts/login/company/");
//        System.out.println( responseGet.getBody().toString());
        // Make a GET request to the endpoint
        Response initialResponse  = RestAssured
                .given()
                .when()
                .get("accounts/login/company/")
                .then()
                .extract()
                .response();
        // Extract the cookies from the response
        Map<String, String> cookies = initialResponse.getCookies();
        // Print the cookies

        // Use the saved cookies in a subsequent request
        Response postResponse = RestAssured
                .given()
                .cookies(cookies) // Add the cookies to the request
                .contentType("text/html; charset=utf-8")
                .formParam("username", "mohammad.j+46895@sitech.me")
                .formParam("password", "Test@123456")
                .when()
                .post("accounts/login/company/")
                .then()
                .extract()
                .response();
        System.out.println( postResponse.getStatusCode());
        assertEquals(postResponse.getStatusCode(), 302);


    }


    @Test
    public void MyTest() {
            try {
                // Fetch the HTML page
                org.jsoup.nodes.Document doc = Jsoup.connect("https://staging.nafith.sa/accounts/login/company/").get();

                // Select the hidden input with name "crfForbidden"
                Elements hiddenInputs = doc.select("input[type=hidden][name=crfForbidden]");

                if (!hiddenInputs.isEmpty()) {
                    // Get the value of the hidden input
                    Element hiddenInput = hiddenInputs.first();
                    String crfForbiddenValue = hiddenInput.attr("value");

                    // Print the value
                    System.out.println("crfForbidden value: " + crfForbiddenValue);
                } else {
                    System.out.println("No hidden input with name 'crfForbidden' found.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    @Test
    public void testInvalidLogin() {
        // Define the request payload with invalid credentials
        String invalidLoginPayload = "{\n" +
                "  \"email\": \"invalid@sitech.me\",\n" +
                "  \"password\": \"WrongPassword\"\n" +
                "}";

        // Send the POST request for invalid login and get the response
        Response invalidLoginResponse = given()
                .header("Content-Type", "application/json")
                .body(invalidLoginPayload)
                .when()
                .post("/api/v1/auth/login") // Adjust the endpoint as necessary
                .then()
                .extract().response();

        // Print invalid login response for debugging
        System.out.println("Invalid Login Response: " + invalidLoginResponse.asString());

        // Assert the status code for the invalid login response
        assertEquals(invalidLoginResponse.getStatusCode(), 401); // Assuming 401 Unauthorized for invalid login
    }
}

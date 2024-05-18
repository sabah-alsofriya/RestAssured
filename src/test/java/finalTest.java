import org.testng.annotations.BeforeTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class finalTest {

    @BeforeTest
    public static void main(String[] args) {

        // Define the base URI of the API
        RestAssured.baseURI = "https://staging.nafith.sa";

        // Send a GET request to the API endpoint
        Response response = RestAssured
                .given()
                .when()
                .get("/accounts/login/company/?next=%2Faccounts%2Flogout%2F");

        // Extract the cookie named "yourCookieName" from the response
        String cookieValue = response.getCookie("csrftoken");

        // Save the cookie value in a string variable
        System.out.println("Extracted Cookie: " + cookieValue);

    }
}

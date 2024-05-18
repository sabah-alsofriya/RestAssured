import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class Demo {
    @Test(priority = 0)
    public void login(){
        given()
                .baseUri("https://staging.nafith.sa")
                .contentType("multipart/form-data")
                .multiPart("csrfmiddlewaretoken", "GkDLo8cSOk8HmnfPEMjbJaNPjCzPwUnMcYCAC4WGit8wjEDXiRHuc74iDWViH5Me")
                .multiPart("email", "mohammad.j%2B46895%40sitech.me")
                .multiPart("password", "Test%40123456")
                .when()
                .post("/accounts/login/company")
                .then().log().all();
    }
    @Test(priority = 1)
    public void otp(){
        given()
                .baseUri("https://staging.nafith.sa")
                .contentType("multipart/form-data")
                .multiPart("csrfmiddlewaretoken", "InTr1HS0lN0jSwiDEV9pUqTQC8FfTgrFe1SgfDCOPW08PNGLi0xInnajWs1I4rQ7")
                .multiPart("csrfmiddlewaretoken", "InTr1HS0lN0jSwiDEV9pUqTQC8FfTgrFe1SgfDCOPW08PNGLi0xInnajWs1I4rQ7")
                .multiPart("otp_digit", "111111")
                .when()
                .post("/accounts/otp-verification/NDA0OWM1NDNkZWFhNDRlOGI3YWYyNjRlZWI2ODU0MTQ/?action=company_login")
                .then().log().all();
    }
}
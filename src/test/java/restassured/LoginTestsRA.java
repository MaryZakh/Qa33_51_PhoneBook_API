package restassured;

import dto.AuthRequestDto;
import dto.AuthResponseDto;
import dto.ErrorDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class LoginTestsRA {

    String endpoint = "user/login/usernamepassword";

    @BeforeMethod
    public void preCondition() {
        RestAssured.baseURI = "https://contactapp-telran-backend.herokuapp.com";
        RestAssured.basePath = "v1";
    }

    @Test
    public void loginSuccess() {
        AuthRequestDto auth = AuthRequestDto.builder()
                .username("margo@gmail.com")
                .password("Mmar123456$")
                .build();

        AuthResponseDto responseDto = given()
                .body(auth)
                .contentType("application/json")
                .when()
                .post(endpoint)
                .then()
                .assertThat().statusCode(200)
                .extract()
                .response()
                .as(AuthResponseDto.class);
        System.out.println(responseDto.getToken());

    }

    @Test
    public void loginWrongEmail() {
        AuthRequestDto auth = AuthRequestDto.builder()
                .username("margogmail.com")
                .password("Mmar123456$")
                .build();

        ErrorDto errorDto = given()
                .body(auth)
                .contentType(ContentType.JSON)
                .when()
                .post(endpoint)
                .then()
                .assertThat().statusCode(401)
                .extract()
                .response()
                .as(ErrorDto.class);
        Assert.assertEquals(errorDto.getMessage(),"Login or Password incorrect");
    }

    @Test
    public void loginWrongEmailFormat() {
        AuthRequestDto auth = AuthRequestDto.builder()
                .username("margogmail.com")
                .password("Mmar123456$")
                .build();

        given()
                .body(auth)
                .contentType(ContentType.JSON)
                .when()
                .post(endpoint)
                .then()
                .assertThat().statusCode(401)
                .assertThat().body("message", containsString("Login or Password incorrect"))
                .assertThat().body("path",equalTo("/v1/user/login/usernamepassword"));



    }


}

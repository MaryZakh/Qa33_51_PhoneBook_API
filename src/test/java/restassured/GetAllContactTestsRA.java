package restassured;

import dto.ContactDto;
import dto.ErrorDto;
import dto.GetAllContactsDto;
import io.restassured.RestAssured;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

public class GetAllContactTestsRA {

    String token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoibWFyZ29AZ21haWwuY29tIiwiaXNzIjoiUmVndWxhaXQiLCJleHAiOjE3ODMwMDc4MzQsImlhdCI6MTc4MjQwNzgzNH0.z5n45EmTEThL362SMHRFFqK0qOzsssOB-bF6Pb_u1os";

    @BeforeMethod
    public void preCondition() {
        RestAssured.baseURI = "https://contactapp-telran-backend.herokuapp.com";
        RestAssured.basePath = "v1";
    }

    @Test
    public void getAllContactsSuccess() {
        GetAllContactsDto contactsDto = given()
                .header("Authorization", token)
                .when()
                .get("contacts")
                .then()
                .assertThat().statusCode(200)
                .extract()
                .response()
                .as(GetAllContactsDto.class);
        List<ContactDto> list = contactsDto.getContacts();
        for (ContactDto contact : list) {
            System.out.println(contact.getId());
            System.out.println(contact.getEmail());
            System.out.println("Size of list -->" + list.size());
            System.out.println("==================");
        }
    }

    @Test
    public void getAllContactsWrongToken() {
        ErrorDto errorDto = given()
                .header("Authorization", "ttt")
                .when()
                .get("contacts")
                .then()
                .extract()
                .response()
                .as(ErrorDto.class);
        System.out.println(errorDto.getMessage());
        Assert.assertTrue(errorDto.getMessage().toString().contains("JWT strings must contain exactly 2 period"));
        Assert.assertEquals(errorDto.getStatus(), 401);
    }

    @Test
    public void getAllContactsWrongToken1() {
        given()
                .header("Authorization", "ttt")
                .when()
                .get("contacts")
                .then()
                .assertThat().statusCode(401)
                .assertThat().body("message", containsString("JWT strings must contain exactly 2 period"));

    }


}

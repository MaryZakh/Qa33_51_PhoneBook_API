package restassured;

import dto.ContactDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

public class UpdateContactTests {

    String token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoibWFyZ29AZ21haWwuY29tIiwiaXNzIjoiUmVndWxhaXQiLCJleHAiOjE3ODMwMDc4MzQsImlhdCI6MTc4MjQwNzgzNH0.z5n45EmTEThL362SMHRFFqK0qOzsssOB-bF6Pb_u1os";
    String id;

    ContactDto contactDto = ContactDto.builder()
            .name("Donna")
            .lastName("Doww")
            .email("donna@gmail.com")
            .phone("123654555555")
            .address("NY")
            .build();

    @BeforeMethod
    public void preCondition() {
        RestAssured.baseURI = "https://contactapp-telran-backend.herokuapp.com";
        RestAssured.basePath = "v1";
        String message = given()
                .body(contactDto)
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .when()
                .post("contacts")
                .then()
                .assertThat().statusCode(200)
                //get id from "message": "Contact was added! ID: a7809d99-68b8-46f7-8a81-f281e96883b9"
                .extract()
                .path("message");
        String[] all = message.split(": ");
        id = all[1];
    }


    @Test
    public void updateExistsContactSuccess(){
        String name = contactDto.getName();
        contactDto.setId(id);
        contactDto.setName("wwwwwwwwww");

        given()
                .body(contactDto)
                .header("Authorization", token)
                .contentType(ContentType.JSON)
                .when()
                .put("contacts")
                .then()
                .assertThat().statusCode(200)
                .assertThat().body("message", containsString("Contact was updated"));



    }
}

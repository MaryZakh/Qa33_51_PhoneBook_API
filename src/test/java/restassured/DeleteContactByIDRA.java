package restassured;

import dto.ContactDto;
import dto.MessageDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Random;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class DeleteContactByIDRA {

    String token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoibWFyZ29AZ21haWwuY29tIiwiaXNzIjoiUmVndWxhaXQiLCJleHAiOjE3ODMwMDc4MzQsImlhdCI6MTc4MjQwNzgzNH0.z5n45EmTEThL362SMHRFFqK0qOzsssOB-bF6Pb_u1os";
    String id;

    @BeforeMethod
    public void preCondition() {
        RestAssured.baseURI = "https://contactapp-telran-backend.herokuapp.com";
        RestAssured.basePath = "v1";

        int i = new Random().nextInt(100) + 1000;
        ContactDto contactDto = ContactDto.builder()
                .name("Donna")
                .lastName("Doww")
                .email("donna" + i + "@gmail.com")
                .phone("12365455" + i)
                .address("NY")
                .build();

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
    public void deleteContactById() {
        MessageDto messageDto = given()
                .header("Authorization", token)
                .when()
                .delete("contacts/" + id)
                .then()
                .assertThat().statusCode(200)
                .extract()
                .response()
                .as(MessageDto.class);
        Assert.assertEquals(messageDto.getMessage(), "Contact was deleted!");
    }

    @Test
    public void deleteContactById_1() {
        given()
                .header("Authorization", token)
                .when()
                .delete("contacts/" + id)
                .then()
                .assertThat().statusCode(200)
                .assertThat().body("message", containsString("Contact was deleted!"));

    }

    @Test
    public void deleteContactByIdWrongToken() {
        given()
                .header("Authorization", "656dfgjkv")
                .when()
                .delete("contacts/" + id)
                .then()
                .assertThat().statusCode(401)
                .assertThat().body("error",equalTo("Unauthorized"));
    }


}

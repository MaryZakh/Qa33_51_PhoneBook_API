package okhttp;

import com.google.gson.Gson;
import dto.ContactDto;
import dto.ErrorDto;
import dto.MessageDto;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Random;

public class DeleteContactByIdOkhttp {
    String token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoibWFyZ29AZ21haWwuY29tIiwiaXNzIjoiUmVndWxhaXQiLCJleHAiOjE3ODMwMDc4MzQsImlhdCI6MTc4MjQwNzgzNH0.z5n45EmTEThL362SMHRFFqK0qOzsssOB-bF6Pb_u1os";
    Gson gson = new Gson();
    OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON = MediaType.get("application/json");
    String id;

    @BeforeMethod
    public void preCondition() throws IOException {
        //create contact
        int i = new Random().nextInt(1000)+1000;
        ContactDto contactDto = ContactDto.builder()
                .name("Maya")
                .lastName("Dow")
                .email("maya" + i + "@gmail.com")
                .phone("1234556"+i)
                .address("Haifa")
                .description("Sister")
                .build();

        RequestBody body = RequestBody.create(gson.toJson(contactDto),JSON);
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts")
                .post(body)
                .addHeader("Authorization",token)
                .build();
        Response response = client.newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());
        MessageDto messageDto = gson.fromJson(response.body().string(), MessageDto.class);
        //get id from "message": "Contact was added! ID: a7809d99-68b8-46f7-8a81-f281e96883b9"
        String message = messageDto.getMessage();
        System.out.println(message);
       String [] all =  message.split(": ");
       id = all[1];
        System.out.println(id);
    }

    @Test
    public void deleteContactByIdSuccess() throws IOException {
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts/"+id)
                .delete()
                .addHeader("Authorization", token)
                .build();
        Response response = client.newCall(request).execute();
        Assert.assertEquals(response.code(),200);

        MessageDto dto = gson.fromJson(response.body().string(), MessageDto.class);
        System.out.println(dto.getMessage());
        Assert.assertEquals(dto.getMessage(),"Contact was deleted!");
    }

    @Test
    public void deleteContactByIWrongId() throws IOException {
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts/123")
                .delete()
                .addHeader("Authorization", token)
                .build();
        Response response = client.newCall(request).execute();
        Assert.assertEquals(response.code(),400);

        ErrorDto errorDto = gson.fromJson(response.body().string(), ErrorDto.class);
        System.out.println(errorDto.getError());
        System.out.println(errorDto.getMessage());
        Assert.assertEquals(errorDto.getStatus(),400);
        Assert.assertEquals(errorDto.getError(),"Bad Request");
        Assert.assertEquals(errorDto.getMessage(), "Contact with id: 123 not found in your contacts!");
    }

    @Test
    public void deleteContactByIWrongToken() throws IOException {
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts/+id")
                .delete()
                .addHeader("Authorization", "dlkjg57257dgdg")
                .build();
        Response response = client.newCall(request).execute();
        Assert.assertEquals(response.code(),401);

        ErrorDto errorDto = gson.fromJson(response.body().string(), ErrorDto.class);
        System.out.println(errorDto.getError());
        System.out.println(errorDto.getMessage());
        Assert.assertEquals(errorDto.getStatus(),401);
        Assert.assertEquals(errorDto.getError(),"Unauthorized");
        Assert.assertEquals(errorDto.getMessage(), "JWT strings must contain exactly 2 period characters. Found: 0");
    }

}


//cb14247f-50fe-4863-8fc1-f035a0cfb52d
//Hayden.Crona@gmail.com
//66223354123
//        ================================
//        9f80768a-723d-456f-ad87-8b90d086a0b6
//Maybell86@yahoo.com
//222977824123
//        ================================
//        9ea25928-eea8-4477-a01a-2497183a81d9
//Trinity.Rogahn82@gmail.com
//6101000705123
//        ================================
//        534046ae-85c2-414a-bc20-ed315cb22523
//Brooke.Bins@yahoo.com
//786370219123
//        ================================
//        986cc292-7fc0-41e2-a0ac-0088ecd2056a
//Tristian.Hayes@hotmail.com
//38445553123
//        ================================
package okhttp;

import com.google.gson.Gson;
import dto.ContactDto;
import dto.ErrorDto;
import dto.GetAllContactsDto;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

public class GetAllContactsTestsOkhttp {

    String token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoibWFyZ29AZ21haWwuY29tIiwiaXNzIjoiUmVndWxhaXQiLCJleHAiOjE3ODMwMDc4MzQsImlhdCI6MTc4MjQwNzgzNH0.z5n45EmTEThL362SMHRFFqK0qOzsssOB-bF6Pb_u1os";
    Gson gson = new Gson();
    OkHttpClient client = new OkHttpClient();

    @Test
    public void getAllContactsSuccess() throws IOException {
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts")
                .get()
                .addHeader("Authorization",token)
                .build();

        Response response = client.newCall(request).execute();
        Assert.assertEquals(response.code(),200);
        GetAllContactsDto contactsDto = gson.fromJson(response.body().string(), GetAllContactsDto.class);
        List<ContactDto>contacts = contactsDto.getContacts();
        for(ContactDto c:contacts){
            System.out.println(c.getId());
            System.out.println(c.getEmail());
            System.out.println(c.getPhone());
            System.out.println("================================");
        }
    }

    @Test
    public void getAllContactsWrongToken() throws IOException {
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts")
                .get()
                .addHeader("Authorization","dfkjghk")
                .build();

        Response response = client.newCall(request).execute();
        Assert.assertEquals(response.code(),401);
        ErrorDto errorDto = gson.fromJson(response.body().string(), ErrorDto.class);
        Assert.assertEquals(errorDto.getError(),"Unauthorized");
    }
}

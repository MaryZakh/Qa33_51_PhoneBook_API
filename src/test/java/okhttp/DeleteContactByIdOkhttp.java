package okhttp;

import com.google.gson.Gson;
import dto.DeleteByIdResponseDto;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

public class DeleteContactByIdOkhttp {
    String token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoibWFyZ29AZ21haWwuY29tIiwiaXNzIjoiUmVndWxhaXQiLCJleHAiOjE3ODMwMDc4MzQsImlhdCI6MTc4MjQwNzgzNH0.z5n45EmTEThL362SMHRFFqK0qOzsssOB-bF6Pb_u1os";
    Gson gson = new Gson();
    OkHttpClient client = new OkHttpClient();
    String id;

    @BeforeMethod
    public void preCondition(){
        //create contact
        //get id from "message": "Contact was added! ID: a7809d99-68b8-46f7-8a81-f281e96883b9"
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

        DeleteByIdResponseDto dto = gson.fromJson(response.body().string(), DeleteByIdResponseDto.class);
        System.out.println(dto.getMessage());
        Assert.assertEquals(dto.getMessage(),"Contact was deleted!");
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
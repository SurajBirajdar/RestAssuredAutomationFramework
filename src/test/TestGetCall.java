package test;


import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class TestGetCall {

    @Test
    public void getCallTest() {
        given().get("http://www.google.com").then().statusCode(200);
        Response response = given().get("http://www.google.com");
        System.out.println("Status code is: " + response.statusCode());
        //System.out.println(response.toString());
        System.out.println("getTime is: " + response.getTime());
        //response.prettyPrint();
        System.out.println("contentType: " + response.contentType());
        Headers head = response.getHeaders();
        for(Header header : head) {
            System.out.println(header);
        }
    }

}

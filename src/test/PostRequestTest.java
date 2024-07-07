package test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.*;

public class PostRequestTest {

    @Test
    public void postRequestTest() {
        String reqBody = "  {\n" +
                "    \"id\": \"55\",\n" +
                "    \"firstName\": \"Rajabhau\",\n" +
                "    \"lastName\": \"Birajdar\",\n" +
                "    \"email\": \"rajabhaubirajdar558@gmail.com\"\n" +
                "  }";

        Response response = given()
                .header("Content-Type", ContentType.JSON)
                .body(reqBody)
                .log()
                .all()
                .post("http://localhost:3000/employees");

        response.prettyPrint();
        System.out.println(response.getStatusCode());
        Assert.assertEquals(response.getStatusCode(), 201);
    }

    @Test
    public void test2() {
        Response response = given()
                .header("Content-Type",ContentType.JSON)
                .log()
                .all()
                .body(new File(System.getProperty("user.dir")+"/testJson.json"))
                .post("http://localhost:3000/employees");

        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(),201);
    }

    @Test
    public void test3() {
        String reqBody = "";
        try {
            reqBody = new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir") + "/testJson.json")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Response response = given()
                .header("Content-Type",ContentType.JSON)
                .log()
                .all()
                .body(reqBody)
                .post("http://localhost:3000/employees");

        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(),201);
    }
}

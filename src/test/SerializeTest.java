package test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class SerializeTest {

    @Test
    public void serializeTest() {
        RestAssured.baseURI = "https://rahulshettyacademy.com";
        Response response = given()
                .log()
                .all()
                .queryParam("key","qaclick123")
                .body()
                .when().post("/maps/api/place/add/json")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(200)
                .extract()
                .response();

        String responseAsString = response.asString();

    }

}

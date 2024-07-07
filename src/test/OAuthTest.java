package test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

import javax.xml.bind.util.JAXBSource;

import static io.restassured.RestAssured.given;

public class OAuthTest {

    @Test
    public void oAuthTest() {

        RestAssured.baseURI = "https://rahulshettyacademy.com";
        String response = given()
                .log()
                .all()
                .formParam("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                .formParam("client_secret","erZOWM9g3UtwNRj340YYaK_W")
                .formParam("grant_type","client_credentials")
                .formParam("scope","trust")
                .when()
                .post("/oauthapi/oauth2/resourceOwner/token")
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .response()
                .asString();

        System.out.println("Authentication server response is: " + response);
        JsonPath js = new JsonPath(response);
        String access_token = js.getString("access_token");
        System.out.println("Access token is: " + access_token);

        //Get course details

        String courseDetailsResponse = given()
                .log()
                .all()
                .queryParam("access_token",access_token)
                .when()
                .get("/oauthapi/getCourseDetails")
                .then()
                .log()
                .all()
                .assertThat()
                .extract()
                .response()
                .asString();

    }
}

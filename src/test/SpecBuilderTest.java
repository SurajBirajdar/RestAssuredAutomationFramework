package test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;
import test.googlePlaceApiPojo.AddPlaceApi;
import test.googlePlaceApiPojo.Location;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class SpecBuilderTest {

    @Test
    public void serializeTest() {
        RestAssured.baseURI = "https://rahulshettyacademy.com";
        AddPlaceApi addPlace = new AddPlaceApi();
        addPlace.setAccuracy(50);
        addPlace.setName("Frontline house");
        addPlace.setPhone_number("(+91) 983 893 3937");
        addPlace.setAddress("29, side layout, cohen 09");
        addPlace.setWebsite("http://google.com");
        addPlace.setLanguage("French-IN");

        List<String> types = new ArrayList<>();
        types.add("shoe park");
        types.add("shop");
        addPlace.setTypes(types);

        Location location = new Location();
        location.setLat(-38.383494);
        location.setLng(33.427362);
        addPlace.setLocation(location);

        RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .addQueryParam("key","qaclick123")
                .build();

        RequestSpecification response = given()
                .spec(req)
                .log()
                .all()
                .body(addPlace);

                 response.when().post("/maps/api/place/add/json")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(200)
                .extract()
                .response();

        String responseAsString = response.asString();
        System.out.println(responseAsString);

    }

}
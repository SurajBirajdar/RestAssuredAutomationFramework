package test;

import main.ReUsableMethods;
import main.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class DynamicJson {

    @Test(dataProvider = "dp")
    public void addBook(String isbn, String aisle) {
        RestAssured.baseURI = "https://rahulshettyacademy.com";
        String response = given().log().all().header("Content-Type","application/json")
                .body(payload.addBookPayload(isbn,aisle))
                .when().post("/Library/Addbook.php")
                .then().log().all().assertThat().statusCode(200).extract().response().asString();
        System.out.println("*******************");
        System.out.println(response);
        System.out.println("*******************");
        JsonPath js = ReUsableMethods.rawToJson(response);
        String ID = js.get("ID");
        System.out.println("ID is: " + ID);
    }

    @DataProvider(name="dp")
    public Object[][] getData() {
        //array = collection of elements
        //multiDimensional array = collection of arrays
        return new Object[][]{{"efgh","6789"},{"ijkl","0123"},{"mnop","4567"}};
    }
}

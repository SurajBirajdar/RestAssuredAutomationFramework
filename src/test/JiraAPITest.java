package test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;
import java.io.File;
import static io.restassured.RestAssured.*;

public class JiraAPITest {

    @Test
    public void validateJiraApi() {
        RestAssured.baseURI = "https://surajbirajdar0371.atlassian.net";
        // API to create bug in Jira
        String response = given()
                .header("Content-Type","application/Json")
                .header("Authorization","Basic c3VyYWpiaXJhamRhcjAzNzFAZ21haWwuY29tOkFUQVRUM3hGZkdGME9MWkE3a0FlQzhRQ1ZsdU9tUjFSNHdBY200RHJidFhtb09pbGJqUkp0SENYeG4zZ1kycFNHUmVwdWdyemtfZTItUF80bzg0NUVMSTVQRTduQXJxQlVneGVybU53WEE3RjYtdHZuNVVQWmhQeGZQb0xjSVdHaWx2LVd1X0VVc0NsN3pqUzdnUXRVQThlUHpVSUxGc2VrOUVHN3B2VlcxUU9IQjRVRk05Q2FSMD1EQzdEOEZFNg==")
                .body("{\n" +
                        "    \"fields\": {\n" +
                        "       \"project\":\n" +
                        "       {\n" +
                        "          \"key\": \"SCRUM\"\n" +
                        "       },\n" +
                        "       \"summary\": \"Above panel is not working\",\n" +
                        "       \"issuetype\": {\n" +
                        "          \"name\": \"Bug\"\n" +
                        "       }\n" +
                        "   }\n" +
                        "}")
                .post("/rest/api/3/issue").then().assertThat().statusCode(201).extract().response().asString();

        JsonPath js = new JsonPath(response);
        String id = js.getString("id");
        System.out.println("ID after bug creation is: " + id);

        //API to attach the attachments to bug created
        String attachmentResponse = given().log().all()
                .header("X-Atlassian-Token","no-check")
                .header("Authorization","Basic c3VyYWpiaXJhamRhcjAzNzFAZ21haWwuY29tOkFUQVRUM3hGZkdGME9MWkE3a0FlQzhRQ1ZsdU9tUjFSNHdBY200RHJidFhtb09pbGJqUkp0SENYeG4zZ1kycFNHUmVwdWdyemtfZTItUF80bzg0NUVMSTVQRTduQXJxQlVneGVybU53WEE3RjYtdHZuNVVQWmhQeGZQb0xjSVdHaWx2LVd1X0VVc0NsN3pqUzdnUXRVQThlUHpVSUxGc2VrOUVHN3B2VlcxUU9IQjRVRk05Q2FSMD1EQzdEOEZFNg==")
                .pathParam("id",id)
                .multiPart("file", new File("/Users/surajbirajdar/Desktop/Screenshot 2024-07-06 at 11.00.54 PM.png"))
                .post("/rest/api/3/issue/{id}/attachments")
                .then().log().all().assertThat().statusCode(200).extract().response().asString();

        JsonPath js2 = new JsonPath(attachmentResponse);
        System.out.println("FileName is: " + js2.getString("filename"));
        String attachmentId = js2.getString("id");
        System.out.println("Attachment Id is: " + attachmentId);
        attachmentId = attachmentId.substring(1,6);

        // API GET call to get attachment content information
        String attachContent = given().log().all()
                .pathParam("attachmentId",attachmentId)
                .header("Authorization","Basic c3VyYWpiaXJhamRhcjAzNzFAZ21haWwuY29tOkFUQVRUM3hGZkdGME9MWkE3a0FlQzhRQ1ZsdU9tUjFSNHdBY200RHJidFhtb09pbGJqUkp0SENYeG4zZ1kycFNHUmVwdWdyemtfZTItUF80bzg0NUVMSTVQRTduQXJxQlVneGVybU53WEE3RjYtdHZuNVVQWmhQeGZQb0xjSVdHaWx2LVd1X0VVc0NsN3pqUzdnUXRVQThlUHpVSUxGc2VrOUVHN3B2VlcxUU9IQjRVRk05Q2FSMD1EQzdEOEZFNg==")
                .when().get("/rest/api/3/attachment/{attachmentId}").then().log().all().assertThat().statusCode(200).extract().response().asString();

        System.out.println("Attachment content Information is: \n" + attachContent);
    }
}

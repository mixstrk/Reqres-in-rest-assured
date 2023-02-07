import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.net.HttpURLConnection;

import static io.restassured.RestAssured.given;

public class DeleteTest {

    @Test
    public void deleteUserTest() {
        given()
                .contentType(ContentType.JSON)
                .baseUri("https://reqres.in/api")
                .when().delete("/users/2")
                .then()
                .statusCode(HttpURLConnection.HTTP_NO_CONTENT);
    }
}

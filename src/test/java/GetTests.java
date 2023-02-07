import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pojos.getPojos.ColorsPojo;
import pojos.getPojos.UserPojo;

import java.net.HttpURLConnection;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class GetTests {
    private static final RequestSpecification REQ_SPEC =
            new RequestSpecBuilder()
                    .setBaseUri("https://reqres.in/api")
                    .setContentType(ContentType.JSON)
                    .build();
    @Test
    public void checkUsersTest() {
        List<UserPojo> users = given()
                .spec(REQ_SPEC)
                .when().get("/users?page=2")
                .then().log().all()
                .statusCode(HttpURLConnection.HTTP_OK)
                .body("page", equalTo(2))
                .body("data[0].id", greaterThanOrEqualTo(0))
                .body("data[0].email", notNullValue())
                .body("data[0].first_name", notNullValue())
                .body("data[0].last_name", notNullValue())
                .body("data[0].avatar", notNullValue())
                .extract().body().jsonPath().getList("data", UserPojo.class);
        Assertions.assertTrue(users.stream().allMatch(x->x.getEmail().endsWith("@reqres.in")));
        Assertions.assertTrue(users.stream().allMatch(x->x.getAvatar().endsWith(".jpg")));
        Assertions.assertTrue(users.stream().allMatch(x->x.getFirstName().length() <= 50));
        Assertions.assertTrue(users.stream().allMatch(x->x.getLastName().length() <= 50));
    }

    @Test
    public void checkSingleUserTest() {
        int id = 7;
        String idUser = String.valueOf(id);
        given()
                .spec(REQ_SPEC)
                .when().get("/users/" + idUser)
                .then().log().all()
                .statusCode(HttpURLConnection.HTTP_OK)
                .body("data.id", equalTo(id))
                .body("data.email", endsWith("@reqres.in"))
                .body("data.first_name", notNullValue())
                .body("data.last_name", notNullValue())
                .body("data.avatar", endsWith(".jpg"));
    }

    @Test
    public void CheckSingleUserNotFoundTest() {
        int id = 40;
        String idUser = Integer.toString(id);
        given()
                .spec(REQ_SPEC)
                .get("/users/" + idUser)
                .then().log().all()
                .statusCode(HttpURLConnection.HTTP_NOT_FOUND)
                .body(equalTo("{}"));
    }

    @Test
    public void checkColorsTest() {
        List<ColorsPojo> color = given()
                .spec(REQ_SPEC)
                .get("/unknown")
                .then().log().all()
                .statusCode(HttpURLConnection.HTTP_OK)
                .body("page", equalTo(1))
                .body("data[0].id", notNullValue())
                .body("data[0].name", notNullValue())
                .body("data[0].year", notNullValue())
                .body("data[0].pantone_value", notNullValue())
                .extract().jsonPath().getList("data", ColorsPojo.class);
        Assertions.assertTrue(color.stream().allMatch(x->x.getName().length() <= 50));
        Assertions.assertTrue(color.stream().allMatch(x->x.getYear() < 2023)
            && color.stream().allMatch(x->x.getYear() > 1950));
        Assertions.assertTrue(color.stream().allMatch(x->x.getColor().startsWith("#")
            && color.stream().allMatch(y->y.getColor().length() == 7)));
    }
}

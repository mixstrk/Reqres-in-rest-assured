import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pojos.postPojos.*;

import java.net.HttpURLConnection;
import java.time.Clock;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class PostTests {

    private final static RequestSpecification REQ_SPEC =
            new RequestSpecBuilder()
                    .setBaseUri("https://reqres.in/api")
                    .setContentType(ContentType.JSON)
                    .build();

    @Test
    public void checkCreateUserTest() {
        CreateUserRequestPojo userReq = new CreateUserRequestPojo();
        userReq.setName("morpheus");
        userReq.setJob("leader");

        CreateUserResponsePojo response =
                given()
                        .spec(REQ_SPEC)
                        .body(userReq)
                        .when().post("/users")
                        .then().log().all()
                        .statusCode(HttpURLConnection.HTTP_CREATED)
                        .extract().as(CreateUserResponsePojo.class);
        assertThat(response)
                .isNotNull()
                .extracting(CreateUserResponsePojo::getName)
                .isEqualTo(userReq.getName());
        assertThat(response)
                .extracting(CreateUserResponsePojo::getJob)
                .isEqualTo(userReq.getJob());
        assertThat(response)
                .extracting(CreateUserResponsePojo::getId)
                .isNotNull();
        Assertions.assertTrue(response.getId() >= 0);

        String currentTime = Clock.systemUTC().instant().toString().replaceAll("(.{14})$", "");
        Assertions.assertEquals(currentTime, response.getCreatedAt().replaceAll("(.{8})$", ""));
    }

    @Test
    public void checkRegisterUserTest() {
        RegUserRequestPojo userReq = new RegUserRequestPojo();
        userReq.setEmail("eve.holt@reqres.in");
        userReq.setPassword("pistol");

        RegUserResponsePojo response =
                given()
                        .spec(REQ_SPEC)
                        .body(userReq)
                        .when().post("/register")
                        .then().log().all()
                        .statusCode(HttpURLConnection.HTTP_OK)
                        .extract().as(RegUserResponsePojo.class);

        assertThat(response)
                .isNotNull()
                .extracting(RegUserResponsePojo::getId)
                .isEqualTo(4);

        assertThat(response)
                .extracting(RegUserResponsePojo::getToken)
                .isEqualTo("QpwL5tke4Pnpja7X4");
    }

    @Test
    public void checkUnSuccessRegUserTest() {
        UnSuccessRegUserReqPojo user = new UnSuccessRegUserReqPojo();
        user.setEmail("sydney@fife");

        UnSuccessRegUserRespPojo response =
                given()
                        .spec(REQ_SPEC)
                        .body(user)
                        .when().post("/register")
                        .then().log().all()
                        .statusCode(HttpURLConnection.HTTP_BAD_REQUEST)
                        .extract().as(UnSuccessRegUserRespPojo.class);

        assertThat(response)
                .isNotNull()
                .extracting(UnSuccessRegUserRespPojo::getError)
                .isEqualTo("Missing password");
    }
}

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pojos.putPojos.ReplaceJobReqPojo;
import pojos.putPojos.ReplaceJobRespPojo;

import java.time.Clock;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class PutTests {

    private final static RequestSpecification REQ_SPEC =
            new RequestSpecBuilder()
                    .setBaseUri("https://reqres.in/api")
                    .setContentType(ContentType.JSON)
                    .build();
    @Test
    public void replaceJobTest() {
        ReplaceJobReqPojo jobs = new ReplaceJobReqPojo();
        jobs.setJob("zion resident");

        ReplaceJobRespPojo response =
                given()
                        .spec(REQ_SPEC)
                        .body(jobs)
                        .when().put("/users/2")
                        .then().log().all()
                        .statusCode(200)
                        .extract().as(ReplaceJobRespPojo.class);

        assertThat(response)
                .isNotNull()
                .extracting(ReplaceJobRespPojo::getJob)
                .isEqualTo(jobs.getJob());

        String currentTime = Clock.systemUTC().instant().toString().replaceAll("(.{14})$", "");
        Assertions.assertEquals(currentTime, response.getUpdatedAt().replaceAll("(.{8})$", ""));
    }
}

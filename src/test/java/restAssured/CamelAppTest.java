package restAssured;

import org.junit.Test;

import static com.jayway.restassured.RestAssured.given;

public class CamelAppTest extends FunctionalTest{

    @Test
    public void basicPingTest(){
        given().when().get("/CamelApp").then().statusCode(200);
    }
}
